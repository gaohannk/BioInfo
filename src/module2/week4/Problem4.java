package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module2.common.Constant.MASSTABLE;
import static module2.week3.Problem7.*;
import static module2.week4.Problem1.CyclopeptideScoring;
import static module2.week4.Problem3.Trim;

public class Problem4 {
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
	public static String LeaderboardCyclopeptideSequencing(int N, List<Integer> spectrum){
		String leaderPeptides = "";
		List<String> leaderBoard = new LinkedList<>();
		leaderBoard.add("");
		while(!leaderBoard.isEmpty()){
			List<String> expand = expend(leaderBoard);
			Iterator<String> it = expand.iterator();
			while (it.hasNext()){
				String peptide = it.next();
				int parentMassOfSpectrum = parentMass(spectrum);
				if(mass(peptide) == parentMassOfSpectrum){
					if(CyclopeptideScoring(peptide, spectrum) > CyclopeptideScoring(leaderPeptides, spectrum)){
						leaderPeptides = peptide;
					}
				} else if (mass(peptide) > parentMassOfSpectrum){
					it.remove();
				}
			}
			leaderBoard = Trim(expand, spectrum, N);
		}
		return leaderPeptides;
	}

	private static String convertToMassFormat(String finalPeptides) {
		StringBuilder sb = new StringBuilder();
		for(char c: finalPeptides.toCharArray()) {
			sb.append(Integer.toString(MASSTABLE.get(c))+ '-');
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_102_8.txt"), Charset.forName("UTF-8"));
		int N = Integer.parseInt(text.split("\n")[0]);
		List<Integer> spectrum = Arrays.stream(text.split("\n")[1].split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		System.out.println(convertToMassFormat(LeaderboardCyclopeptideSequencing(N, spectrum)));
	}
}
