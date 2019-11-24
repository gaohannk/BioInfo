package module2.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import static common.PrintUtils.printListByLine;
import static module2.week1.Problem5.DeBruijnGraphFromkmersProblem;
import static module2.week2.Problem1.EulerianCycle;
import static module2.week2.Problem3.PathToGenome;

public class Problem4 {
    /**
     * Code Challenge: Solve the k-Universal Circular String Problem.
     * Input: An integer k.
     * Output: A k-universal circular string.
     */
    private static String kUniversalCircularStringProblem(int k) {
        List<String> binaryStrings = generate(2, k);
        Map<String, List<String>> graph = DeBruijnGraphFromkmersProblem(binaryStrings);
        List<String> path = EulerianCycle(graph);
        for (int i = 0; i < k - 1; i++) {
            path.remove(path.size() - 1);
        }
        printListByLine(path);
        return PathToGenome(path);
    }

    public static List<String> generate(int n, int len) {
        List<String> binaryStrings = new ArrayList<>();
        helper(binaryStrings, new StringBuilder(), 0, n, 0, len);
        return binaryStrings;
    }

    private static void helper(List<String> binaryStrings, StringBuilder data, int start, int end, int index, int len) {
        if (index == len) {
            binaryStrings.add(data.toString());
            return;
        }
        for (int i = start; i < end; i++) {
            data.append(i);
            helper(binaryStrings, data, start, end, index + 1, len);
            data.deleteCharAt(data.length() - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("./resource/module2/dataset_203_11.txt"), Charset.forName("UTF-8"));
        int k = Integer.parseInt(file.replace("\n", ""));
        String res = kUniversalCircularStringProblem(k);
        System.out.println(res);
    }
}
