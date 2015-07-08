package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class String_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_STRING_TYPE";
	}
}