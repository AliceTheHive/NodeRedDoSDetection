package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class DecLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_DEC";
	}
}