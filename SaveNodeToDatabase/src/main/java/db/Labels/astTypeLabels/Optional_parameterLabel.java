package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Optional_parameterLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_OPTIONAL_PARAMETER";
	}
}