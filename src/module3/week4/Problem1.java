package module3.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;

public class Problem1 {

    public static int approxReversalDistance = 0;

    /**
     * Code Challenge: Implement GreedySorting.
     *
     *     Input: A permutation P.
     *     Output: The sequence of permutations corresponding to applying GreedySorting to P, ending with the identity permutation.
     */
    /**
     * GreedySorting(P)
     * approxReversalDistance ← 0
     * for k = 1 to |P|
     * if element k is not sorted
     * apply the k-sorting reversal to P
     * approxReversalDistance ← approxReversalDistance + 1
     * if k-th element of P is −k
     * apply the k-sorting reversal to P
     * approxReversalDistance ← approxReversalDistance + 1
     * return approxReversalDistance
     */
    public static List<List<Integer>> GreedySorting(List<Integer> input) {
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 1; i <= input.size(); i++) {
            if (Math.abs(input.get(i - 1)) != i) {
                int pos = input.indexOf(i) != -1 ? input.indexOf(i) : input.indexOf(-1 * i);
                swap(input, i - 1, pos);
                res.add(List.copyOf(input));
                approxReversalDistance++;
            }
            if (input.get(i - 1) == -i) {
                input.remove(i - 1);
                input.add(i - 1, i);
                res.add(List.copyOf(input));
                approxReversalDistance++;
            }
        }
        return res;
    }

    public static void swap(List<Integer> list, int start, int end) {
        for (int i = start; i <= end; i++) {
            int value = list.remove(i);
            list.add(i, -value);
        }
        while (start < end) {
            Collections.swap(list, start++, end--);
        }
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_286_4.txt"), Charset.forName("UTF-8"));
        String[] input = text.replace("\n", "").split(" ");
        List<Integer> list = new LinkedList<>();
        for (String x : input) {
            list.add(Integer.parseInt(x));
        }
        Function<? super List<Integer>, ?> mapListToString = new Function<List<Integer>, String>() {
            @Override
            public String apply(List<Integer> integers) {
                StringBuilder sb = new StringBuilder();
                for (Integer v : integers) {
                    sb.append(v > 0 ? "+" + v :  v);
                    sb.append(" ");
                }
                return sb.substring(0, sb.length()- 1);
            }
        };
        printListByLine(GreedySorting(list).stream().map(mapListToString).collect(Collectors.toList()));
    }

}
