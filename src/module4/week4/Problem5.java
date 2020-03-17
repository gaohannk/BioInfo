package module4.week4;

import module4.common.Amino;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module2.common.Constant.MASSTABLE_REV_NO_TOY;

public class Problem5 {

    public static int maxScore = Integer.MIN_VALUE;
	public static String aminoAcid;

    /**
     * CODE CHALLENGE: Solve the Peptide Sequencing Problem.
     * Given: A space-delimited spectral vector Spectrum'.
     * Return: An amino acid string with maximum score against Spectrum'. For masses
     * with more than one amino acid, any choice may be used.
     * <p>
     * Note: When a spectral vector Spectrum' = s1 ... sm is given, it does not have a zero-th element; in your implementations, you should assume that s0 is equal to zero.
     */
    public static Amino PeptideSequencingProblem(List<Integer> spectrum) {
        spectrum.add(0, 0);
        traversal(spectrum, 0, 0, "");
        return new Amino(aminoAcid, maxScore);
    }


    public static void traversal(List<Integer> spectrum, int cur, int score, String peptide) {
        if (cur >= spectrum.size()) {
            return;
        }
		score += spectrum.get(cur);
		if (cur == spectrum.size() - 1) {
			if (score > maxScore) {
				maxScore = score;
				aminoAcid = peptide;
			}
            return;
        }
        for (int mass : MASSTABLE_REV_NO_TOY.keySet()) {
            int next = cur + mass;
            peptide += MASSTABLE_REV_NO_TOY.get(mass);
            traversal(spectrum, next, score, peptide);
            peptide = peptide.substring(0, peptide.length() - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11813_10.txt"), Charset.forName("UTF-8"));
        List<Integer> spectrum = Arrays.stream(text.split("\n")[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(PeptideSequencingProblem(spectrum).peptide);
    }
}
