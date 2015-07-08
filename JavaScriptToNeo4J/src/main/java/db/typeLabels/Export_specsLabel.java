package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Export_specsLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_EXPORT_SPECS";
	}
}