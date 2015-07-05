package com.google.javascript.jscomp;

import com.google.common.collect.Lists;
import com.google.javascript.jscomp.graph.DiGraph;
import com.google.javascript.rhino.Node;
import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.IOException;
import java.util.*;
public class AstGetTest {

	public static void main(String[] args) throws IOException {
		findOnInput();
	}

	private static void printFunctions() {
		List<SourceFile> sources = getSourceFiles(Arrays.asList(new String[] {"switch_node_code.js"}));
		CompilerOptions options = getCompilerOptions();

		com.google.javascript.jscomp.Compiler compiler = new Compiler();
		compiler.compile(new ArrayList<SourceFile>(), sources, options);
		normailizeAst(compiler);
//		printAst(compiler.getRoot());
		int nodeCount = 0;
		NodeTraversal findFunction = new NodeTraversal(compiler, new NodeTraversal.AbstractPreOrderCallback() {
			@Override
			public boolean shouldTraverse(NodeTraversal nodeTraversal, Node node, Node node1) {
				if (node.isFunction())
				{
					if (node.getFirstChild() != null && node.getFirstChild().isName()) {
						System.out.println(node.getFirstChild().getString());
						System.out.println("name");
					} else {
						System.out.println("<name not found>");
					}
				}

				return true;
			}

		});
		findFunction.traverse(compiler.getJsRoot());
	}

	/**
		First try to find the on input call. Is not working.
	 */
	private static void findOnInput() {

		List<SourceFile> sources = getSourceFiles(Arrays.asList(new String[] {"switch_node_code.js"}));
		CompilerOptions options = getCompilerOptions();

		com.google.javascript.jscomp.Compiler compiler = new Compiler();
		compiler.compile(new ArrayList<SourceFile>(), sources, options);
		normailizeAst(compiler);
		NodeTraversal findOnInput = new NodeTraversal(compiler, new NodeTraversal.Callback() {
			@Override
			public boolean shouldTraverse(NodeTraversal nodeTraversal, Node node, Node node1) {
				if (node.isCall() && node.hasChildren()) {
					if (node.getFirstChild().isGetProp()) {
						Node getProp = node.getFirstChild();
						Node firstArgument = node.getChildAtIndex(1);
						Node secondArgument = node.getChildAtIndex(2);
						System.out.println(getProp);
						System.out.println(getProp.getFirstChild());
						System.out.println(getProp.getChildAtIndex(1));
						System.out.println(firstArgument.getString());
						System.out.println(secondArgument);
						if (getProp.hasMoreThanOneChild() && getProp.getFirstChild().isThis() && getProp.getChildAtIndex(1).isString() && getProp.getChildAtIndex(1).getString() == "on") {
							if (firstArgument.isString()) {
								System.out.println(firstArgument.getString());
							}
						}
					}
//					System.out.println(Lists.newArrayList(node.children()).stream().map(n -> n.toString()).collect(Collectors.joining(" ")));
//					System.out.println(node.getFirstChild());
//					System.out.println(node1);
//					System.out.println(node1.getParent());
//					System.out.println(Lists.newArrayList(node1.getParent().children()).stream().map(n->n.toString()).collect(Collectors.joining(" ")));
//					System.out.println(node.getChildAtIndex(1).isString());
				}
				return true;
			}

			@Override
			public void visit(NodeTraversal nodeTraversal, Node node, Node node1) {

			}
		});
		findOnInput.traverse(compiler.getJsRoot());
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
