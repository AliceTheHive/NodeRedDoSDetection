package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class ErrorLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ERROR";
	}
}