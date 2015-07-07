package db.typeLabels;

import org.neo4j.graphdb.Label;

public class ShneLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_SHNE";
	}
}