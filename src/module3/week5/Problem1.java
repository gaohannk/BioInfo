package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;
import static common.PrintUtils.printListInOneline;

public class Problem1 {

	/**
	 * ChromosomeToCycle(Chromosome)
	 *      for j ← 1 to |Chromosome|
	 *           i ← Chromosomej
	 *           if i > 0
	 *                Nodes2j−1 ←2i−1
	 *                Nodes2j ← 2i
	 *           else
	 *                Nodes2j−1 ← -2i
	 *                Nodes2j ←-2i−1
	 *      return Nodes
	 */
	/**
	 * Code Challenge: Implement ChromosomeToCycle.
	 * Input: A chromosome Chromosome containing n synteny blocks.
	 * Output: The sequence Nodes of integers between 1 and 2n resulting from applying ChromosomeToCycle to Chromosome.
	 */
	public static List<Integer> ChromosomeToCycle(List<Integer> input) {
		List<Integer> cycle = new LinkedList<>();
		for (int i = 0; i < input.size(); i++) {
            Integer chromosome = input.get(i);
            if (chromosome > 0) {
				cycle.add(2 * chromosome - 1);
				cycle.add(2 * chromosome);
			} else {
				cycle.add(-2 * chromosome);
				cycle.add(-2 * chromosome - 1);
			}
		}
		return cycle;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_8222_4.txt"), Charset.forName("UTF-8"));
		String[] input = text.replace("\n", "")
				.replace("(", "")
				.replace(")", "")
				.split(" ");
        System.out.print("(");
		printListInOneline(ChromosomeToCycle(Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList())));
        System.out.print(")");
	}
}
