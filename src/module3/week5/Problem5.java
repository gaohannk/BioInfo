package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Problem5 {
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
    // Pass Grader, But result not easy to reconstruct genome
    public static List<String> TwoBreakOnGenomeGraph(List<String> input, int i1, int i2, int i3, int i4) {
        List<String> afterBreak = new LinkedList<>();
        int i = 0;
        List<Integer> index = new LinkedList<>();
        List<Integer> breakNode = new LinkedList<>();
        for (String edge : input) {
            int node = Integer.parseInt(edge.split(", ")[0]);
            int node2 = Integer.parseInt(edge.split(", ")[1]);
            if (node == i2 && node2 == i1 || node == i1 && node2 == i2 || node == i4 && node2 == i3 || node == i3 && node2 == i4) {
                breakNode.add(node);
                breakNode.add(node2);
                index.add(i);
                continue;
            }
            afterBreak.add(edge);
            i++;
        }
        boolean reverse = false;
        // 1 6 3 8
        if (breakNode.get(0) == i3 && breakNode.get(1) == i4 && breakNode.get(2) == i2 && breakNode.get(3) == i1) {
            afterBreak.add(index.get(0), i3 + ", " + i1);
            afterBreak.add(index.get(1), i2 + ", " + i4);
            // 6 1 8 3
        } else if (breakNode.get(0) == i4 && breakNode.get(1) == i3 && breakNode.get(2) == i1 && breakNode.get(3) == i2) {
            afterBreak.add(index.get(0), i4 + ", " + i2);
            afterBreak.add(index.get(1), i1 + ", " + i3);
            // 1 6 8 3
        } else if (breakNode.get(0) == i4 && breakNode.get(1) == i3 && breakNode.get(2) == i2 && breakNode.get(3) == i1) {
            afterBreak.add(index.get(0), i4 + ", " + i2);
            afterBreak.add(index.get(1), i3 + ", " + i1);
            reverse = true;
            // 6 1 3 8
        } else if (breakNode.get(0) == i3 && breakNode.get(1) == i4 && breakNode.get(2) == i1 && breakNode.get(3) == i2) {
            afterBreak.add(index.get(0), i3 + ", " + i4);
            afterBreak.add(index.get(1), i1 + ", " + i2);
            reverse = true;
        }
        if (reverse) {
//            for (int j = index.get(0) + 1; j < index.get(1); j++) {
//                reverseEdge(afterBreak, j);
//            }
        }
        return afterBreak;
    }

    private static void reverseEdge(List<String> afterBreak, int j) {
        String edge = afterBreak.get(j);
        String reverseEdge = edge.split(", ")[0] + ", "+ edge.split(", ")[1];
        afterBreak.remove(j);
        afterBreak.add(j, reverseEdge);
    }


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_8224_2.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        List<String> input = Arrays.stream(splits[0].substring(1, splits[0].length() - 1).split("\\), \\(")).collect(Collectors.toList());
        String[] indices = splits[1].split(", ");
        int i1 = Integer.parseInt(indices[0]);
        int i2 = Integer.parseInt(indices[1]);
        int i3 = Integer.parseInt(indices[2]);
        int i4 = Integer.parseInt(indices[3]);
        List<String> afterBreak = TwoBreakOnGenomeGraph(input, i1, i2, i3, i4);
        System.out.println(afterBreak.stream().map(e -> "(" + e + ")").collect(Collectors.joining(", ")));
    }

}
