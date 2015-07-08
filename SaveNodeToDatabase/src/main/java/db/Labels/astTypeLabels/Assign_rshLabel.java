package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Assign_rshLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ASSIGN_RSH";
	}
}