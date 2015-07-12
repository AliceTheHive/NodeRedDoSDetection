package db.Labels;

import org.neo4j.graphdb.Label;

public class CfgNodeLabel implements Label {
	@Override
	public String name() {
		return "CFG Node";
	}
}
