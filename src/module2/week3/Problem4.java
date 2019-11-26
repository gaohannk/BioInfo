package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static common.PrintUtils.printListInOneline;

public class Problem4 {

	/**
	 * LinearSpectrum(Peptide, Alphabet, AminoAcidMass)
	 *     PrefixMass(0) ← 0
	 *     for i ← 1 to |Peptide|
	 *         for every symbol s in Alphabet
	 *             if s = i-th amino acid in Peptide
	 *                 PrefixMass(i) ← PrefixMass(i − 1) + AminoAcidMass[s]
	 *     LinearSpectrum ← a list consisting of the single integer 0
	 *     for i ← 0 to |Peptide| − 1
	 *         for j ← i + 1 to |Peptide|
	 *             add PrefixMass(j) − PrefixMass(i) to LinearSpectrum
	 *     return sorted list LinearSpectrum
	 *
	 * Code Challenge: Implement LinearSpectrum.
	 *
	 *     Input: An amino acid string Peptide.
	 *     Output: The linear spectrum of Peptide.
	 * @param peptide
	 * @return
	 */
	public static List<Integer> LinearSpectrum(String peptide) {
		List<Integer> prefixMass = new LinkedList<>();
		prefixMass.add(0);
		for (int i = 0; i < peptide.length(); i++) {
			prefixMass.add(prefixMass.get(i) + Constant.MASSTABLE.get(peptide.charAt(i)));
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

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_4912_2.txt"), Charset.forName("UTF-8"));
		String peptide = text.replace("\n", "");
		printListInOneline(LinearSpectrum(peptide));
	}
}
