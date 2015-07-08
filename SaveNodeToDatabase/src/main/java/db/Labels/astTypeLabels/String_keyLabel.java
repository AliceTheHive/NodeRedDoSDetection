package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class String_keyLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_STRING_KEY";
	}
}