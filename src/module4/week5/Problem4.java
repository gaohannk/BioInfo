package module4.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module2.common.Constant.MASSTABLE_NO_TOY;

public class Problem4 {

    /**
     * CODE CHALLENGE: Solve the Probability of Spectral Dictionary Problem.
     * Given: A spectral vector Spectrum', an integer threshold, and an integer max_score.
     * Return: The probability of the dictionary Dictionarythreshold(Spectrum').
     */
    private static double ProbabilityofSpectralDictionaryProblem(List<Integer> spectrumVectors, int threshold, int maxScore) {
        spectrumVectors.add(0, 0);
        int m = spectrumVectors.size();
        Double[][] prob = new Double[m][maxScore + 1];
        prob[0][0] = 1.0;
        for (int t = 1; t <= maxScore; t++) {
            prob[0][t] = 0.0;
        }
        for (int i = 1; i < m; i++) {
            prob[i][0] = 0.0;
        }
        for (int i = 1; i < m; i++) {
            for (int t = 1; t <= maxScore; t++) {
                prob[i][t] = 0.0;
                int tprime = t - spectrumVectors.get(i);
                for (char acmino : MASSTABLE_NO_TOY.keySet()) {
                    int iprime = i - MASSTABLE_NO_TOY.get(acmino);
                    if (iprime < 0 || tprime < 0 || tprime > maxScore) {
                        continue;
                    } else {
                        prob[i][t] += prob[iprime][tprime] / 20;
                    }
                }
            }
        }
        double probability = 0;
        for (int t = threshold; t <= maxScore; t++) {
            probability += prob[m - 1][t];
        }
        return probability;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11866_11.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        List<Integer> spectrum = Arrays.stream(splits[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        int threshold = Integer.parseInt(splits[1]);
        int maxScore = Integer.parseInt(splits[2]);
        System.out.println(ProbabilityofSpectralDictionaryProblem(spectrum, threshold, maxScore));
    }
}
