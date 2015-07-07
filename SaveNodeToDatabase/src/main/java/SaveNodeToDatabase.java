import com.google.common.collect.Lists;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import compiler.IdPropertyObject;
import compiler.PreorderListCallback;
import compiler.SaveNodeToDatabaseCallback;
import db.AstRootLabel;
import db.AstTypeLabels;
import db.Properties;
import db.RelationshipTypes;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Per on 05.07.2015.
 */

public class SaveNodeToDatabase {


	private static final String DATABASE_FOLDER_NAME = "database.graphdb";

	public static void main(String[] args) {

		try {
			Utils.deleteFolder(new File(DATABASE_FOLDER_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DATABASE_FOLDER_NAME);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});

		Compiler compiler = setupCompiler();

		writeAstToDatabase(compiler, graphDb);
		validateAstInDatabase(compiler, graphDb);

		graphDb.shutdown();
		System.out.println("Done");


	}

	private static Compiler setupCompiler() {
		List<SourceFile> sources = getSourceFiles(Arrays.asList(new String[]{"switch_node_code.js"}));
		CompilerOptions options = getCompilerOptions();

		com.google.javascript.jscomp.Compiler compiler = new Compiler();
		compiler.compile(new ArrayList<SourceFile>(), sources, options);

		// Normalize the AST. The Normalize compiler pass is package visible, so it is accessed via AccessToNormalize.
		AccessToNormalize.processNormalize(compiler);
		return compiler;
	}


	private static List<SourceFile> getSourceFiles(List<String> sourceFiles) {
		ArrayList<SourceFile> sources = new ArrayList<>();
		for (String filepath : sourceFiles) {
			SourceFile sourceFile = SourceFile.fromFile(filepath);
			sources.add(sourceFile);
		}
		return sources;
	}

	private static List<SourceFile> getExternFiles() {
		SourceFile externFile = SourceFile.fromFile("externs.js");
		try {
			System.out.println(externFile.getCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<SourceFile> externs = new ArrayList<>();
		externs.add(externFile);
		return externs;
	}

	private static CompilerOptions getCompilerOptions() {
		CompilerOptions options = new CompilerOptions();
		options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT5_STRICT);
		options.setSkipAllPasses(true);
		return options;
	}


	private static void printAst(com.google.javascript.rhino.Node root) {
		Stack<com.google.javascript.rhino.Node> nodesToVisit = new Stack<>();
		nodesToVisit.push(root);
		int ident = 0;
		while (!nodesToVisit.isEmpty()) {
			com.google.javascript.rhino.Node node = nodesToVisit.pop();
			if (node == null) {
				ident--;
				continue;
			}

			for (int i = 0; i < ident; i++) {
				System.out.print("    ");
			}
			System.out.println(node.toString());

			List<com.google.javascript.rhino.Node> children = Lists.newArrayList(node.children());
			if (children.size() > 0) {
				ident++;
				nodesToVisit.push(null);
				children = Lists.reverse(children);
				for (com.google.javascript.rhino.Node child : children) {
					nodesToVisit.push(child);
				}
			}
		}
	}

	private static void writeAstToDatabase(Compiler compiler, GraphDatabaseService db) {
		try (Transaction tx = db.beginTx()) {
			SaveNodeToDatabaseCallback callback = new SaveNodeToDatabaseCallback(db);

			NodeTraversal.traverse(compiler, compiler.getRoot(), callback);
			tx.success();
		}
	}

	private static void validateAstInDatabase(Compiler compiler, GraphDatabaseService db) {
		List<com.google.javascript.rhino.Node> preOrderCompilerAst = getPreOrderCompilerAst(compiler);
		List<Node> preOrderDbAst = getPreOrderDbAst(db);
		System.out.println(preOrderCompilerAst.size() == preOrderDbAst.size());
		try(Transaction tx = db.beginTx()) {

		for (int i = 0; i < preOrderCompilerAst.size(); i++) {
			com.google.javascript.rhino.Node compilerNode = preOrderCompilerAst.get(i);
			Node dbNode = preOrderDbAst.get(i);

			long compilerNodeId = ((IdPropertyObject) compilerNode.getProp(IdPropertyObject.ID_PROP)).getId();

			int compilerType = compilerNode.getType();

			int dbType = (Integer) dbNode.getProperty(Properties.AST_TYPE);
//			if (i < 20) {
//				System.out.print(compilerNodeId);
//				System.out.print(" ");
//				System.out.println(dbNode.getId());
//			}
			if (!dbNode.hasLabel(AstTypeLabels.typeToLabel(dbType))) {
				System.out.println("Error label type not equal property type.");
			}
			if (dbNode.getId() != compilerNodeId || compilerType != dbType) {
				System.out.println("Error");
			}
			tx.success();
		}
		}
	}

	private static List<Node> getPreOrderDbAst(GraphDatabaseService db) {
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
						return ((Integer)o1.getProperty(Properties.AST_CHILD_RANK)) - ((Integer) o2.getProperty(Properties.AST_CHILD_RANK));
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
//			Iterator nodes = db.findNodes(new db.AstRootLabel());
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

	private static List<com.google.javascript.rhino.Node> getPreOrderCompilerAst(Compiler compiler) {
		PreorderListCallback preorderListCallback = new PreorderListCallback();
		NodeTraversal.traverse(compiler, compiler.getRoot(), preorderListCallback);
		return preorderListCallback.getNodes();
	}

}


