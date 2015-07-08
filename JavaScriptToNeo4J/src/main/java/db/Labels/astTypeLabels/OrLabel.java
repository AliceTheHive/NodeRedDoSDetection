package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class OrLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_OR";
	}
}