package db.typeLabels;

import org.neo4j.graphdb.Label;

public class ForLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_FOR";
	}
}