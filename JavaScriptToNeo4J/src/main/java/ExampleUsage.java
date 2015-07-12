
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ExampleUsage {

	public static void main(String[] args) {
		try {
			Utils.deleteFolder(new File("database"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Compiler compiler = Compiler.saveCFGToDatabase(Arrays.asList(new String[]{"test.js"}), "database");
		compiler.validateAstInDatabase();
		compiler.shutdown();
		System.out.println("Done");
	}
}
