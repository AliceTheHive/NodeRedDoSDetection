package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class GetpropLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_GETPROP";
	}
}