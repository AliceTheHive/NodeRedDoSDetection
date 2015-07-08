package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Number_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_NUMBER_TYPE";
	}
}