package db.Labels;

import org.neo4j.graphdb.Label;

/**
 * Created by Per on 05.07.2015.
 */
public class AstNodeLabel implements Label {
	@Override
	public String name() {
		return "AstNode";
	}
}
