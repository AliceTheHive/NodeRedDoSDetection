import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Utils {

	public static void printAst(com.google.javascript.rhino.Node root) {
		Stack<com.google.javascript.rhino.Node> nodesToVisit = new Stack<>();
		nodesToVisit.push(root);
		int ident = 0;
		while (!nodesToVisit.isEmpty()) {
			com.google.javascript.rhino.Node node = nodesToVisit.pop();
			if (node == null) {
				ident--;
				continue;
			}

			for (int i = 0; i < ident; i++) {
				System.out.print("    ");
			}
			System.out.println(node.toString());

			List<com.google.javascript.rhino.Node> children = Lists.newArrayList(node.children());
			if (children.size() > 0) {
				ident++;
				nodesToVisit.push(null);
				children = Lists.reverse(children);
				for (com.google.javascript.rhino.Node child : children) {
					nodesToVisit.push(child);
				}
			}
		}
	}

	public static void deleteFolder(File file)
			throws IOException {
		if (file.isDirectory()) {
			//directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {

				//list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					//construct the file structure
					File fileDelete = new File(file, temp);

					//recursive delete
					deleteFolder(fileDelete);
				}

				//check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			//if file, then delete it
			file.delete();
		}
	}
}
