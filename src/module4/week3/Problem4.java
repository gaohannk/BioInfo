package module4.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Problem4 {

	/**
	 * Code Challenge: Implement the nearest neighbor interchange heuristic for the Large Parsimony Problem.
	 * <p>
	 * Input: An integer n, followed by an adjacency list for an unrooted binary tree whose n leaves are labeled by DNA strings and whose internal nodes are labeled by integers.
	 * Output: The parsimony score and unrooted labeled tree obtained after every step of the nearest neighbor interchange heuristic. Each step should be separated by a blank line.
	 * Note: Depending on how your code breaks ties, you may obtain a different solution than the one we provide.  As a result, the parsimony score at each step may vary
	 **/
	public static Node NearestNeighborInterchange(String input) {

		return null;
	}

	private static Map<Integer, Node> constructTree(String[] splits) {
		int id = -1;
		Map<Integer, Node> nodeMap = new HashMap<>();

		return nodeMap;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_10336_6.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int src = Integer.parseInt(splits[0].split(" ")[0]);
		int dest = Integer.parseInt(splits[0].split(" ")[1]);
		Map<Integer, Node> map = constructTree(splits);
	}
}
