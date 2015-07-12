package db;

import com.google.javascript.jscomp.ControlFlowGraph;
import org.neo4j.graphdb.RelationshipType;

/**
 * Created by Per on 05.07.2015.
 */
public enum RelationshipTypes implements RelationshipType {
	AST_PARENT_OF, CFG_UNCOND, CFG_ON_TRUE, CFG_ON_FALSE, CFG_ON_EX, CFG_SYN_BLOCK;

	public static RelationshipTypes getCfgRelationshipType(ControlFlowGraph.Branch edgeValue) {
		switch (edgeValue) {
			case UNCOND:
				return CFG_UNCOND;
			case ON_TRUE:
				return CFG_ON_TRUE;
			case ON_FALSE:
				return CFG_ON_FALSE;
			case ON_EX:
				return CFG_ON_EX;
			case SYN_BLOCK:
				return CFG_SYN_BLOCK;
			default:
				throw new IllegalStateException("Unexpected cfg branch type.");
		}

	}
}
