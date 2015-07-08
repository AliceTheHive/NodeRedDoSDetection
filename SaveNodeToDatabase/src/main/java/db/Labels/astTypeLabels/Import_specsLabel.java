package db.Labels.astTypeLabels;

import org.neo4j.graphdb.Label;

public class Import_specsLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_IMPORT_SPECS";
	}
}