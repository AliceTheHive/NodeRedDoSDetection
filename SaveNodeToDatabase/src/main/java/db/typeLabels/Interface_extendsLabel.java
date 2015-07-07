package db.typeLabels;

import org.neo4j.graphdb.Label;

public class Interface_extendsLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_INTERFACE_EXTENDS";
	}
}