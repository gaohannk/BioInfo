package module4.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module2.common.Constant.*;

public class Problem3 {
    /**
     * Size of Spectral Dictionary Problem: Find the size of a spectral dictionary for a given spectrum and score threshold.
     *      Input: A spectral vector Spectrum' and an integer threshold.
     *      Output: The number of peptides in Dictionarythreshold(Spectrum').
     */
    /**
     * CODE CHALLENGE: Solve the Size of Spectral Dictionary Problem.
     * Given: A spectral vector Spectrum', an integer threshold, and an integer max_score.
     * Return: The size of the dictionary Dictionarythreshold(Spectrum').
     */
    public static int SizeofSpectralDictionaryProblem(List<Integer> spectrumVectors, int threshold, int maxScore) {
        spectrumVectors.add(0, 0);
        int m = spectrumVectors.size();
        Integer[][] size = new Integer[m][maxScore + 1];
        size[0][0] = 1;
        for (int t = 1; t <= maxScore; t++) {
            size[0][t] = 0;
        }
        for (int i = 1; i < m; i++) {
            size[i][0] = 0;
        }
        for (int i = 1; i < m; i++) {
            for (int t = 1; t <= maxScore; t++) {
                size[i][t] = 0;
                int tprime = t - spectrumVectors.get(i);
                for (char acmino : MASSTABLE_NO_TOY.keySet()) {
                    int iprime = i - MASSTABLE_NO_TOY.get(acmino);
                    if (iprime < 0 || tprime < 0 || tprime > maxScore) {
                        continue;
                    } else {
                        size[i][t] += size[iprime][tprime];
                    }
                }
            }
        }
        int dictionarySize = 0;
        for (int t = threshold; t <= maxScore; t++) {
            dictionarySize += size[m - 1][t];
        }
        return dictionarySize;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11866_8.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        List<Integer> spectrum = Arrays.stream(splits[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        int threshold = Integer.parseInt(splits[1]);
        int maxScore = Integer.parseInt(splits[2]);
        System.out.println(SizeofSpectralDictionaryProblem(spectrum, threshold, maxScore));
    }
}
