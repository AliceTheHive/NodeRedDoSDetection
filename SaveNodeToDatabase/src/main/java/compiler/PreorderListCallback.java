package compiler;

import com.google.javascript.jscomp.NodeTraversal;
import com.google.javascript.rhino.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Per on 06.07.2015.
 */
public class PreorderListCallback extends NodeTraversal.AbstractPreOrderCallback {

	public List<Node> getNodes() {
		return nodes;
	}

	private List<com.google.javascript.rhino.Node> nodes = new ArrayList<>();

	@Override
	public boolean shouldTraverse(NodeTraversal nodeTraversal, com.google.javascript.rhino.Node node, com.google.javascript.rhino.Node node1) {
		nodes.add(node);
		return true;
	}
}
