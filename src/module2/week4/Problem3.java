package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;
import static module2.week4.Problem2.LinearScore;

public class Problem3 {
	/**
	 * Code Challenge: Implement Trim (reproduced below).
	 *
	 *     Input: A collection of peptides Leaderboard, a collection of integers Spectrum, and an integer N.
	 *     Output: The N highest-scoring linear peptides on Leaderboard with respect to Spectrum.
	 *
	 * Trim(Leaderboard, Spectrum, N, Alphabet, AminoAcidMass)
	 *     for j ← 1 to |Leaderboard|
	 *         Peptide ← j-th peptide in Leaderboard
	 *         LinearScores(j) ← LinearScore(Peptide, Spectrum, Alphabet, AminoAcidMass)
	 *     sort Leaderboard according to the decreasing order of scores in LinearScores
	 *     sort LinearScores in decreasing order
	 *     for j ← N + 1 to |Leaderboard|
	 *         if LinearScores(j) < LinearScores(N)
	 *             remove all peptides starting from the j-th peptide from Leaderboard
	 *             return Leaderboard
	 *     return Leaderboard
	 * @param leaderBoard
	 * @param spectrum
	 * @param N
	 * @return
	 */
	public static List<String> Trim(List<String> leaderBoard, List<Integer> spectrum, int N) {
		List<Pair> linearScore = new LinkedList<>();
		for(String peptide : leaderBoard){
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

	// TODO need to verify correctness use Trim first
	public static List<String> Trim2(List<String> leaderBoard, List<Integer> spectrum, int N) {
		Queue<Pair> linearScore = new PriorityQueue<>(Collections.reverseOrder());
		for(String peptide : leaderBoard){
			linearScore.add(new Pair(peptide, LinearScore(peptide, spectrum)));
		}
		List<Pair> res = new LinkedList<>();
		while(N-- > 0 && !linearScore.isEmpty()){
			res.add(linearScore.poll());
		}

		if(res.isEmpty()) {
			return new LinkedList<>();
		}
		int boundary = res.get(res.size() -1).score;
		while(!linearScore.isEmpty() && linearScore.peek().score >= boundary){
			res.add(linearScore.poll());
		}
		return res.stream().map(p -> p.peptide).collect(Collectors.toList());
	}



	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_4913_3.txt"), Charset.forName("UTF-8"));
		List<String> leaderBoard = Arrays.stream(text.split("\n")[0].split(" ")).collect(Collectors.toList());
		List<Integer> spectrum = Arrays.stream(text.split("\n")[1].split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		int N = Integer.parseInt(text.split("\n")[2]);
		printListInOneline(Trim(leaderBoard, spectrum, N));
	}
}
