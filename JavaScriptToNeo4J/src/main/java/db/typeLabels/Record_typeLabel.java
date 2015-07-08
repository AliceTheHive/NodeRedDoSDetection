package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Record_typeLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_RECORD_TYPE";
	}
}