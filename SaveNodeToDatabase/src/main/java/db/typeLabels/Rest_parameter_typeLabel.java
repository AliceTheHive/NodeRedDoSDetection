package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Rest_parameter_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_REST_PARAMETER_TYPE";
	}
}