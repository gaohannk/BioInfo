package module1.week3;

import common.Constant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.Constant.nucle;
import static common.PrintUtils.printListByLine;
import static module1.week1.Week1.numberToPattern;
import static module1.week2.Week2.ApproximatePatternCount;
import static module1.week2.Week2.HammingDistance;
import static module1.week2.Week2.Neighbors;

public class Week3 {
    public static List<String> MotifEnumeration(List<String> DNA, int k, int d) {
        Set<String> motifs = new HashSet<>();
        String text = DNA.get(0);

        for (int i = 0; i <= text.length() - k; i++) {
            String pattern = text.substring(i, i + k);
            List<String> patternNeighbor = Neighbors(pattern, d);
            for (String neighbor : patternNeighbor) {
                boolean flag = true;
                for (String dna : DNA) {
                    if (ApproximatePatternCount(dna, neighbor, d) == 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    motifs.add(neighbor);
                }
            }
        }

        return motifs.stream().collect(Collectors.toList());
    }

    /**
     * MedianString(Dna, k)
     * distance ← ∞
     * for each k-mer Pattern from AA…AA to TT…TT
     * if distance > d(Pattern, Dna)
     * distance ← d(Pattern, Dna)
     * Median ← Pattern
     * return Median
     */
    public static String MedianString(List<String> DNA, int k) {
        String median = null;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < Math.pow(4, k); i++) {
            String pattern = numberToPattern(i, k);
            if (distance > DistanceBetweenPatternAndStrings(pattern, DNA)) {
                distance = DistanceBetweenPatternAndStrings(pattern, DNA);
                median = pattern;
            }
        }
        return median;
    }

    /**
     * List of medianString with all equal minimum distance.
     *
     * @param DNA
     * @param k
     * @return
     */
    public static List<String> MedianStrings(List<String> DNA, int k) {
        List<String> median = new LinkedList<>();
        int dis[] = new int[(int) Math.pow(4, k)];
        for (int i = 0; i < Math.pow(4, k); i++) {
            String pattern = numberToPattern(i, k);
            dis[i] = DistanceBetweenPatternAndStrings(pattern, DNA);

        }
        int minDis = Arrays.stream(dis).min().getAsInt();
        for (int i = 0; i < Math.pow(4, k); i++) {
            if (dis[i] == minDis) {
                median.add(numberToPattern(i, k));
            }
        }
        return median;
    }

    /**
     * k ← |Pattern|
     * distance ← 0
     * for each string Text in Dna
     * HammingDistance ← ∞
     * for each k-mer Pattern’ in Text
     * if HammingDistance > HammingDistance(Pattern, Pattern’)
     * HammingDistance ← HammingDistance(Pattern, Pattern’)
     * distance ← distance + HammingDistance
     * return distance
     */
    public static int DistanceBetweenPatternAndStrings(String pattern, List<String> DNA) {
        int k = pattern.length();
        int totalDistance = 0;
        for (String text : DNA) {
            int hammingDis = Integer.MAX_VALUE;
            for (int i = 0; i <= text.length() - k; i++) {
                String patternPrime = text.substring(i, i + k);
                if (hammingDis > HammingDistance(pattern, patternPrime))
                    hammingDis = HammingDistance(pattern, patternPrime);
            }
            totalDistance += hammingDis;
        }
        return totalDistance;
    }

    public static String ProfilemostProbablekmerProblem(String text, int k, double[][] profile) {
        double maxValue = -1;
        String res = null;
        for (int i = 0; i <= text.length() - k; i++) {
            String pattern = text.substring(i, i + k);
            double prob = getProbFromProfile(profile, pattern);
            if (maxValue < prob) {
                maxValue = prob;
                res = pattern;
            }
        }
        return res;
    }

    public static double getProbFromProfile(double[][] profile, String pattern) {
        double prob = 1.0;
        for (int j = 0; j < pattern.length(); j++) {
            prob *= profile[Constant.INDEX.get(String.valueOf(pattern.charAt(j)))][j];
        }
        return prob;
    }

    /**
     * GreedyMotifSearch(Dna, k, t)
     *         BestMotifs ← motif matrix formed by first k-mers in each string from Dna
     *         for each k-mer Motif in the first string from Dna
     *             Motif1 ← Motif
     *             for i = 2 to t
     *                 form Profile from motifs Motif1, …, Motifi - 1
     *                 Motifi ← Profile-most probable k-mer in the i-th string in Dna
     *             Motifs ← (Motif1, …, Motift)
     *             if Score(Motifs) < Score(BestMotifs)
     *                 BestMotifs ← Motifs
     *         return BestMotifs
     *
     * @param DNA
     * @param k
     * @param t
     * @return
     */
    // t == DNA.size() the number of DNA
    public static List<String> GreedyMotifSearch(List<String> DNA, int k, int t) {
        List<String> bestMotifs = new LinkedList<>();
        for (int i = 0; i < t; i++) {
            bestMotifs.add(DNA.get(i).substring(0, k));
        }

        String firstDna = DNA.get(0);
        for (int i = 0; i <= firstDna.length() - k; i++) {
            List<String> motifs = new LinkedList<>();
            motifs.add(firstDna.substring(i, i + k)); // Get first motif in motifs
            for (int j = 1; j < t; j++) {
                double[][] profile = formProfileFromMotifWithPseudocounts(motifs);
                String nextMotif = ProfilemostProbablekmerProblem(DNA.get(j), k, profile);
                motifs.add(nextMotif);
            }

            if (score(motifs) < score(bestMotifs)) {
                bestMotifs = motifs;
            }
            //System.out.println("Score:"+score(motifs));
        }
        return bestMotifs;
    }

    public static int score(List<String> motifs) {
        int rowNum = motifs.size();
        int colNum = motifs.get(0).length();
        int score = 0;
        for (int col = 0; col < colNum; col++) {
            String[] chars = new String[rowNum];
            for (int row = 0; row < rowNum; row++) {
                chars[row] = motifs.get(row).charAt(col) + "";
            }
            Map<String, Long> freq = Stream.of(chars).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            int max = Integer.MIN_VALUE;
            String mostNucle = null;
            for (String nucle : freq.keySet()) {
                if (freq.get(nucle) > max) {
                    max = freq.get(nucle).intValue();
                    mostNucle = nucle;
                }
            }
            score += rowNum - freq.get(mostNucle);
        }
        return score;
    }


    public static double[][] formProfileFromMotifWithPseudocounts(List<String> motifs) {
        int rowNum = motifs.size();
        int colNum = motifs.get(0).length();
        double[][] profile = new double[4][colNum];

        for (int col = 0; col < colNum; col++) {
            String[] chars = new String[rowNum];
            for (int row = 0; row < rowNum; row++) {
                chars[row] = motifs.get(row).charAt(col) + "";
            }
            Map<String, Long> freq = Stream.of(chars)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            for (int i = 0; i < 4; i++) {
                double count = freq.getOrDefault(String.valueOf(Constant.nucle[i]), 0L) + 1;
                profile[i][col] = count / (rowNum + 4);
            }
        }
        return profile;
    }

    public static double[][] formProfileFromMotif(List<String> motifs) {
        int rowNum = motifs.size();
        int colNum = motifs.get(0).length();
        double[][] profile = new double[4][colNum];

        for (int col = 0; col < colNum; col++) {
            String[] chars = new String[rowNum];
            for (int row = 0; row < rowNum; row++) {
                chars[row] = motifs.get(row).charAt(col) + "";
            }
            Map<String, Long> freq = Stream.of(chars)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            for (int i = 0; i < 4; i++) {
                double count = freq.getOrDefault(String.valueOf(nucle[i]), 0L);
                profile[i][col] = count / rowNum;
            }
        }
        return profile;
    }


    public static void main(String[] args) throws IOException {
//         String file = Files.readString(Path.of("./dataset_159_3 (1).txt"), Charset.forName("UTF-8"));
//         String splits[] = file.split("\n");
//        int k = Integer.parseInt(splits[0].split(" ")[0]);
//        int d = Integer.parseInt(splits[0].split(" ")[1]);
//        List<String> dna = new LinkedList<>();
//        for (int i = 1; i < splits.length; i++) {
//            dna.add(splits[i]);
//        }
//        printListInOneline(MotifEnumeration(dna, k, d));
//        String pattern = splits[0];
//        List<String> dna = Arrays.stream(splits[1].split(" ")).collect(Collectors.toList());
//        System.out.print(DistanceBetweenPatternAndStrings(pattern, dna));

//        int k = Integer.parseInt(splits[0]);
//        List<String> dna = new LinkedList<>();
//        for (int i = 1; i < splits.length; i++) {
//            dna.add(splits[i]);
//        }
//
//        System.out.print(MedianString(dna, k));
//        String text = splits[0];
//        int k = Integer.parseInt(splits[1]);
//        double[][] profile = new double[splits.length - 2][splits[2].split(" ").length];
//        for (int i = 2; i < splits.length; i++) {
//            String[] nums = splits[i].split(" ");
//            for (int j = 0; j < nums.length; j++) {
//               profile[i - 2][j] = Double.parseDouble(nums[j]);
//            }
//        }

//        System.out.print(ProfilemostProbablekmerProblem(text, k, profile));
//
//        String text = "CTCGATGAGTAGGAAAGTAGTTTCACTGGGCGAACCACCCCGGCGCTAATCCTAGTGCCC\n" +
//                "GCAATCCTACCCGAGGCCACATATCAGTAGGAACTAGAACCACCACGGGTGGCTAGTTTC\n" +
//                "GGTGTTGAACCACGGGGTTAGTTTCATCTsATTGTAGGAATCGGCTTCAAATCCTACACAG";
//        List<String> dna = Arrays.stream(text.split("\n")).collect(Collectors.toList());
//        System.out.print(MedianStrings(dna, 7));


        String file = Files.readString(Path.of("./dataset_160_9.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        int k = Integer.parseInt(splits[0].split(" ")[0]);
        int t = Integer.parseInt(splits[0].split(" ")[1]);
        List<String> dna = new LinkedList<>();
        for (int i = 1; i < splits.length; i++) {
            dna.add(splits[i]);
        }
//
//        String text = "GGCGTTCAGGCA\n" +
//                "AAGAATCAGTCA\n" +
//                "CAAGGAGTTCGC\n" +
//                "CACGTCAATCAC\n" +
//                "CAATAATATTCG";
//        List<String> dna = new LinkedList<>();
//        String splits[] = text.split("\n");
//        for (int i = 0; i < splits.length; i++) {
//            dna.add(splits[i]);
//        }
        printListByLine(GreedyMotifSearch(dna, k, t));
    }
}
