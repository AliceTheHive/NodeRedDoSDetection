package compiler;

import com.google.javascript.jscomp.NodeTraversal;
import com.google.javascript.rhino.Node;
import db.*;
import db.Labels.AstNodeLabel;
import db.Labels.AstRootLabel;
import db.Labels.AstTypeLabels;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;

import java.util.HashMap;
import java.util.stream.Collectors;

public class SaveNodeToDatabaseCallback extends NodeTraversal.AbstractPreOrderCallback {

	private GraphDatabaseService db;

	private HashMap<Node, org.neo4j.graphdb.Node> compilerToDbNodeMap = new HashMap<>();

	public SaveNodeToDatabaseCallback(GraphDatabaseService db) {
		this.db = db;
	}

	@Override
	public boolean shouldTraverse(NodeTraversal nodeTraversal, Node node, Node parent) {
		org.neo4j.graphdb.Node dbNode = db.createNode();
		dbNode.addLabel(new AstNodeLabel());
		if (parent == null) {
			dbNode.addLabel(new AstRootLabel());
		}
		dbNode.addLabel(AstTypeLabels.typeToLabel(node.getType()));
		dbNode.setProperty(Properties.AST_TYPE, node.getType());

		compilerToDbNodeMap.put(node, dbNode);

		if (parent != null) {
			org.neo4j.graphdb.Node dbParent =  compilerToDbNodeMap.get(parent);
			Relationship parentRelationship = dbParent.createRelationshipTo(dbNode, RelationshipTypes.AST_PARENT_OF);

			int childRank = parent.getIndexOfChild(node);
			parentRelationship.setProperty(Properties.AST_CHILD_RANK, childRank);
		}
		return true;
	}

	public HashMap<Node, Long> getCompilerToDbNodeMap() {
		HashMap<Node, Long> compilerToDbNodeIdMap = new HashMap<>();
		for ( Node node : compilerToDbNodeMap.keySet()) {
			compilerToDbNodeIdMap.put(node, compilerToDbNodeMap.get(node).getId());
		}
		return compilerToDbNodeIdMap;
	}
}
