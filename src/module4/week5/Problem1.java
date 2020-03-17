package module4.week5;

import module4.common.Amino;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module2.common.Constant.MASSTABLE;

public class Problem1 {
    /**
     * Peptide Identification Problem: Find a peptide from a proteome with maximum score against a spectrum.
     * Input: A spectral vector Spectrum and an amino acid string Proteome.
     * Output: An amino acid string Peptide that maximizes Score(Peptide, Spectrum) among all substrings of Proteome.
     *
     * CODE CHALLENGE: Solve the Peptide Identification Problem.
     * Given: A space-delimited spectral vector Spectrum' and an amino acid string Proteome.
     * Return: A substring of Proteome with maximum score against Spectrum'.
     */
    public static Amino PeptideSIdentificationProblem(List<Integer> spectrumVector, String proteome) {
        spectrumVector.add(0, 0);
        int maxScore = Integer.MIN_VALUE;
        String aminoAcid = null;
        for (int i = 0; i < proteome.length(); i++) {
            int idx = 0;
            int j = i;
            int score = 0;
            String peptide = "";
            while (idx < spectrumVector.size() && j < proteome.length()) {
                score += spectrumVector.get(idx);
                if (idx == spectrumVector.size() - 1 && score > maxScore) {
                    maxScore = score;
                    aminoAcid = peptide;
                    break;
                }
                peptide += proteome.charAt(j);
                idx += MASSTABLE.get(proteome.charAt(j));
                j++;
            }
        }
        return new Amino(aminoAcid, maxScore);
    }


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11866_2.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        String proteome = splits[1];
        List<Integer> spectrum = Arrays.stream(splits[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(PeptideSIdentificationProblem(spectrum, proteome).peptide);
    }
}
