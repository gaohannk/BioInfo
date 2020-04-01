package module4.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module4.week4.Problem3.convertPeptideToPeptideVector;

public class Problem5 {

    public static int maxScore = Integer.MIN_VALUE;
    public static String aminoAcid;

    /**
     * Spectral Alignment Problem: Given a peptide and a spectral vector, find a modified variant of this peptide that maximizes the peptide-spectrum score, among all variants of the peptide with up to k modifications.
     *      Input: An amino acid string Peptide, a spectral vector Spectrum', and an integer k.
     *      Output: A peptide of maximum score against Spectrum' among all peptides in Variantsk(Peptide).
     */
    /**
     * CODE CHALLENGE: Solve the Spectral Alignment Problem.
     * Given: A peptide Peptide, a spectral vector Spectrum', and an integer k.
     * Return: A peptide Peptide' related to Peptide by up to k modifications with
     * maximal score against Spectrum' out of all possibilities.
     */
    private static String SpectralAlignmentProblem(List<Integer> spectrum, String peptide, int k) {

        for (int i = 0; i < peptide.length(); i++) {
            //helper(spectrum, peptide, k-1, i, )
        }
        return  "";
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11813_2.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        String peptide = splits[0];
        List<Integer> spectrum = Arrays.stream(splits[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        int k = Integer.parseInt(splits[2]);
        System.out.println(SpectralAlignmentProblem(spectrum, peptide, k));
    }
}
