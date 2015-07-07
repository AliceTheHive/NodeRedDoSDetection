package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Member_variable_defLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_MEMBER_VARIABLE_DEF";
	}
}