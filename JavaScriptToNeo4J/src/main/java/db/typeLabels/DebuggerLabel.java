package db.typeLabels;

import org.neo4j.graphdb.Label;

public class DebuggerLabel implements Label {
	@Override
	public String name() {
		return "AST_TYPE_DEBUGGER";
	}
}