package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Computed_propLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_COMPUTED_PROP";
	}
}