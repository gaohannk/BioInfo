package module2.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Problem6 {


    /**
     * String Spelled by a Gapped Genome Path Problem: Reconstruct a sequence of (k, d)-mers corresponding to a path in a paired de Bruijn graph.
     *      Input: A sequence of (k, d)-mers (a1|b1), ..., (an|bn) such that Suffix((ai|bi)) = Prefix((ai+1|bi+1)) for 1 ≤ i ≤ n - 1.
     *      Output: A string Text of length k + d + k + n - 1 such that the i-th (k, d)-mer of Text is equal to (ai|bi) for 1 ≤ i ≤ n (if such a string
     *      exists).
     *  StringSpelledByGappedPatterns(GappedPatterns, k, d)
     *         FirstPatterns ← the sequence of initial k-mers from GappedPatterns
     *         SecondPatterns ← the sequence of terminal k-mers from GappedPatterns
     *         PrefixString ← StringSpelledByPatterns(FirstPatterns, k)
     *         SuffixString ← StringSpelledByPatterns(SecondPatterns, k)
     *         for i = k + d + 1 to |PrefixString|
     *             if the i-th symbol in PrefixString does not equal the (i - k - d)-th symbol in SuffixString
     *                 return "there is no string spelled by the gapped patterns"
     *         return PrefixString concatenated with the last k + d symbols of SuffixString
     * @throws IOException
     */
    /**
     * GACC||GCGC
     * ACCG||CGCC
     * CCGA||GCCG
     * CGAG||CCGG
     * GAGC||CGGA
     *
     * @param k
     * @param d
     * @return
     */
    public static String StringSpelledByGappedPatterns(List<Pair<String>> pairReads, int k, int d) throws Exception {
        String prefix = PrefixStringSpelledByPatterns(pairReads, k, d);
        String surfix = SurfixStringSpelledByPatterns(pairReads, k, d);
        for (int i = k + d; i < prefix.length(); i++) {
            if (prefix.charAt(i) != surfix.charAt(i - k - d)) {
                throw new Exception();
            }
        }
        return prefix + surfix.substring(surfix.length() - k - d);
    }

    public static String PrefixStringSpelledByPatterns(List<Pair<String>> pairReads, int k, int d) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < pairReads.size() - 1; i++) {
            //System.out.println(pairReads.get(i).read1);
            res.append(pairReads.get(i).read1.charAt(0));
        }
        res.append(pairReads.get(pairReads.size() - 1).read1);
        return res.toString();
    }

    public static String SurfixStringSpelledByPatterns(List<Pair<String>> pairReads, int k, int d) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < pairReads.size() - 1; i++) {
            res.append(pairReads.get(i).read2.charAt(0));
        }
        res.append(pairReads.get(pairReads.size() - 1).read2);
        return res.toString();
    }


    public static void main(String[] args) throws Exception {
        String file = Files.readString(Path.of("./resource/module2/dataset_6206_4.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        int k = Integer.parseInt(splits[0].split(" ")[0]);
        int d = Integer.parseInt(splits[0].split(" ")[1]);

        List<Pair<String>> pairReads = new LinkedList<>();
        for (int i = 1; i < splits.length; i++) {
            String read1 = splits[i].split("\\|")[0];
            String read2 = splits[i].split("\\|")[1];
            //System.out.println(read1+":"+read2);
            pairReads.add(new Pair<>(read1, read2));
        }
        System.out.println(StringSpelledByGappedPatterns(pairReads, k, d));
    }
}
