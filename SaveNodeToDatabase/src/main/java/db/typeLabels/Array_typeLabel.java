package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Array_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ARRAY_TYPE";
	}
}