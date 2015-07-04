package com.google.javascript.jscomp;

import com.google.common.collect.Lists;
import com.google.javascript.jscomp.graph.DiGraph;
import com.google.javascript.jscomp.parsing.parser.util.ErrorReporter;
import com.google.javascript.jscomp.parsing.parser.util.SourcePosition;
import com.google.javascript.rhino.Node;
import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
public class AstGetTest {

	static class SimpleErrorReporter extends ErrorReporter {

		@Override
		protected void reportMessage(SourcePosition sourcePosition, String s) {
			System.out.println("Parse Error.");
		}
	}

	public static void main(String[] args) throws IOException {
		astTest();
	}

	private static void astTest() {
		List<SourceFile> sources = getSourceFiles(Arrays.asList(new String[] {"test.js"}));
		CompilerOptions options = getCompilerOptions();

		com.google.javascript.jscomp.Compiler compiler = new Compiler();
		compiler.compile(new ArrayList<SourceFile>(), sources, options);
		normailizeAst(compiler);
		printAst(compiler.getRoot());
		int nodeCount = 0;
		NodeTraversal nodeTraversal = new NodeTraversal(compiler, new NodeTraversal.Callback() {
			@Override
			public boolean shouldTraverse(NodeTraversal nodeTraversal, Node node, Node node1) {
				return true;
			}

			@Override
			public void visit(NodeTraversal nodeTraversal, Node node, Node node1) {
				System.out.println(node);
			}
		});
		nodeTraversal.traverse(compiler.getJsRoot());
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


	private static void printAst(Node root) {
		Stack<Node> nodesToVisit = new Stack<>();
		nodesToVisit.push(root);
		int ident = 0;
		while (!nodesToVisit.isEmpty()) {
			Node node = nodesToVisit.pop();
			if (node == null) {
				ident--;
				continue;
			}

			for(int i = 0; i < ident; i++) {
				System.out.print("    ");
			}
			System.out.println(node.toString());

			List<Node> children = Lists.newArrayList(node.children());
			if (children.size() > 0) {
				ident++;
				nodesToVisit.push(null);
				children = Lists.reverse(children);
				for (Node child : children) {
					nodesToVisit.push(child);
				}
			}
		}
	}

	private static CallGraph getCallgraph(Compiler compiler) {
		CallGraph callGraph = new CallGraph(compiler);
		callGraph.process(compiler.getRoot().getFirstChild(), compiler.getRoot().getLastChild());
		return callGraph;
	}

	private static void printCfg(DiGraph.DiGraphNode entry) {
		Queue<DiGraph.DiGraphNode> diNodesToVisit = new LinkedList<>();
		diNodesToVisit.add(entry);

		while (!diNodesToVisit.isEmpty()) {
			DiGraph.DiGraphNode node = diNodesToVisit.poll();
			System.out.println(node.toString());
			for (Object edge : node.getOutEdges()) {
				diNodesToVisit.offer(((DiGraph.DiGraphEdge) edge).getDestination());
			}
		}
	}

	private static void normailizeAst(Compiler compiler) {
		Normalize normalize = new Normalize(compiler, false);
		compiler.process(normalize);
	}

}
