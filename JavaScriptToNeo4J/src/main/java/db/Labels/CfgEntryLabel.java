package db.Labels;

import org.neo4j.graphdb.Label;

public class CfgEntryLabel implements Label{
	@Override
	public String name() {
		return "CFG entry";
	}
}
