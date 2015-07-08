package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Label_nameLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_LABEL_NAME";
	}
}