package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Undefined_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_UNDEFINED_TYPE";
	}
}