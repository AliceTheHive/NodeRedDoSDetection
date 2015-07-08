package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Default_caseLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_DEFAULT_CASE";
	}
}