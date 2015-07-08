import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Created by Per on 08.07.2015.
 */
public class ExampleUsage {

	public static void main(String[] args) {
		Compiler compiler = Compiler.saveAstToDatabase(Arrays.asList(new String[]{"switch_node_code.js"}), "database");
		compiler.validateAstInDatabase();
		compiler.shutdown();
		System.out.println("hello");
	}
}
