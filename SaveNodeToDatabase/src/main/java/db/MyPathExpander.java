package db;

import com.google.common.collect.Lists;
import db.Properties;
import db.RelationshipTypes;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.BranchState;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Per on 06.07.2015.
 */
public class MyPathExpander implements PathExpander {
	@Override
	public Iterable<Relationship> expand(Path path, BranchState branchState) {
		Node endNode = path.endNode();
		Iterable<Relationship> relationships = endNode.getRelationships(RelationshipTypes.AST_PARENT_OF, Direction.OUTGOING);
		List<Relationship> relationshipList = Lists.newArrayList(relationships);
		relationshipList.sort(new Comparator<Relationship>() {
			@Override
			public int compare(Relationship o1, Relationship o2) {
				return ((Integer) o1.getProperty(Properties.AST_CHILD_RANK)) - ((Integer)o2.getProperty(Properties.AST_CHILD_RANK));
			}
		});
		return relationshipList;
	}

	@Override
	public PathExpander reverse() {
		return null;
	}
}
