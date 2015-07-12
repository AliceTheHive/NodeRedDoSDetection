import com.google.common.collect.Lists;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.graph.DiGraph;
import compiler.PreorderListCallback;
import compiler.SaveNodeToDatabaseCallback;
import db.Labels.*;
import db.Properties;
import db.RelationshipTypes;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.*;

public class Compiler {
	private List<String> inputFilePaths;
	private String databasePath; //TODO path possible or just name?
	private List<SourceFile> inputFiles;
	private com.google.javascript.jscomp.Compiler compiler;
	private CompilerOptions compilerOptions;
	private GraphDatabaseService db;

	private HashMap<com.google.javascript.rhino.Node, Long> compilerToDbNodeMap;
	private HashMap<Long, com.google.javascript.rhino.Node> dbNodeIdToCompilerNodeMap;
	private ControlFlowGraph<com.google.javascript.rhino.Node> controllFlowGraph;

	public static Compiler saveAstToDatabase(List<String> inputFilePaths, String databasePath) {
		Compiler compiler = new Compiler(inputFilePaths, databasePath);
		compiler.initialize();
		compiler.compile();
		compiler.normalizeAst();
		compiler.writeAstToDatabase();
		return compiler;
	}

	public static Compiler saveCFGToDatabase(List<String> inputFilePaths, String databasePath) {
		Compiler compiler = new Compiler(inputFilePaths, databasePath);
		compiler.initialize();
		compiler.compile();
		compiler.normalizeAst();
		compiler.writeAstToDatabase();
		compiler.generateControllFlowGraph();
		compiler.writeCFGToDatabase();
		compiler.validateCFGInDatabase();
		return compiler;
	}

	public Compiler(List<String> inputFilePaths, String databasePath) {
		this.inputFilePaths = inputFilePaths;
		this.databasePath = databasePath;
	}

	public void initialize() {
		inputFiles = getInputFiles();
		compilerOptions = getCompilerOptions();
		compiler = new com.google.javascript.jscomp.Compiler();

		db = new GraphDatabaseFactory().newEmbeddedDatabase(databasePath);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				db.shutdown();
			}
		});
	}

	public void compile() {
		compiler.compile(new ArrayList<SourceFile>(), inputFiles, compilerOptions);
	}

	private List<SourceFile> getInputFiles() {
		ArrayList<SourceFile> inputFiles = new ArrayList<>();
		for (String filepath : inputFilePaths) {
			SourceFile inputFile = SourceFile.fromFile(filepath);
			inputFiles.add(inputFile);
		}
		return inputFiles;
	}

	CompilerOptions getCompilerOptions() {
		CompilerOptions options = new CompilerOptions();
		options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT5_STRICT);
		options.setSkipAllPasses(true);
		return options;
	}

	public void normalizeAst() {
		AccessToNormalize.processNormalize(compiler);
	}

	public void writeAstToDatabase() {
		try (Transaction tx = db.beginTx()) {
			SaveNodeToDatabaseCallback callback = new SaveNodeToDatabaseCallback(db);
			NodeTraversal.traverse(compiler, compiler.getRoot(), callback);
			compilerToDbNodeMap = callback.getCompilerToDbNodeMap();
			dbNodeIdToCompilerNodeMap = callback.getDbNodeIdToCompilerNode();
			tx.success();
		}
	}

	public void validateAstInDatabase() {
		List<com.google.javascript.rhino.Node> preOrderCompilerAst = getPreOrderCompilerAst();
		List<Node> preOrderDbAst = getPreOrderDbAst();
		try (Transaction tx = db.beginTx()) {
			for (int i = 0; i < preOrderCompilerAst.size(); i++) {
				com.google.javascript.rhino.Node compilerNode = preOrderCompilerAst.get(i);
				Node dbNode = preOrderDbAst.get(i);

				int compilerType = compilerNode.getType();

				int dbType = (Integer) dbNode.getProperty(Properties.AST_TYPE);

				if (!dbNode.hasLabel(AstTypeLabels.typeToLabel(dbType))) {
					System.out.println("Error label type not equal property type.");
				}
				if (compilerType != dbType) {
					System.out.println("Error");
				}
				if (compilerNode.getChildCount() != Lists.newArrayList(dbNode.getRelationships(RelationshipTypes.AST_PARENT_OF, Direction.OUTGOING)).size()) {
					System.out.println("Error unequal child count.");
				}
				tx.success();
			}
		}
	}

	private List<Node> getPreOrderDbAst() {
		List<Node> nodeList = new ArrayList<>();
		try (Transaction tx = db.beginTx()) {
			Stack<Node> nodesToVisit = new Stack<>();
			Iterator<Node> rootNodes = db.findNodes(new AstRootLabel());
			nodesToVisit.push(rootNodes.next());

			while (!nodesToVisit.isEmpty()) {
				Node node = nodesToVisit.pop();
				nodeList.add(node);

				List<Relationship> childrenRels = Lists.newArrayList(node.getRelationships(RelationshipTypes.AST_PARENT_OF, Direction.OUTGOING));
				childrenRels.sort(new Comparator<Relationship>() {
					@Override
					public int compare(Relationship o1, Relationship o2) {
						return ((Integer) o1.getProperty(Properties.AST_CHILD_RANK)) - ((Integer) o2.getProperty(Properties.AST_CHILD_RANK));
					}
				});
				childrenRels = Lists.reverse(childrenRels);
				for (Relationship childRel : childrenRels) {
					nodesToVisit.push(childRel.getEndNode());
				}
			}
			tx.success();
			return nodeList;
		}

//			TraversalDescription preOrderTraversalWithRank = db.traversalDescription();
//			preOrderTraversalWithRank
//					.relationships(db.RelationshipTypes.AST_PARENT_OF, Direction.OUTGOING)
//					.order(BranchOrderingPolicies.PREORDER_DEPTH_FIRST)
//					.expand(new db.MyPathExpander());
//			Iterator nodes = db.findNodes(new db.Labels.AstRootLabel());
//			Iterable<Node> nodesIterable = new Iterable<Node>() {
//				@Override
//				public Iterator<Node> iterator() {
//					return nodes;
//				}
//			};
//			org.neo4j.graphdb.traversal.Traverser traverser = preOrderTraversalWithRank.traverse(nodesIterable);
//
//			nodeList = Lists.newArrayList(traverser.nodes());
//			tx.success();
//		}
//		return nodeList;
	}

	private List<com.google.javascript.rhino.Node> getPreOrderCompilerAst() {
		PreorderListCallback preorderListCallback = new PreorderListCallback();
		NodeTraversal.traverse(compiler, compiler.getRoot(), preorderListCallback);
		return preorderListCallback.getNodes();
	}

	public void generateControllFlowGraph() {
		this.controllFlowGraph = AccessToControlFlowAnalysis.processControllFlowAnalysis(compiler);
		Utils.printCfg(controllFlowGraph);
	}

	public void writeCFGToDatabase() {
		Collection<DiGraph.DiGraphNode<com.google.javascript.rhino.Node, ControlFlowGraph.Branch>> controlFlowNodes = this.controllFlowGraph.getNodes();

		try (Transaction tx = db.beginTx()) {
			Node returnNode = db.createNode();
			returnNode.addLabel(new CfgNodeLabel());
			returnNode.addLabel(new CfgReturnLabel());

			for (DiGraph.DiGraphNode<com.google.javascript.rhino.Node,ControlFlowGraph.Branch> node : controlFlowNodes) {
				if (!controllFlowGraph.isImplicitReturn(node)) {
					Node sourceDbNode = db.getNodeById(compilerToDbNodeMap.get(node.getValue()));
					sourceDbNode.addLabel(new CfgNodeLabel());
					if (node.getInEdges().size() == 0) {
						sourceDbNode.addLabel(new CfgEntryLabel());
					}

					for (DiGraph.DiGraphEdge<com.google.javascript.rhino.Node, ControlFlowGraph.Branch> edge : node.getOutEdges()) {
						DiGraph.DiGraphNode<com.google.javascript.rhino.Node, ControlFlowGraph.Branch> targetNode = edge.getDestination();
						Node targetDbNode;
						if (controllFlowGraph.isImplicitReturn(targetNode)) {
							targetDbNode = returnNode;
						} else {
							targetDbNode= db.getNodeById(compilerToDbNodeMap.get(edge.getDestination().getValue()));
						}
						sourceDbNode.createRelationshipTo(targetDbNode, RelationshipTypes.getCfgRelationshipType(edge.getValue()));
					}
				}
			}
			tx.success();
		}
	}

	public void validateCFGInDatabase() {
		try (Transaction tx = db.beginTx()) {
			HashMap<com.google.javascript.rhino.Node, DiGraph.DiGraphNode<com.google.javascript.rhino.Node, ControlFlowGraph.Branch>> astToCfgNodes = new HashMap<>();
			for (DiGraph.DiGraphNode<com.google.javascript.rhino.Node, ControlFlowGraph.Branch> node : controllFlowGraph.getNodes()) {
				if (node.getValue() != null) {
					astToCfgNodes.put(node.getValue(), node);
				}
			}

			ResourceIterator<Node> cfgNodes = db.findNodes(new CfgNodeLabel());
			int nodeCounter = 0;
			while (cfgNodes.hasNext()) {
				nodeCounter++;
				Node node = cfgNodes.next();
				DiGraph.DiGraphNode<com.google.javascript.rhino.Node, ControlFlowGraph.Branch> cfgNode = astToCfgNodes.get(dbNodeIdToCompilerNodeMap.get(node.getId()));
				if (cfgNode == null) {
					cfgNode = controllFlowGraph.getImplicitReturn();
				}

				for (ControlFlowGraph.Branch branch : ControlFlowGraph.Branch.values()) {
					if (Lists.newArrayList(node.getRelationships(RelationshipTypes.getCfgRelationshipType(branch), Direction.OUTGOING)).size()
							!= cfgNode.getOutEdges().stream().filter(e -> e.getValue() == branch).count()) {
						System.out.println("Edge count not equal.");
					}
					if (Lists.newArrayList(node.getRelationships(RelationshipTypes.getCfgRelationshipType(branch), Direction.INCOMING)).size()
							!= cfgNode.getInEdges().stream().filter(e -> e.getValue() == branch).count()) {
						System.out.println("Edge count not equal.");
					}
				}
			}
			tx.success();
			if (nodeCounter != controllFlowGraph.getNodes().size()) {
				System.out.println("Node count unequal.");
			}

		}

	}

	public void shutdown() {
		db.shutdown();
	}

	public com.google.javascript.jscomp.Compiler getCompiler() {
		return compiler;
	}

}
