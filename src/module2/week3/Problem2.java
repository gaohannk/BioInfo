package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static common.Constant.dic;
import static common.PrintUtils.printListByLine;
import static module2.week3.Constant.CONDONTABLE;

public class Problem2 {
	/**
	 * Peptide Encoding Problem: Find substrings of a genome encoding a given amino acid sequence.
	 * <p>
	 * Input: A DNA string Text, an amino acid string Peptide, and the array GeneticCode.
	 * Output: All substrings of Text encoding Peptide (if any such substrings exist).
	 */

	public static List<String> PeptideEncodingProblem(String DNA, String peptide) {
		List<String> res = new LinkedList<>();
		int len = peptide.length() * 3;
		for (int i = 0; i < DNA.length() - len+1; i++) {
			String candidateDNA = DNA.substring(i, i + len);
			String complementDNA = complement(candidateDNA);
			String revComplementDna = reverse(complementDNA);
			String RNA = transToRNA(revComplementDna);
			String revRNA = transToRNA(candidateDNA);

			if (canTranslateToPeptide(RNA, peptide) || canTranslateToPeptide(revRNA, peptide)) {
				res.add(candidateDNA);
			}
		}
		return res;
	}

	private static String transToRNA(String dna) {
		return dna.replaceAll("T", "U");
	}

	private static boolean canTranslateToPeptide(String RNA, String peptide) {
		for (int i = 0; i < RNA.length(); i = i + 3) {
			String codons = RNA.substring(i, i + 3);
			if (!Constant.CONDONTABLE.containsKey(codons)) {
				return false;
			}
			if(CONDONTABLE.get(codons) != peptide.toCharArray()[i/3]){
				return false;
			}
		}
		return true;
	}

	private static String reverse(String seq) {
		return new StringBuilder(seq).reverse().toString();
	}

	private static String complement(String dna) {
		StringBuilder complement = new StringBuilder();
		for(char c: dna.toCharArray()){
			complement.append(dic.get(c));
		}
		return complement.toString();
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_96_7.txt"), Charset.forName("UTF-8"));
		String DNA = text.split("\n")[0];
		String peptide = text.split("\n")[1];
		printListByLine(PeptideEncodingProblem(DNA, peptide));
	}
}
