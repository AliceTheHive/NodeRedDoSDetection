package db.typeLabels;

import org.neo4j.graphdb.Label;

public class EllipsisLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ELLIPSIS";
	}
}