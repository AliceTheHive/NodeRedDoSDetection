package com.google.javascript.jscomp.parsing;

import com.google.javascript.jscomp.NodeTraversal;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.parsing.Config;
import com.google.javascript.jscomp.parsing.ParserRunner;
import com.google.javascript.jscomp.parsing.parser.util.ErrorReporter;
import com.google.javascript.jscomp.parsing.parser.util.SourcePosition;
import com.google.javascript.rhino.Node;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


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
		Config config = new Config(new HashSet<String>(), new HashSet<String>(), false, Config.LanguageMode.ECMASCRIPT5_STRICT, false);
		ParserRunner.ParseResult result = ParserRunner.parse(sourceFile, sourceFile.getCode(), config, null);
		Queue<Node> nodesToVisit = new LinkedList<Node>();
		nodesToVisit.add(result.ast);

		while (!nodesToVisit.isEmpty()) {
			Node node = nodesToVisit.poll();
			System.out.println(node.toString());
			for (Node child : node.children()) {
				nodesToVisit.offer(child);
			}
		}


	}
}
