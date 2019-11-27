package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module2.week3.Problem4_2.CyclicSpectrum;

public class Problem1 {
	/**
	 * Cyclopeptide Scoring Problem: Compute the score of a cyclic peptide against a spectrum.
	 *
	 *     Input: An amino acid string Peptide and a collection of integers Spectrum.
	 *     Output: The score of Peptide against Spectrum, Score(Peptide, Spectrum).
	 */
	public static int CyclopeptideScoring(String peptide, List<Integer> spectrum){
		List<Integer> cyclicSpectrum =  CyclicSpectrum(peptide);
		return getScore(spectrum, cyclicSpectrum);
	}

	public static int getScore(List<Integer> spectrum, List<Integer> theoreticalSpectrum) {
		int score = 0;
		Map<Integer, Integer> theoreticalMap = new HashMap<>();
		Map<Integer, Integer> spectrumMap = new HashMap<>();
		for(int mass: theoreticalSpectrum){
			theoreticalMap.put(mass, theoreticalMap.getOrDefault(mass, 0) + 1);
		}
		for(int mass: spectrum){
			spectrumMap.put(mass, spectrumMap.getOrDefault(mass, 0) + 1);
		}
		for(int mass: theoreticalMap.keySet()) {
			score += Math.min(theoreticalMap.get(mass), spectrumMap.getOrDefault(mass, 0));
		}
		return score;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_102_3.txt"), Charset.forName("UTF-8"));
		String peptide = text.split("\n")[0];
		List<Integer> spectrum = Arrays.stream(text.split("\n")[1].split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		System.out.println(CyclopeptideScoring(peptide, spectrum));
	}
}
