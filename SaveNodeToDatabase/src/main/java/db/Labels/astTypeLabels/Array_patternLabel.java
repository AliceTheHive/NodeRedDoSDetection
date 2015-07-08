package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Array_patternLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_ARRAY_PATTERN";
	}
}