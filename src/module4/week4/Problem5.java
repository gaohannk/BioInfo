package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module2.common.Constant.MASSTABLE_REV;

public class Problem5 {

	public static int maxScore = Integer.MIN_VALUE;
	public static List<Node> maxScorePath;

	/**
	 * CODE CHALLENGE: Solve the Peptide Sequencing Problem.
	 * Given: A space-delimited spectral vector Spectrum'.
	 * Return: An amino acid string with maximum score against Spectrum'. For masses
	 * with more than one amino acid, any choice may be used.
	 * <p>
	 * Note: When a spectral vector Spectrum' = s1 ... sm is given, it does not have a zero-th element; in your implementations, you should assume that s0 is equal to zero.
	 */
	public static String PeptideSequencingProblem(List<Integer> spectrum) {
		spectrum.add(0, 0);
		Map<Integer, List<Node>> graph = constructDag(spectrum);
		List<Node> path = new LinkedList<>();
		path.add(new Node(0, 0));
		traversal(graph, 0, 0, path);
		String res = "";
		for (int i = 1; i < maxScorePath.size(); i++) {
			res += MASSTABLE_REV.get(maxScorePath.get(i).id - maxScorePath.get(i - 1).id);
		}
		return res;
	}

	private static void traversal(Map<Integer, List<Node>> graph, int cur, int curScore, List<Node> path) {
		if (graph.get(cur).size() == 0) {
			if (maxScore < curScore) {
				maxScore = curScore;
				maxScorePath = List.copyOf(path);
			}
			return;
		}
		for (Node neibour : graph.get(cur)) {
			curScore += neibour.weight;
			path.add(neibour);
			traversal(graph, neibour.id, curScore, path);
			curScore -= neibour.weight;
			path.remove(neibour);
		}
	}

	static class Node {
		public int id;
		public int weight;

		public Node(int id, Integer weight) {
			this.id = id;
			this.weight = weight;
		}

		public String toString() {
			return "Node{" +
					"id=" + id +
					", weight=" + weight +
					'}';
		}
	}

	public static Map<Integer, List<Node>> constructDag(List<Integer> spectrum) {
		Map<Integer, List<Node>> map = new TreeMap<>();
		List<Integer> iter = new LinkedList<>(spectrum);
		iter.add(0);
		for (int i = 0; i < spectrum.size(); i++) {
			map.put(i, new LinkedList<>());
			for (int mass : MASSTABLE_REV.keySet()) {
				if (i + mass < spectrum.size()) {
					map.get(i).add(new Node(i + mass, spectrum.get(i + mass)));
				}
			}
		}
		return map;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_11813_10.txt"), Charset.forName("UTF-8"));
		List<Integer> spectrum = Arrays.stream(text.split("\n")[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
		System.out.println(PeptideSequencingProblem(spectrum));
		System.out.println(maxScore);
	}
}
