package module3.week5;

import module3.common.UnionFind;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static module3.week5.Problem3.ColoredEdges;

// 1.2
public class Problem7 {
    public static final String DELIMITER = ", ";
    /**
     * Code Challenge: Solve the 2-Break Distance Problem.
     *
     * Input: Genomes P and Q.
     * Output: The 2-break distance d(P, Q).
     */
    /**
     * Sample Input:
     *
     * (+1 +2 +3 +4 +5 +6)
     * (+1 -3 -6 -5)(+2 -4)
     * Sample Output:
     *
     * 3
     */
    /*
    This one is really straightforward, you just generate the colored edges of P with the algorithm of the charging station,
    then same for Q, append them into a single graph (the breakpoint graph), count the number of cycles in it, subtract it from the block number,
    and you are done.
     */
    //(2, 3), (4, 5), (6, 7), (8, 9), (10, 11), (12, 1)
    //(2, 6), (5, 12), (11, 10), (9, 1), (4, 8), (7, 3)
    public static int TwoBreakDistance(List<List<Integer>> P, List<List<Integer>> Q) {
        List<String> colorEdgeP = ColoredEdges(P);
        List<String> colorEdgeQ = ColoredEdges(Q);
        colorEdgeP.addAll(colorEdgeQ);
        colorEdgeQ.addAll(colorEdgeQ);
        UnionFind uf = constructUnionFind(colorEdgeP);
        UnionFind blockUf = constructUnionFind(colorEdgeQ);
        return blockUf.getCount() - uf.getCount();
    }

    private static UnionFind constructUnionFind(List<String> edges) {
        int n = edges.stream().map(x -> Arrays.stream(x.split(DELIMITER))
                .mapToInt(Integer::parseInt).max().getAsInt())
                .max(Comparator.naturalOrder()).get();
        UnionFind uf = new UnionFind(n);
        for (String edge : edges) {
            int p = Integer.parseInt(edge.split(DELIMITER)[0]);
            int q = Integer.parseInt(edge.split(DELIMITER)[1]);
            if (!uf.isConnected(p - 1, q - 1))
                uf.unite(p - 1, q - 1);
        }
        return uf;
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
        System.out.println(TwoBreakDistance(genomeP, genomeQ));
    }
}
