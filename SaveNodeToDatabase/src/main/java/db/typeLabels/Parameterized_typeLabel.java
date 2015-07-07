package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Parameterized_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_PARAMETERIZED_TYPE";
	}
}