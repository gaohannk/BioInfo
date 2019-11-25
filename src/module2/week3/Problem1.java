package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static module2.week3.Constant.CONDONTABLE;

public class Problem1 {
	/**
	 * Protein Translation Problem: Translate an RNA string into an amino acid string.
	 * Input: An RNA string Pattern and the array GeneticCode.
	 * Output: The translation of Pattern into an amino acid string Peptide.
	 */

	public static String ProteinTranslationProblem(String RNA) {
		StringBuilder protein = new StringBuilder();
		for (int i = 0; i < RNA.length(); i=i+3) {
			String codons = RNA.substring(i, i + 3);
			if (Constant.CONDONTABLE.containsKey(codons)) {
				protein.append(CONDONTABLE.get(codons));
			}
		}
		return protein.toString();
	}

	public static void main(String[] args) throws IOException {
		String RNA = Files.readString(Path.of("./resource/module2/dataset_96_4.txt"), Charset.forName("UTF-8"));
		RNA.replace("\n","");
		System.out.println(ProteinTranslationProblem(RNA));
	}
}
