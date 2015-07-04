package com.google.javascript.jscomp;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.graph.DiGraph;
import com.google.javascript.jscomp.parsing.Config;
import com.google.javascript.jscomp.parsing.ParserRunner;
import com.google.javascript.jscomp.parsing.parser.util.ErrorReporter;
import com.google.javascript.jscomp.parsing.parser.util.SourcePosition;
import com.google.javascript.rhino.Node;

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

		List<SourceFile> sources = getSourceFiles();
		List<SourceFile> externs = getExternFiles();
		CompilerOptions options = getCompilerOptions();

		com.google.javascript.jscomp.Compiler compiler = new Compiler();
		compiler.compile(externs, sources, options);

		printAst(compiler.getRoot());

		CallGraph callGraph = getCallgraph(compiler);
		System.out.println(callGraph.getAllFunctions().stream().map(f -> f.getName()).collect(Collectors.joining(" ")));
		System.out.println(callGraph.getAllCallsites().stream().map(cs -> Boolean.toString(cs.hasExternTarget())).collect(Collectors.joining(" ")));

		ControlFlowGraph cfg = compiler.computeCFG();
		printCfg(cfg.getEntry());


	}

	private static List<SourceFile> getSourceFiles() {
		SourceFile sourceFile = SourceFile.fromFile("switch_node_code.js");
		try {
			System.out.println(sourceFile.getCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<SourceFile> sources = new ArrayList<>();
		sources.add(sourceFile);
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
		Queue<Node> nodesToVisit = new LinkedList<Node>();
		nodesToVisit.add(root);

		while (!nodesToVisit.isEmpty()) {
			Node node = nodesToVisit.poll();
			System.out.println(node.toString());
			for (Node child : node.children()) {
				nodesToVisit.offer(child);
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

}
