package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static module3.week5.Problem1.ChromosomeToCycle;

public class Problem3 {

	/**
	 * ColoredEdges(P)
	 *      Edges ← an empty set
	 *      for each chromosome Chromosome in P
	 *           Nodes ← ChromosomeToCycle(Chromosome)
	 *           for j ← 1 to |Chromosome|
	 *                add the edge (Nodes2j, Nodes2j +1) to Edges
	 *      return Edges
	 */
	/**
	 * Code Challenge: Implement ColoredEdges.
	 * <p>
	 * Input: A genome P.
	 * Output: The collection of colored edges in the genome graph of P in the form (x, y).
	 *
	 * @param genome
	 * @return
	 */
	public static List<String> ColoredEdges(List<List<Integer>> genome) {
		List<String> edges = new LinkedList<>();
		for (List<Integer> chromosome : genome) {
			List<Integer> nodes = ChromosomeToCycle(chromosome);
			nodes.add(nodes.get(0));
			for (int i = 0; i < chromosome.size(); i++) {
				String edge = nodes.get(2 * i + 1) + ", " + nodes.get(2 * i + 2);
				edges.add(edge);
			}
		}
		return edges;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_8222_7.txt"), Charset.forName("UTF-8"));
		String[] input = text.trim()
				.substring(1, text.length() - 2)
				.split("\\)\\(");
		List<List<Integer>> genome = Arrays.stream(input)
				.map(e -> Arrays.stream(e.split(" ")).map(Integer::parseInt).collect(Collectors.toList()))
				.collect(Collectors.toList());
		List<String> edges = ColoredEdges(genome);
		System.out.println(edges.stream().map(e -> "(" + e + ")").collect(Collectors.joining(", ")));
	}
}
