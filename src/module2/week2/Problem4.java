package module2.week2;

import java.util.ArrayList;

import java.util.List;

import static common.PrintUtils.printListByLine;
import static module2.week2.Problem3.StringReconstruction;

public class Problem4 {
    /**
     * Code Challenge: Solve the k-Universal Circular String Problem.
     * Input: An integer k.
     * Output: A k-universal circular string.
     */
    private static String kUniversalCircularStringProblem(int k) {
        List<String> binaryStrings = generate(2, k);
        printListByLine(binaryStrings);
        return StringReconstruction(binaryStrings);
    }

    public static List<String> generate(int n, int len) {
        List<String> binaryStrings = new ArrayList<>();
        helper(binaryStrings, new StringBuilder(), 0, n , 0, len);
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


    public static void main(String[] args) {
        String res = kUniversalCircularStringProblem(4);
        System.out.println(res);
    }
}
