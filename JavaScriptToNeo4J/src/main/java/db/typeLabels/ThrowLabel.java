package db.typeLabels;

import org.neo4j.graphdb.Label;

public class ThrowLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_THROW";
	}
}