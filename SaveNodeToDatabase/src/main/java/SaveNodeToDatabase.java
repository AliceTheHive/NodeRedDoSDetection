import com.google.common.collect.Lists;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.graph.DiGraph;
import com.google.javascript.rhino.*;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * Created by Per on 05.07.2015.
 */

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.IOException;
import java.util.*;

public class SaveNodeToDatabase {

	private static enum RelTypes implements RelationshipType {
		KNOWS
	}

	public static void main(String[] args) {
		Node firstNode;
		Node secondNode;
		Relationship relationship;


		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("database.graphdb");

		Compiler compiler = setupCompiler();

		printAst(compiler.getRoot());

		try (Transaction tx = graphDb.beginTx()) {
			firstNode = graphDb.createNode();
			firstNode.setProperty("message", "Hello, ");
			secondNode = graphDb.createNode();
			secondNode.setProperty("message", "World!");

			relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
			relationship.setProperty("message", "brave Neo4j ");
			System.out.print(firstNode.getProperty("message"));
			System.out.print(relationship.getProperty("message"));
			System.out.println(secondNode.getProperty("message"));
			tx.success();
		}

		graphDb.shutdown();
	}

	private static Compiler setupCompiler() {
		List<SourceFile> sources = getSourceFiles(Arrays.asList(new String[] {"switch_node_code.js"}));
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

			for(int i = 0; i < ident; i++) {
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
}


