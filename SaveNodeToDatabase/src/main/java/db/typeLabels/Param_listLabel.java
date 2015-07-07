package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Param_listLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_PARAM_LIST";
	}
}