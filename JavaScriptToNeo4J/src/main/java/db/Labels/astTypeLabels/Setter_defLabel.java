package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Setter_defLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_SETTER_DEF";
	}
}