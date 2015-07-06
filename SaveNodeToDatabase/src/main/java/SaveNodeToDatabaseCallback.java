import com.google.javascript.jscomp.NodeTraversal;
import com.google.javascript.rhino.Node;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.HashMap;

/**
 * Created by Per on 05.07.2015.
 */
public class SaveNodeToDatabaseCallback implements NodeTraversal.Callback {
	private int ID_PROP = 10000;

	private GraphDatabaseService db;

	private int nodeCounter = 0;

	private HashMap<Long, org.neo4j.graphdb.Node> parentMap = new HashMap<>();

	public SaveNodeToDatabaseCallback(GraphDatabaseService db) {
		this.db = db;
	}

	@Override
	public boolean shouldTraverse(NodeTraversal nodeTraversal, Node node, Node parent) {
		nodeCounter++;
		org.neo4j.graphdb.Node dbNode = db.createNode();
		dbNode.addLabel(new AstNodeLabel());

		node.putProp(ID_PROP, new IdPropertyObject(dbNode.getId()));
		parentMap.put(dbNode.getId(), dbNode);

		if (parent != null) {
			Object parentIdProp = parent.getProp(ID_PROP);
			long parentId = ((IdPropertyObject) parentIdProp).getId();
			org.neo4j.graphdb.Node dbParent =  parentMap.get(parentId);
			dbParent.createRelationshipTo(dbNode, RelationshipTypes.AST_PARENT_OF);
		}
		dbNode.getRelationships()
		return true;
	}

	@Override
	public void visit(NodeTraversal nodeTraversal, Node node, Node parent) {
//		if (parent != null) {
//			IdPropertyObject idObject = (IdPropertyObject) parent.getProp(ID_PROP);
//
//			parentMap.remove(idObject.getId());
//
//		}
	}

	public int getNodeCounter() {
		return nodeCounter;
	}

	private class IdPropertyObject {
		private long id;

		public IdPropertyObject(long id) {
			this.id = id;
		}

		public long getId() {
			return id;
		}
	}
}
