package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module3.week5.Problem3.ColoredEdges;
import static module3.week5.Problem4.GraphToGenome;
import static module3.week5.Problem5.TwoBreakOnGenomeGraph;

public class Problem8 {
    /**
     2-Break Sorting Problem: Find a shortest transformation of one genome into another by 2-breaks.

     Input: Two genomes with circular chromosomes on the same set of synteny blocks.
     Output: The sequence of genomes resulting from applying a shortest sequence of 2-breaks transforming one genome into the other.
     */
    /**ShortestRearrangementScenario(P, Q)
     output P
     RedEdges ← ColoredEdges(P)
     BlueEdges ← ColoredEdges(Q)
     BreakpointGraph ← the graph formed by RedEdges and BlueEdges
     while BreakpointGraph has a non-trivial cycle Cycle
     (i2,i3)<-An arbitrary edge from BlueEdges in a non trivial red-blue cycle
     (i1,i2)<-An edge from RedEdges originating at node i1
     (i3,i4)<-an edge from RedEdges originating at node i3
     RedEdges ← RedEdges with edges (i1, i2) and (i3, i4) removed
     RedEdges ← RedEdges with edges (i2, i3) and (i4, i1) added
     BreakpointGraph ← the graph formed by RedEdges and BlueEdges
     P ← 2-BreakOnGenome(P, i1 , i3 , i2 , i4 )
     output P
     **/
    public static List<List<List<Integer>>> TwoBreakSorting(List<List<Integer>> P, List<List<Integer>> Q) {
        return null;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_288_4.txt"), StandardCharsets.UTF_8);
        String[] splits = text.split("\n");
        String[] inputP = splits[0].trim()
                .substring(1, splits[0].length() - 1)
                .split("\\)\\(");
        List<List<Integer>> genomeP = Arrays.stream(inputP)
                .map(e -> Arrays.stream(e.split(" ")).map(Integer::parseInt).collect(Collectors.toList()))
                .collect(Collectors.toList());
        String[] inputQ = splits[1].trim()
                .substring(1, splits[1].length() - 1)
                .split("\\)\\(");
        List<List<Integer>> genomeQ = Arrays.stream(inputQ)
                .map(e -> Arrays.stream(e.split(" ")).map(Integer::parseInt).collect(Collectors.toList()))
                .collect(Collectors.toList());
        System.out.println(TwoBreakSorting(genomeP, genomeQ));
    }
}
