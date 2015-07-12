import com.google.common.collect.Lists;
import com.google.javascript.jscomp.ControlFlowGraph;
import com.google.javascript.jscomp.graph.DiGraph;
import com.google.javascript.rhino.Node;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Utils {

	public static void printAst(com.google.javascript.rhino.Node root) {
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

	public static void printCfg(ControlFlowGraph<Node> cfg) {

		Collection<DiGraph.DiGraphNode<Node, ControlFlowGraph.Branch>> nodes = cfg.getNodes();
		List<DiGraph.DiGraphNode<Node, ControlFlowGraph.Branch>> visitedNodes = new ArrayList<>();

		for (DiGraph.DiGraphNode<Node, ControlFlowGraph.Branch> node : nodes) {
			if (node.getInEdges().size() == 0) {
				printRecursive(cfg, visitedNodes, node, 0);
			}
		}
//
//		Stack<DiGraph.DiGraphEdge<Node, ControlFlowGraph.Branch>> edgesToVisit = new Stack<>();
//		for (DiGraph.DiGraphNode<Node, ControlFlowGraph.Branch> node : nodes) {
//			if (node.getInEdges().size() == 0) {
//				for (DiGraph.DiGraphEdge<Node, ControlFlowGraph.Branch> edge : node.getOutEdges()) {
//					edgesToVisit.push(edge);
//				}
//			}
//		}
//
//
//		int ident = 0;
//		boolean sourceAlreadyPrinted = false;
//		while (!edgesToVisit.isEmpty()) {
//
//			DiGraph.DiGraphEdge<Node, ControlFlowGraph.Branch> edge = edgesToVisit.pop();
//			if (edge == null) {
//				ident--;
//				sourceAlreadyPrinted = true;
//				continue;
//			}
//			if (!sourceAlreadyPrinted) {
//				System.out.println(edge.getSource().toString());
//			}
//			for (int i = 0; i < ident; i++) {
//				System.out.print("    ");
//			}
//			System.out.print(edge.getValue().toString());
//			System.out.print("-->");
//			if (cfg.isImplicitReturn(edge.getDestination())) {
//				System.out.println("Return");
//			} else {
//
//				System.out.println(edge.getDestination().toString());
//			}
//
//			if (!edge.getDestination().getOutEdges().isEmpty()) {
//				ident++;
//				edgesToVisit.push(null);
//				sourceAlreadyPrinted = false;
//				for (DiGraph.DiGraphEdge<Node, ControlFlowGraph.Branch> childEdge : edge.getDestination().getOutEdges()) {
//					edgesToVisit.push(childEdge);
//				}
//			}
//		}
	}


	private static void printRecursive(ControlFlowGraph<Node> cfg, List<DiGraph.DiGraphNode<Node, ControlFlowGraph.Branch>> visitedNodes, DiGraph.DiGraphNode<Node, ControlFlowGraph.Branch> node, int ident) {
		if (cfg.isImplicitReturn(node)) {
			System.out.println("return");
		} else {
			System.out.println(node.toString());
		}


		if (!visitedNodes.contains(node)) {
			visitedNodes.add(node);
			for (DiGraph.DiGraphEdge<Node, ControlFlowGraph.Branch> edge : node.getOutEdges()) {
				for (int i = 0; i < ident+1; i++) {
					System.out.print("    ");
				}
				System.out.print(edge.getValue().toString());
				System.out.print("-->");
				printRecursive(cfg, visitedNodes, edge.getDestination(), ident+1);
			}
		}
	}


	public static void deleteFolder(File file)
			throws IOException {
		if (file.isDirectory()) {
			//directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {

				//list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					//construct the file structure
					File fileDelete = new File(file, temp);

					//recursive delete
					deleteFolder(fileDelete);
				}

				//check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			//if file, then delete it
			file.delete();
		}
	}
}
