package module1.week2;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constant.nucle;
import static common.PrintUtils.printListInOneline;
import static module1.week1.Week1.numberToPattern;
import static module1.week1.Week1.patternToNumber;
import static module1.week1.Week1.reverseComplement;

public class Week2 {

    public static List<Integer> SkewArray(String text) {
        List<Integer> skew = new LinkedList<>();
        skew.add(0);

        for (int i = 0; i < text.length(); i++) {
            switch (text.charAt(i)) {
                case 'A':
                case 'T':
                    skew.add(skew.get(i));
                    break;
                case 'C':
                    skew.add(skew.get(i) - 1);
                    break;
                case 'G':
                    skew.add(skew.get(i) + 1);
                    break;
            }
        }
        return skew;
    }

    public static List<Integer> MinimumSkew(String text) {
        List<Integer> skew = SkewArray(text);
        List<Integer> res = new LinkedList<>();
        int min = skew.stream().reduce((a, b) -> Math.min(a, b)).get();
        for (int i = 0; i < skew.size(); i++) {
            if (skew.get(i) == min) {
                res.add(i);
            }
        }
        return res;
    }

    public static int HammingDistance(String p, String q) {
        int count = 0;
        for (int i = 0; i < p.length(); i++) {
            if (q.charAt(i) != p.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static List<Integer> ApproximatePatternMatchingProblem(String text, String pattern, int d) {
        List<Integer> res = new LinkedList<>();
        int len = pattern.length();
        for (int i = 0; i <= text.length() - len; i++) {
            String s = text.substring(i, i + len);
            if (HammingDistance(s, pattern) <= d)
                res.add(i);
        }
        return res;
    }

    public static int ApproximatePatternCount(String text, String pattern, int match) {
        int count = 0;
        int len = pattern.length();
        for (int i = 0; i <= text.length() - len; i++) {
            String s = text.substring(i, i + len);
            if (HammingDistance(s, pattern) <= match)
                count++;
        }
        return count;
    }

    public static int[] ComputingFrequenciesWithMismatches(String text, int k, int d) {
        int[] freqArray = new int[(int) Math.pow(4, k)];
        for (int i = 0; i <= text.length() - k; i++) {
            String pattern = text.substring(i, i + k);
            List<String> Neighborhood = Neighbors(pattern, d);
            for (String approximatePattern : Neighborhood) {
                int id = patternToNumber(approximatePattern);
                freqArray[id]++;
            }
        }
        return freqArray;
    }

    public static Set<String> ImmediateNeighbors(String pattern) {
        Set<String> neighborhood = new HashSet<>();
        for (int i = 0; i < pattern.length(); i++) {
            for (char c : nucle) {
                if (c != pattern.charAt(i)) {
                    String neighbor = pattern.substring(0, i) + c + pattern.substring(i + 1);
                    neighborhood.add(neighbor);
                }
            }
        }
        return neighborhood;
    }

    public static List<String> Neighbors(String pattern, int d) {
        List<String> neighborhood = new LinkedList<>();
        if (d == 0) {
            neighborhood.add(pattern);
            return neighborhood;
        }
        if (pattern.length() == 1) {
            neighborhood.add("T");
            neighborhood.add("A");
            neighborhood.add("G");
            neighborhood.add("C");
            return neighborhood;
        }
        for (String neighbor : Neighbors(pattern.substring(1), d)) {
            if (HammingDistance(neighbor, pattern.substring(1)) < d) {
                for (char c : nucle) {
                    neighborhood.add(c + neighbor);
                }
            } else {
                neighborhood.add(pattern.charAt(0) + neighbor);
            }
        }
        return neighborhood;
    }

    public static List<String> FrequentWordsWithMismatches(String text, int k, int d) {
        List<String> freqWord = new LinkedList<>();
        List<String> neighorhood = new LinkedList<>();

        for (int i = 0; i <= text.length() - k; i++) {
            neighorhood.addAll(Neighbors(text.substring(i, i + k), d));
        }
//        form an array NeighborhoodArray holding all strings in Neighborhoods
        int[] index = new int[neighorhood.size()];
        int[] count = new int[neighorhood.size()];
        for (int i = 0; i < neighorhood.size(); i++) {
            index[i] = patternToNumber(neighorhood.get(i));
            count[i] = 1;
        }
        Arrays.sort(index);
        for (int i = 1; i < neighorhood.size(); i++) {
            if (index[i] == index[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }
        int maxCount = Arrays.stream(count).max().getAsInt();
        for (int i = 0; i < neighorhood.size(); i++) {
            if (count[i] == maxCount) {
                String pattern = numberToPattern(index[i], k);
                freqWord.add(pattern);
            }
        }
        return freqWord;
    }
    public static List<String> FrequentWordsWithMismatchesandReverseComplementsProblem(String text, int k, int d) {
        List<String> freqWord = new LinkedList<>();
        List<String> neighorhood = new LinkedList<>();

        for (int i = 0; i <= text.length() - k; i++) {
            neighorhood.addAll(Neighbors(text.substring(i, i + k), d));
            neighorhood.addAll(Neighbors(reverseComplement(text.substring(i, i + k)), d));
        }
        neighorhood = neighorhood.stream().distinct().collect(Collectors.toList());
//        form an array NeighborhoodArray holding all strings in Neighborhoods
        int[] index = new int[neighorhood.size()];
        int[] count = new int[neighorhood.size()];
        for (int i = 0; i < neighorhood.size(); i++) {
            index[i] = patternToNumber(neighorhood.get(i));
            count[i] = 1;
        }
        Arrays.sort(index);
        for (int i = 1; i < neighorhood.size(); i++) {
            if (index[i] == index[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }
        int maxCount = Arrays.stream(count).max().getAsInt();
        for (int i = 0; i < neighorhood.size(); i++) {
            if (count[i] == maxCount) {
                String pattern = numberToPattern(index[i], k);
                freqWord.add(pattern);
            }
        }
        return freqWord;
    }

    public static void main(String[] args) throws IOException {
//        String text = Files.readString(Path.of("./dataset_9_6.txt"), Charset.forName("UTF-8"));
         printListInOneline(MinimumSkew("CATTCCAGTACTTCGATGATGGCGTGAAGA"));
//        String splits[] = text.split("\n");
//        String pattern = splits[0];
//        String gene = splits[1];
//        int match = Integer.parseInt(splits[2]);
//        System.out.print(ApproximatePatternCount(gene, pattern, match));
//         printListInOneline(ApproximatePatternMatchingProblem("AACAAGCTGATAAACATTTAAAGAG", "AAAAA", 2));
//         printListByLine(Neighbors("AATTCACC", 3));
        //String text = "CATTTTACACAGATCAGATTACCAACCATCAGATCTCTCACTTAGAAGATTAGAAGACATCCAACACAGATCAGATCTTTCAGATTCATCTTAGAACTCACACAGATCTTAGATCAGACAAGATTACTTTCACCAACCATCACAGAAGAAGATTAGACACATCAGAACAGAACTCCAAGACACAACTCAGATTAGAAGACAAGACATTTTTTTTAGAACAGA";
        //printListInOneline(FrequentWordsWithMismatchesandReverseComplementsProblem(text, 7, 2));
        String p = "TGACCCGTTATGCTCGAGTTCGGTCAGAGCGTCATTGCGAGTAGTCGTTTGCTTTCTCAAACTCC";
        String q = "GAGCGATTAAGCGTGACAGCCCCAGGGAACCCACAAAACGTGATCGCAGTCCATCCGATCATACA";
        System.out.println(HammingDistance(p,q));
        System.out.println(ApproximatePatternCount("CGTGACAGTGTATGGGCATCTTT", "TGT", 1));
        System.out.println(Neighbors("TGCAT",  2).size());


    }

}
