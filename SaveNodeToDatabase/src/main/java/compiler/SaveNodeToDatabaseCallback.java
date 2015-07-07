package compiler;

import com.google.javascript.jscomp.NodeTraversal;
import com.google.javascript.rhino.Node;
import db.AstNodeLabel;
import db.AstRootLabel;
import db.Properties;
import db.RelationshipTypes;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;

import java.util.HashMap;

/**
 * Created by Per on 05.07.2015.
 */
public class SaveNodeToDatabaseCallback implements NodeTraversal.Callback {

	private GraphDatabaseService db;

	private int nodeCounter = 0;

	// I am using object instance equality here on purpose.
	private HashMap<Node, org.neo4j.graphdb.Node> parentMap = new HashMap<>();

	public SaveNodeToDatabaseCallback(GraphDatabaseService db) {
		this.db = db;
	}

	@Override
	public boolean shouldTraverse(NodeTraversal nodeTraversal, Node node, Node parent) {
		nodeCounter++;
		org.neo4j.graphdb.Node dbNode = db.createNode();
		dbNode.addLabel(new AstNodeLabel());
		if (parent == null) {
			dbNode.addLabel(new AstRootLabel());
		}
		dbNode.setProperty(Properties.AST_TYPE, node.getType());

		node.putProp(IdPropertyObject.ID_PROP, new IdPropertyObject(dbNode.getId()));
		parentMap.put(node, dbNode);

		if (parent != null) {
//			Object parentIdProp = parent.getProp(IdPropertyObject.ID_PROP);
//			long parentId = ((IdPropertyObject) parentIdProp).getId();
			org.neo4j.graphdb.Node dbParent =  parentMap.get(parent);
			Relationship parentRelationship = dbParent.createRelationshipTo(dbNode, RelationshipTypes.AST_PARENT_OF);

			int childRank = parent.getIndexOfChild(node);
			parentRelationship.setProperty(Properties.AST_CHILD_RANK, childRank);
		}
		return true;
	}

	@Override
	public void visit(NodeTraversal nodeTraversal, Node node, Node parent) {
//		if (parent != null) {
//			compiler.IdPropertyObject idObject = (compiler.IdPropertyObject) parent.getProp(ID_PROP);
//
//			parentMap.remove(idObject.getId());
//
//		}
	}

	public int getNodeCounter() {
		return nodeCounter;
	}


}
