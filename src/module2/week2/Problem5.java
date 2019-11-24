package module2.week2;

import java.io.IOException;
import java.util.*;

public class Problem5 {

    /**
     * String Reconstruction from Read-Pairs Problem: Reconstruct a string from its paired composition.
     * Input: A collection of paired k-mers PairedReads and an integer d.
     * Output: A string Text with (k,d)-mer composition equal to PairedReads (if such a string exists).
     *
     * @throws IOException
     */
    public static Map<String, List<String>> GenerateKDmercomposition(int k, int d, String text) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < text.length() - 2 * k - d + 1; i++) {
            String read1 = text.substring(i, i + k);
            String read2 = text.substring(i + k + d, i + 2 * k + d);
            if (map.containsKey(read1)) {
                map.get(read1).add(read2);
            } else {
                List<String> reads = new LinkedList<>();
                reads.add(read2);
                map.put(read1, reads);
            }
        }
        return map;
    }

    public static String printKDmercomposition(Map<String, List<String>> kdmer) {
        StringBuilder res = new StringBuilder();
        List<String> readPairs = new LinkedList<>();
        for (String read1 : kdmer.keySet()) {
            for (String read2 : kdmer.get(read1)) {
                String readPair = "(" + read1 + "|" + read2 + ")";
                readPairs.add(readPair);
            }
        }
        Collections.sort(readPairs);
        for (String pair : readPairs) {
            res.append(pair);
        }
        return res.toString();
    }

    public static void main(String[] args) throws IOException {
        String text = "TAATGCCATGGGATGTT";
        System.out.println(printKDmercomposition(GenerateKDmercomposition(3, 2, text)));
    }
}
