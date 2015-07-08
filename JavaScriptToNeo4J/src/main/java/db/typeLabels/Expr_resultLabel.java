package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Expr_resultLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_EXPR_RESULT";
	}
}