package module1.week1;

import module1.Constant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Week1 {

    public static int patternCount(String text, String pattern) {
        int count = 0;
        int len = pattern.length();
        for (int i = 0; i < text.length() - len; i++) {
            if (text.substring(i, i + len).equals(pattern))
                count++;
        }
        return count;
    }

    public static Set<String> frequentWords(String text, int k) {
        int[] counts = new int[text.length()];
        Set<String> res = new HashSet<>();
        for (int i = 0; i < text.length() - k; i++) {
            String pattern = text.substring(i, i + k);
            counts[i] = patternCount(text, pattern);
        }
        int max = Arrays.stream(counts).max().getAsInt();
        for (int i = 0; i < text.length() - k; i++) {
            if (counts[i] == max) {
                res.add(text.substring(i, i + k));
            }
        }
        return res;
    }

    public static List<String> sortedFrequentWords(String text, int k) {
        return frequentWords(text, k).stream().sorted().collect(Collectors.toList());
    }

    public static String reverseComplement(String input) {
        StringBuilder sb = new StringBuilder();


        for (int i = input.length() - 1; i >= 0; i--) {
            sb.append(Constant.dic.get(input.charAt(i)));
        }
        return sb.toString();
    }

    public static List<Integer> patternMatching(String pattern, String text) {
        List<Integer> res = new LinkedList<>();
        int len = pattern.length();
        for (int i = 0; i < text.length() - len; i++) {
            if (text.substring(i, i + len).equals(pattern))
                res.add(i);
        }
        return res;
    }

    public static int patternToNumber(String pattern) {
        if (pattern.length() == 0) {
            return 0;
        }
        int len = pattern.length();
        return 4 * patternToNumber(pattern.substring(0, len - 1)) + symbolToNumber(pattern.charAt(len - 1));
    }

    public static int symbolToNumber(char symbol) {
        switch (symbol) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
        }
        return 0;
    }

    public static String numberToPattern(int index, int k) {
        if (k == 1)
            return String.valueOf(numberToSymbol(k));
        int quotient = index;
        StringBuilder sb = new StringBuilder(k);
        while (quotient > 0) {
            int remain = quotient % 4;
            quotient = quotient / 4;
            sb.append(numberToSymbol(remain));
        }
        sb.append("A".repeat(k - sb.length()));
        return sb.reverse().toString();
    }

    private static char numberToSymbol(int k) {
        switch (k) {
            case 0:
                return 'A';
            case 1:
                return 'C';
            case 2:
                return 'G';
            case 3:
                return 'T';
        }
        return 0;
    }

    public static int[] computingFrequency(String text, int k) {
        int[] freqArray = new int[(int) Math.pow(4, k)];
        for (int i = 0; i <= text.length() - k; i++) {
            int id = patternToNumber(text.substring(i, i + k));
            freqArray[id]++;
        }
        return freqArray;
    }

    /**
     * Not include the first and last char.
     * @param text
     * @param k
     * @return
     */
    public static int[] computingFrequencyWithinInterval(String text, int k) {
        int[] freqArray = new int[(int) Math.pow(4, k)];
        for (int i = 1; i < text.length() - k; i++) {
            int id = patternToNumber(text.substring(i, i + k));
            freqArray[id]++;
        }
        return freqArray;
    }

    public static Set<String> fasterFrequentWords(String text, int k) {
        int[] freqArray = computingFrequency(text, k);
        int max = Arrays.stream(freqArray).max().getAsInt();
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < Math.pow(4, k); i++) {
            if (freqArray[i] == max)
                set.add(numberToPattern(i, k));
        }
        return set;
    }

    public static Set<String> fasterFrequentWordsAtLeastTtimes(String text, int k, int t) {
        int[] freqArray = computingFrequency(text, k);
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < Math.pow(4, k); i++) {
            if (freqArray[i] >= t)
                set.add(numberToPattern(i, k));
        }
        return set;
    }

    public static Set<String> clumpFindingProblem(String genome, int k, int L, int t) {
        HashSet<String> set = new HashSet<>();
        int[] clump = new int[(int) (Math.pow(4, k))];
        for (int i = 0; i < genome.length() - L; i++) {
            String text = genome.substring(i, i + L);
            int[] freqArry = computingFrequency(text, k);
            for (int index = 0; index < Math.pow(4, k) - 1; index++) {
                clump[index] = freqArry[index] >= t ? 1 : 0;

            }
        }
        for (int i = 0; i < Math.pow(4, k); i++) {
            if (clump[i] == 1) {
                String pattern = numberToPattern(i, k);
                set.add(pattern);
            }
        }
        return set;
    }

    public static Set<String> betterClumpFindingProblem(String genome, int k, int L, int t) {
        HashSet<String> set = new HashSet<>();
        int[] clump = new int[(int) (Math.pow(4, k))];
        String text = genome.substring(0, L);
        int[] freqArry = computingFrequencyWithinInterval(text, k);
        for (int i = 0; i < Math.pow(4, k); i++) {
            clump[i] = freqArry[i] >= t ? 1 : 0;
        }
        for (int i = 1; i <= genome.length() - L; i++) {
            String firstPattern = genome.substring(i - 1, i - 1 + k);
            int index = patternToNumber(firstPattern);
            freqArry[index]--;

            String lastPattern = genome.substring(i + L - k, i + L);
            index = patternToNumber(lastPattern);
            freqArry[index]++;

            clump[index] = freqArry[index] >= t ? 1 : 0;
        }
        for (int i = 0; i < Math.pow(4, k); i++) {
            if (clump[i] == 1) {
                String pattern = numberToPattern(i, k);
                set.add(pattern);
            }
        }
        return set;
    }

    public static List<String> findingFrequentWordsBySorting(String text, int k) {
        List<String> freqWord = new LinkedList<>();
        int[] index = new int[text.length() - k + 1];
        int[] count = new int[text.length() - k + 1];

        for (int i = 0; i < text.length() - k + 1; i++) {
            String pattern = text.substring(i, i + k);
            index[i] = patternToNumber(pattern);
            count[i] = 1;
        }
        Arrays.sort(index);
        for (int i = 1; i < text.length() - k + 1; i++) {
            if (index[i] == index[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }
        int maxCount = Arrays.stream(count).max().getAsInt();
        for (int i = 1; i < text.length() - k + 1; i++) {
            if (count[i] == maxCount) {
                String pattern = numberToPattern(index[i], k);
                freqWord.add(pattern);
            }
        }
        return freqWord;
    }

    public static void main(String[] args) throws IOException {
//        String text = Files.readString(Path.of("./Vibrio_cholerae.txt"), Charset.forName("UTF-8"));
//        String pattern = "CTTGATCAT";
//        List<Integer> res = patternMatching(pattern, text);
//        for (int i = 0; i < res.size(); i++) {
//            System.out.print(res.get(i) + " ");
//        }
        //System.out.print(patternToNumber("CC"));
        //System.out.print(numberToPattern(5437, 8));
//        String text = "CTCTGACTGGGGCGATGTGACATTACCACTACAGGGGATTATCATTCTGCTTTTTTTGCCGCAGGAAATCGACGCATATGCTTCATGGGGAGTAACGAACTCTCCTCTGAATACACGCCCGGAACGAACTCGCTAATAGAGCCAAGGCTTATACAAACGGAGGCCCGCTGATCTCACGCATGCTCCGCGATACGGCACTGCTGGACACCTCTGCCCGAAATTTAACTGGAAATTGCCCAGTACAGAGCTGTATAGTACACGAGTCGCAACGACGGGAGGGATCAAAAAACCCCAGGGCCCAGCTCGTCATGGCAAACTGGATACTGGTACGAACTTCGGACCCCCGAAAGGAGCGTAGCAAGTCCGTTTGTCCATGGACATAAAATCGCTGCTCTTGACTATCACGAATACATTCAACACGTCGTTACCCGGTGTTTTATTTGAGTGCCCGTGCCCTGAGTAGGTCGTCTAGTGGTGTATTCAAGGATTCCAAAATCTCTCTACAAGTATTGATGCGCAGGAAAGGGTCTGGCGTTTGGAGATGCATTTTACCGTAGAGTATACAGCAGAATCTCAACAGTTAAAGAGCCGCCATGACCGATTACCGGAGTTTAATTATCCTTCG";
//        int[] res = computingFrequency(text, 5);
//        for (int i = 0; i < res.length; i++) {
//            System.out.print(res[i] + " ");
//        }
//        String text = Files.readString(Path.of("./E_coli.txt"), Charset.forName("UTF-8"));
//        int k = 9, L = 500, t = 3;
//        long start = System.currentTimeMillis();
//        System.out.println(betterClumpFindingProblem(text, k, L, t).size());
//        System.out.println((System.currentTimeMillis() - start) / 1000);
//        text = "computingFrequencyWithinInterval";
//        System.out.print(fasterFrequentWords(text, 3));
        System.out.println(frequentWords("TAAACGTGAGAGAAACGTGCTGATTACACTTGTTCGTGTGGTAT", 3));
    }
}