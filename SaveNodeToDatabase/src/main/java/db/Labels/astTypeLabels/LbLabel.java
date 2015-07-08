package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class LbLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_LB";
	}
}