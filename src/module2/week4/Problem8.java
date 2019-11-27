package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;
import static module2.week4.Problem6.LeaderboardCyclopeptideSequencingAllMaxScore;
import static module2.week4.Problem6.convertToMassFormat;
import static module2.week4.Problem7.SpectralConvolution;

public class Problem8 {

	/**
	 * Code Challenge: Implement ConvolutionCyclopeptideSequencing.
	 * <p>
	 * Input: An integer M, an integer N, and a collection of (possibly repeated) integers Spectrum.
	 * Output: A cyclic peptide LeaderPeptide with amino acids taken only from the top M elements (and ties) of the convolution of Spectrum that fall between 57 and 200, and where the size of Leaderboard is restricted to the top N (and ties).
	 *
	 * @param
	 * @throws IOException
	 */
	// TODO verify correctness
	public static List<List<Integer>> ConvolutionCyclopeptideSequencing(int M, int N, List<Integer> spectrum) {
		List<Integer> convolution = SpectralConvolution(spectrum);
		Map<Integer, Integer> countMap = new HashMap<>();
		Map<Integer, Set<Integer>> mutlipilicyMap = new TreeMap<>();
		for (Integer mass : convolution) {
			countMap.put(mass, countMap.getOrDefault(mass, 0) + 1);
		}
		for (Integer mass : countMap.keySet()) {
			if (mutlipilicyMap.get(countMap.get(mass)) == null) {
				Set<Integer> peptideMass = new HashSet<>();
				peptideMass.add(mass);
				mutlipilicyMap.put(countMap.get(mass), peptideMass);
			} else {
				Set<Integer> peptideMass = mutlipilicyMap.get(countMap.get(mass));
				peptideMass.add(mass);
			}
		}
		Iterator<Set<Integer>> it = mutlipilicyMap.values().iterator();
		List<Integer> aminoMass = new LinkedList<>();
		while(M >0){
			if(!it.hasNext()){
				break;
			}
			Set<Integer> candidate = it.next().stream().filter(m-> m>=57 && m<=200).collect(Collectors.toSet());
			aminoMass.addAll(candidate);
			M -= candidate.size();
		}
		return LeaderboardCyclopeptideSequencingAllMaxScore(N, spectrum);
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_104_7.txt"), Charset.forName("UTF-8"));
		int M = Integer.parseInt(text.split("\n")[0]);
		int N = Integer.parseInt(text.split("\n")[1]);
		List<Integer> spectrum = Arrays.stream(text.split("\n")[2].split(" "))
				.map(e -> Integer.parseInt(e)).collect(Collectors.toList());
		printListInOneline(convertToMassFormat(ConvolutionCyclopeptideSequencing(M, N, spectrum)));
	}
}
