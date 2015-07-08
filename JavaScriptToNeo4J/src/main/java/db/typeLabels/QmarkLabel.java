package db.typeLabels;

import org.neo4j.graphdb.Label;

public class QmarkLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_QMARK";
	}
}