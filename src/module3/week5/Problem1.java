package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;

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
    public static List<String> ChromosomeToCycle(List<String> input) {

    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_286_4.txt"), Charset.forName("UTF-8"));
        String[] input = text.replace("\n", "").split(" ");
        //GreedySorting(Arrays.stream(input).collect(Collectors.toList()));
        printListByLine(ChromosomeToCycle(Arrays.stream(input).collect(Collectors.toList())));
    }
}
