/**
 * Created by Per on 22.06.2015.
 */
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import com.google.javascript.rhino.InputId;

public class DatabaseTest {

	private static enum RelTypes implements RelationshipType
	{
		KNOWS
	}

	public static void main(String[] args) {
		Node firstNode;
		Node secondNode;
		Relationship relationship;
		InputId test;



		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("database.graphdb");
		System.out.println("Test1");

		try ( Transaction tx = graphDb.beginTx() )
		{
			firstNode = graphDb.createNode();
			firstNode.setProperty( "message", "Hello, " );
			secondNode = graphDb.createNode();
			secondNode.setProperty( "message", "World!" );

			relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
			relationship.setProperty( "message", "brave Neo4j " );
			System.out.print( firstNode.getProperty( "message" ) );
			System.out.print( relationship.getProperty( "message" ) );
			System.out.println( secondNode.getProperty( "message" ) );
			tx.success();
		}


		graphDb.shutdown();
	}
}
