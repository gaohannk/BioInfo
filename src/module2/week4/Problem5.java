package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;
import static module2.week3.Problem7.*;
import static module2.week4.Problem1.CyclopeptideScoring;
import static module2.week4.Problem3.Trim;

public class Problem5 {
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
	public static List<String> LeaderboardCyclopeptideSequencingAllMaxScore(int N, List<Integer> spectrum){
		List<String> leaderPeptides = new LinkedList<>();
		List<String> leaderBoard = new LinkedList<>();
		String maxScorePeptide = "";
		leaderBoard.add("");
		while(!leaderBoard.isEmpty()){
			List<String> expand = expend(leaderBoard);
			Iterator<String> it = expand.iterator();
			while (it.hasNext()){
				String peptide = it.next();
				int parentMassOfSpectrum = parentMass(spectrum);
				if(mass(peptide) == parentMassOfSpectrum){
					// equal() method compare each element in list
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
			leaderBoard = Trim(expand, spectrum, N);
		}
		return leaderPeptides;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_102_10.txt"), Charset.forName("UTF-8"));
		int N = Integer.parseInt(text.split("\n")[0]);
		List<Integer> spectrum = Arrays.stream(text.split("\n")[1].split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		printListInOneline(convertToMassFormat(LeaderboardCyclopeptideSequencingAllMaxScore(N, spectrum)));
	}
}
