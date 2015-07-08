package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Function_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_FUNCTION_TYPE";
	}
}