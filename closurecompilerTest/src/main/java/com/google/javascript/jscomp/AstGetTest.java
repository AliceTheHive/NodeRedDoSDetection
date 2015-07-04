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

		SourceFile sourceFile = SourceFile.fromFile("test.js");
		try {
			System.out.println(sourceFile.getCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<SourceFile> sources = new ArrayList<>();
		sources.add(sourceFile);

		SourceFile externFile = SourceFile.fromFile("externs.js");
		try {
			System.out.println(externFile.getCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<SourceFile> externs = new ArrayList<>();
		externs.add(externFile);

		CompilerOptions options = new CompilerOptions();
		options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT5_STRICT);
		options.setSkipAllPasses(true);
		com.google.javascript.jscomp.Compiler compiler = new Compiler();
		compiler.compile(externs, sources, options);


//		Config config = new Config(new HashSet<String>(), new HashSet<String>(), false, Config.LanguageMode.ECMASCRIPT5_STRICT, false);
//		ParserRunner.ParseResult result = ParserRunner.parse(sourceFile, sourceFile.getCode(), config, null);
		Queue<Node> nodesToVisit = new LinkedList<Node>();
		nodesToVisit.add(compiler.getRoot());

		while (!nodesToVisit.isEmpty()) {
			Node node = nodesToVisit.poll();
			System.out.println(node.toString());
			for (Node child : node.children()) {
				nodesToVisit.offer(child);
			}
		}

		CallGraph callGraph = new CallGraph(compiler);
		callGraph.process(compiler.getRoot().getFirstChild(), compiler.getRoot().getLastChild());
		System.out.println(callGraph.getAllFunctions().stream().map(f -> f.getName()).collect(Collectors.joining(" ")));
		System.out.println(callGraph.getAllCallsites().stream().map(cs -> Boolean.toString(cs.hasExternTarget())).collect(Collectors.joining(" ")));

		System.out.println(compiler.toSource());
		ControlFlowGraph cfg = compiler.computeCFG();
		DiGraph.DiGraphNode test = cfg.getEntry();

		Queue<DiGraph.DiGraphNode> diNodesToVisit = new LinkedList<>();
		diNodesToVisit.add(cfg.getEntry());
		System.out.println(cfg.getEdges().size());

		while (!diNodesToVisit.isEmpty()) {
			DiGraph.DiGraphNode node = diNodesToVisit.poll();
			System.out.println(node.toString());
			for (Object edge : node.getOutEdges()) {
				diNodesToVisit.offer(((DiGraph.DiGraphEdge)edge).getDestination());
			}
		}
	}

}
