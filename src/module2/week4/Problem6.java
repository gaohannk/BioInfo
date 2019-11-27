package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;
import static common.PrintUtils.printListInOneline;

import static module2.week3.Problem7.*;

public class Problem6 {
	/**
	 *  LeaderboardCyclopeptideSequencing(Spectrum, N)
	 *         Leaderboard ← set containing only the empty peptide
	 *         LeaderPeptide ← empty peptide
	 *         while Leaderboard is non-empty
	 *             Leaderboard ← Expand(Leaderboard)
	 *             for each Peptide in Leaderboard
	 *                 if Mass(Peptide) = ParentMass(Spectrum)
	 *                     if Score(Peptide, Spectrum) > Score(LeaderPeptide, Spectrum)
	 *                         LeaderPeptide ← Peptide
	 *                 else if Mass(Peptide) > ParentMass(Spectrum)
	 *                     remove Peptide from Leaderboard
	 *             Leaderboard ← Trim(Leaderboard, Spectrum, N)
	 *         output LeaderPeptide
	 *
	 * Code Challenge: Implement LeaderboardCyclopeptideSequencing.
	 *
	 *     Input: An integer N and a collection of integers Spectrum.
	 *     Output: LeaderPeptide after running LeaderboardCyclopeptideSequencing(Spectrum, N).
	 */
	// TODO verify correctness
	public static List<List<Integer>> LeaderboardCyclopeptideSequencingAllMaxScore(int N, List<Integer> spectrum){
		List<List<Integer>> leaderPeptides = new LinkedList<>();
		List<List<Integer>> leaderBoard = new LinkedList<>();
		List<Integer> maxScorePeptide = new LinkedList<>();
		leaderBoard.add(new LinkedList<>());
		while(!leaderBoard.isEmpty()){
			List<List<Integer>> expend = expend(leaderBoard);
			Iterator<List<Integer>> it = expend.iterator();
			while (it.hasNext()){
				List<Integer> peptide = it.next();
				int parentMassOfSpectrum = parentMass(spectrum);
				if(mass(peptide) == parentMassOfSpectrum){
					if(CyclopeptideScoring(peptide, spectrum) > CyclopeptideScoring(maxScorePeptide, spectrum)){
						leaderPeptides.clear();
						leaderPeptides.add(peptide);
						maxScorePeptide = peptide;
					} else if (CyclopeptideScoring(peptide, spectrum) == CyclopeptideScoring(maxScorePeptide, spectrum)){
						leaderPeptides.add(peptide);
					}
				} else if (mass(peptide) > parentMassOfSpectrum){
					it.remove();
				}
			}
			System.out.println("bEFORE CLEAR");
			printListInOneline(expend);
			leaderBoard.clear();
			leaderBoard.addAll(Trim(expend, spectrum, N));
			System.out.println("_----");
			printListByLine(leaderBoard);
		}
		return leaderPeptides;
	}

	public static List<List<Integer>> Trim(List<List<Integer>> leaderBoard, List<Integer> spectrum, int N) {
		List<Pair> linearScore = new LinkedList<>();
		for(List<Integer> peptide : leaderBoard){
			linearScore.add(new Pair(peptide, LinearScore(peptide, spectrum)));
		}
		Collections.sort(linearScore, Collections.reverseOrder());
		for(int i = N; i < linearScore.size(); i++){
			if(linearScore.get(i).score < linearScore.get(N-1).score){
				return linearScore.subList(0, i).stream().map(p-> p.peptide).collect(Collectors.toList());
			}
		}
		return linearScore.stream().map(p-> p.peptide).collect(Collectors.toList());
	}

	public static int CyclopeptideScoring(List<Integer> peptide, List<Integer> spectrum){
		List<Integer> cyclicSpectrum =  CyclicSpectrum(peptide);
		return getScore(spectrum, cyclicSpectrum);
	}

	public static int LinearScore(List<Integer> peptide, List<Integer> spectrum){
		List<Integer> linearSpectrum =  LinearSpectrum(peptide);
		return getScore(spectrum, linearSpectrum);
	}

	public static List<Integer> LinearSpectrum(List<Integer> peptide) {
		List<Integer> prefixMass = new LinkedList<>();
		prefixMass.add(0);
		for (int i = 0; i < peptide.size(); i++) {
			prefixMass.add(prefixMass.get(i) + peptide.get(i));
		}
		List<Integer> linearSpectrum = new LinkedList<>();
		linearSpectrum.add(0);
		for (int i = 0; i < prefixMass.size(); i++) {
			for (int j = i + 1; j < prefixMass.size(); j++) {
				linearSpectrum.add(prefixMass.get(j) - prefixMass.get(i));
			}
		}
		Collections.sort(linearSpectrum);
		return linearSpectrum;
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

	public static List<Integer> CyclicSpectrum(List<Integer> peptide) {
		List<Integer> prefixMass = new LinkedList<>();
		prefixMass.add(0);
		for (int i = 0; i < peptide.size(); i++) {
			prefixMass.add(prefixMass.get(i) + peptide.get(i));
		}
		List<Integer> cyclicSpectrum = new LinkedList<>();
		cyclicSpectrum.add(0);
		int peptideMass = prefixMass.get(peptide.size());
		for (int i = 0; i < prefixMass.size(); i++) {
			for (int j = i + 1; j < prefixMass.size(); j++) {
				cyclicSpectrum.add(prefixMass.get(j) - prefixMass.get(i));
				if(i > 0 && j < peptide.size())
					cyclicSpectrum.add(peptideMass - (prefixMass.get(j) - prefixMass.get(i)));
			}
		}
		Collections.sort(cyclicSpectrum);
		return cyclicSpectrum;
	}

	public static int mass(List<Integer> peptides) {
		return peptides.stream().reduce(Integer::sum).orElse(0);
	}

	public static List<List<Integer>> expend(List<List<Integer>> candidatePeptides) {
		List<List<Integer>> expend = new LinkedList<>();
		for(List<Integer> peptides: candidatePeptides){
			for(int i = 57; i <= 200; i++){
				List<Integer> candidate = new LinkedList<>(peptides);
				candidate.add(i);
				expend.add(candidate);
			}
		}
		return expend;
	}

	public static class Pair implements Comparable<Pair>{
		List<Integer> peptide;
		int score;
		public Pair(List<Integer> peptide, int score){
			this.peptide = peptide;
			this.score = score;
		}

		@Override
		public int compareTo(Pair p) {
			return this.score - p.score;
		}
	}

	public static List<String> convertToMassFormat(List<List<Integer>> finalPeptides) {
		return finalPeptides.stream().map( peptide -> {
			StringBuilder sb = new StringBuilder();
			for(int mass: peptide) {
				sb.append(Integer.toString(mass)+ '-');
			}
			return sb.deleteCharAt(sb.length()-1).toString();
		}).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_102_10.txt"), Charset.forName("UTF-8"));
		int N = Integer.parseInt(text.split("\n")[0]);
		List<Integer> spectrum = Arrays.stream(text.split("\n")[1].split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		printListInOneline(convertToMassFormat(LeaderboardCyclopeptideSequencingAllMaxScore(N, spectrum)));
	}
}
