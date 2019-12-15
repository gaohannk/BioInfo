package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Problem5_2 {
	/**
	 2-BreakOnGenomeGraph(GenomeGraph, i1 , i2 , i3 , i4)
	 remove colored edges (i1, i2) and (i3, i4) from GenomeGraph
	 add colored edges (i1, i3) and (i2, i4) to GenomeGraph
	 return GenomeGraph
	 */

	/**
	 * Code Challenge: Implement 2-BreakOnGenomeGraph.
	 * <p>
	 * Input: The colored edges of a genome graph GenomeGraph, followed by indices i1 , i2 , i3 , and i4 .
	 * Output: The colored edges of the genome graph resulting from applying the 2-break operation 2-BreakOnGenomeGraph(GenomeGraph, i1 , i2 , i3 , i4 ).
	 */
	// Pass Grader
	public static List<String> TwoBreakOnGenomeGraph(List<String> coloredEdges, int i1, int i2, int i3, int i4) {
		List<String> afterBreak = new LinkedList<>();

		for (String edge : coloredEdges) {
			int node = Integer.parseInt(edge.split(", ")[0]);
			int node2 = Integer.parseInt(edge.split(", ")[1]);
			if (node == i1 && node2 == i2 || node == i2 && node2 == i1 || node == i3 && node2 == i4 || node == i4 && node2 == i3) {
				continue;
			}
			afterBreak.add(edge);
		}
		afterBreak.add(i1 + ", " + i3);
		afterBreak.add(i2 + ", " + i4);
		return afterBreak;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_8224_2.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		List<String> coloredEdges = Arrays.stream(splits[0].substring(1, splits[0].length() - 1).split("\\), \\(")).collect(Collectors.toList());
		String[] indices = splits[1].split(", ");
		int i1 = Integer.parseInt(indices[0]);
		int i2 = Integer.parseInt(indices[1]);
		int i3 = Integer.parseInt(indices[2]);
		int i4 = Integer.parseInt(indices[3]);
		List<String> afterBreak = TwoBreakOnGenomeGraph(coloredEdges, i1, i2, i3, i4);
		System.out.println(afterBreak.stream().map(e -> "(" + e + ")").collect(Collectors.joining(", ")));
	}
}
