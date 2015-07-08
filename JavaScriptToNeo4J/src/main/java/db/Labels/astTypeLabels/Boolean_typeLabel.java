package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Boolean_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_BOOLEAN_TYPE";
	}
}