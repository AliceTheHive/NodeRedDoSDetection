package db.Labels;

import org.neo4j.graphdb.Label;

/**
 * Created by Per on 06.07.2015.
 */
public class AstRootLabel implements Label {
	@Override
	public String name() {
		return "AstRoot";
	}
}
