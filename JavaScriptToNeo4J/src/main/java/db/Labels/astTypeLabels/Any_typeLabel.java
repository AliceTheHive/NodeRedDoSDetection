package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Any_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ANY_TYPE";
	}
}