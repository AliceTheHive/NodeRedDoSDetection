package db.Labels;

import org.neo4j.graphdb.Label;

public class CfgReturnLabel implements Label {
	@Override
	public String name() {
		return "CFG return";
	}
}
