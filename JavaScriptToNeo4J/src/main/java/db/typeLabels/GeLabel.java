package db.typeLabels;

import org.neo4j.graphdb.Label;

public class GeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_GE";
	}
}