package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Assign_bitandLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ASSIGN_BITAND";
	}
}