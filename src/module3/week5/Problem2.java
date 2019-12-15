package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;

public class Problem2 {

	/**
	 * CycleToChromosome(Nodes)
	 *      for j ← 1 to |Nodes|/2
	 *           if Nodes2j−1 < Nodes2j
	 *                Chromosomej ← Nodes2j /2
	 *           else
	 *                Chromosomej ← −Nodes2j−1/2
	 *      return Chromosome
	 */
	/** <p>
	 * Code Challenge: Implement CycleToChromosome.
	 * <p>
	 * Input: A sequence Nodes of integers between 1 and 2n.
	 * Output: The chromosome Chromosome containing n synteny blocks resulting from applying CycleToChromosome to Nodes.
	 */
	public static List<Integer> CycleToChromosome(List<Integer> input) {
		List<Integer> chromosome = new LinkedList<>();
		for (int i = 0; i < input.size() / 2; i++) {
			if (input.get(2 * i) < input.get(2 * i + 1)) {
				chromosome.add(input.get(2 * i + 1) / 2);
			} else {
				chromosome.add(-1 * input.get(2 * i) / 2);
			}
		}
		return chromosome;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_8222_5.txt"), Charset.forName("UTF-8"));
		String[] input = text.replace("\n", "")
				.replace("(", "")
				.replace(")", "")
				.split(" ");
		List<Integer> chromosome = CycleToChromosome(Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList()));
		System.out.print("(");
		System.out.print(chromosome.stream().map(e -> e > 0 ? "+" + e : ""+ e).collect(Collectors.joining(" ")));
		System.out.print(")");
	}

}
