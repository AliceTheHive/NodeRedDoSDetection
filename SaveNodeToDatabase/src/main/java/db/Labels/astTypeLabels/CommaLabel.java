package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class CommaLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_COMMA";
	}
}