package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module2.week3.Problem4.LinearSpectrum;
import static module2.week4.Problem1.getScore;

public class Problem2 {

	/**
	 *  Code Challenge: Compute the score of a linear peptide with respect to a spectrum.
	 *
	 *     Input: An amino acid string Peptide and a collection of integers Spectrum.
	 *     Output: The linear score of Peptide with respect to Spectrum, LinearScore(Peptide, Spectrum).
	 */
	public static int LinearScore(String peptide, List<Integer> spectrum){
		List<Integer> linearSpectrum =  LinearSpectrum(peptide);
		return getScore(spectrum, linearSpectrum);
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_4913_1.txt"), Charset.forName("UTF-8"));
		String peptide = text.split("\n")[0];
		List<Integer> spectrum = Arrays.stream(text.split("\n")[1].split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		System.out.println(LinearScore(peptide, spectrum));
	}
}
