package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Object_patternLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_OBJECT_PATTERN";
	}
}