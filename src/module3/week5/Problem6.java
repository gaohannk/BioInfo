package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module3.week5.Problem3.ColoredEdges;
import static module3.week5.Problem4.GraphToGenome;
import static module3.week5.Problem5.TwoBreakOnGenomeGraph;

public class Problem6 {

	/**
	 * 2-BreakOnGenome(P, i1 , i2 , i3 , i4 )
	 *      GenomeGraph ← BlackEdges(P) and ColoredEdges(P)
	 *      GenomeGraph ← 2-BreakOnGenomeGraph(GenomeGraph, i1 , i2 , i3 , i4 )
	 *      P ← GraphToGenome(GenomeGraph)
	 *      return P
	 */
	/**
	 * Code Challenge: Implement 2-BreakOnGenome.
	 * <p>
	 * Input: A genome P, followed by indices i1 , i2 , i3 , and i4 .
	 * Output: The genome P' resulting from applying the 2-break operation 2-BreakOnGenome(GenomeGraph i1 , i2 , i3 , i4 ).
	 *
	 */
	public static List<List<Integer>> TwoBreakOnGenome(List<List<Integer>> GenomeGraph, int i1, int i2, int i3, int i4) {
		List<String> colorEdge = ColoredEdges(GenomeGraph);
		List<String> afterBreak = TwoBreakOnGenomeGraph(colorEdge, i1, i2, i3, i4);
		System.out.println(afterBreak);
		return GraphToGenome(afterBreak);
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_8224_3.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		List<List<Integer>> genome = List.of(Arrays.stream(splits[0].substring(1, splits[0].length()-1).split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
		String[] indices = splits[1].split(", ");
		int i1 = Integer.parseInt(indices[0]);
		int i2 = Integer.parseInt(indices[1]);
		int i3 = Integer.parseInt(indices[2]);
		int i4 = Integer.parseInt(indices[3]);
		List<List<Integer>> afterBreak = TwoBreakOnGenome(genome, i1, i2, i3, i4);
		System.out.println(afterBreak.stream().map(
				l -> "(" + l.stream().map(e -> e > 0 ? "+" + e : "" + e).collect(Collectors.joining(" ")) + ")"
		).collect(Collectors.joining()));
	}

}
