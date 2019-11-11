package module1.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;
import static module1.week3.Week3.*;

public class Week4 {
    public static Random rand = new Random();

    /*
    RandomizedMotifSearch(Dna, k, t)
        randomly select k-mers Motifs = (Motif1, …, Motift) in each string from Dna
        BestMotifs ← Motifs
        while forever
            Profile ← Profile(Motifs)
            Motifs ← Motifs(Profile, Dna)
            if Score(Motifs) < Score(BestMotifs)
                BestMotifs ← Motifs
            else
                return BestMotifs
     */
    public static List<String> RandomizedMotifSearch(List<String> DNA, int k, int t) {
        List<String> motifs = initMotifs(DNA, k, t);
        List<String> bestMotifs = motifs;
        while (true) {
            double[][] profile = formProfileFromMotifWithPseudocounts(motifs);
            motifs = motifs(profile, DNA, k);
            if (score(motifs) < score(bestMotifs))
                bestMotifs = motifs;
            else
                return bestMotifs;
        }
    }

    private static List<String> initMotifs(List<String> DNA, int k, int t) {
        List<String> motifs = new LinkedList<>();
        for (int i = 0; i < t; i++) {
            int bound = DNA.get(i).length() - k + 1;
            int start = rand.nextInt(bound);
            motifs.add(DNA.get(i).substring(start, start + k));
        }
        return motifs;
    }

    public static List<String> RandomizedMotifSearchMultipleTimes(List<String> DNA, int k, int t, int iterNum) {
        List<String> bestMotifs = null;
        int minScore = Integer.MAX_VALUE;
        for (int i = 0; i < iterNum; i++) {
            List<String> motifs = RandomizedMotifSearch(DNA, k, t);
            if (score(motifs) < minScore) {
                minScore = score(motifs);
                bestMotifs = motifs;
            }
        }
        return bestMotifs;
    }


    private static List<String> motifs(double[][] profile, List<String> dna, int k) {
        List<String> motifs = new LinkedList<>();
        for (int i = 0; i < dna.size(); i++) {
            motifs.add(ProfilemostProbablekmerProblem(dna.get(i), k, profile));
        }
        return motifs;
    }

    /*
     GibbsSampler(Dna, k, t, N)
        randomly select k-mers Motifs = (Motif1, …, Motift) in each string from Dna
        BestMotifs ← Motifs
        for j ← 1 to N
            i ← Random(t)
            Profile ← profile matrix constructed from all strings in Motifs except for Motifi
            Motifi ← Profile-randomly generated k-mer in the i-th sequence
            if Score(Motifs) < Score(BestMotifs)
                BestMotifs ← Motifs
        return BestMotifs
     */
    /*
    We now define a Profile-randomly generated k-mer in a string Text.
    For each k-mer Pattern in Text, compute the probability Pr(Pattern | Profile),
    resulting in n = |Text| - k + 1 probabilities (p1, …, pn).
    These probabilities do not necessarily sum to 1, but we can still form the random number generator Random(p1, …, pn)
    based on them. GibbsSampler uses this random number generator to select a Profile-randomly generated k-mer at each step: if the die rolls the number i, then we define the Profile-randomly generated k-mer as the i-th k-mer in Text.
     */
    public static List<String> GibbsSampler(List<String> DNA, int k, int t, int N) {
        List<String> motifs = initMotifs(DNA, k, t);
        List<String> bestMotifs = motifs;
        for (int iter = 0; iter < N; iter++) {
            int i = rand.nextInt(t);
            motifs.remove(i);
            double[][] profile = formProfileFromMotifWithPseudocounts(motifs);
            String motifI = ProfileRandomlyGeneratedKmer(DNA.get(i), k, profile);
            motifs.add(i, motifI);
            if (score(motifs) < score(bestMotifs)) {
                bestMotifs = motifs;
            }
        }
        return bestMotifs;
    }

    public static List<String> GibbsSamplerWithRandomizedMotifSearch(List<String> DNA, int k, int t, int N) {
        List<String> motifs = RandomizedMotifSearchMultipleTimes(DNA, k, t, 250);
        List<String> bestMotifs = motifs;
        for (int iter = 0; iter < N; iter++) {
            int i = rand.nextInt(t);
            motifs.remove(i);
            double[][] profile = formProfileFromMotifWithPseudocounts(motifs);
            String motifI = ProfileRandomlyGeneratedKmer(DNA.get(i), k, profile);
            motifs.add(i, motifI);
            if (score(motifs) < score(bestMotifs)) {
                bestMotifs = motifs;
            }
        }
        return bestMotifs;
    }

    public static String ProfileRandomlyGeneratedKmer(String text, int k, double[][] profile) {
        double prob[] = new double[text.length() - k + 1];
        for (int i = 0; i <= text.length() - k; i++) {
            String pattern = text.substring(i, i + k);
            prob[i] = getProbFromProfile(profile, pattern);
        }
        double sum = Arrays.stream(prob).sum();
        List<Double> normProb = Arrays.stream(prob).map(i -> i / sum).boxed().collect(Collectors.toList());
        int id = getWeightedPattern(rand.nextDouble(), normProb);
        return text.substring(id, id + k);
    }

    private static int getWeightedPattern(double rand, List<Double> normProb) {
        double sum = normProb.get(0);
        double pre = 0;
        for (int i = 1; i <= normProb.size(); i++) {
            if (pre <= rand && rand < sum) {
                return i - 1;
            } else {
                sum += normProb.get(i);
                pre = normProb.get(i - 1);
            }
        }
        return 0;
    }

    static List<String> dna = new LinkedList<>();
    static int k;
    static int t;
    static int N;

    public static void main(String[] args) throws IOException {
        //readFromFile();
        readFromStdin();

        printListByLine(GibbsSamplerWithRandomizedMotifSearch(dna, k, t, 250));
    }

    public static void readFromFile() throws IOException {
        String file = Files.readString(Path.of("./dataset_163_4 (4).txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        k = Integer.parseInt(splits[0].split(" ")[0]);
        t = Integer.parseInt(splits[0].split(" ")[1]);
        N = Integer.parseInt(splits[0].split(" ")[2]);
        for (int i = 1; i < splits.length; i++) {
            dna.add(splits[i]);
        }
    }

    private static void readFromStdin() {
        Scanner scan = new Scanner(System.in);
        k = scan.nextInt();
        t = scan.nextInt();
        N = scan.nextInt();
        while(scan.hasNextLine()){
            dna.add(scan.nextLine());
        }
    }
}
