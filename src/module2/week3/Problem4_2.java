package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static common.PrintUtils.printListInOneline;

public class Problem4_2 {

	/**
	 * CyclicSpectrum(Peptide, Alphabet, AminoAcidMass)
	 *     PrefixMass(0) ← 0
	 *     for i ← 1 to |Peptide|
	 *         for every symbol s in Alphabet
	 *             if s = i-th amino acid in Peptide
	 *                 PrefixMass(i) ← PrefixMass(i − 1) + AminoAcidMass﻿[s]
	 *     peptideMass ← PrefixMass(|Peptide|)
	 *     CyclicSpectrum ← a list consisting of the single integer 0
	 *     for i ← 0 to |Peptide| − 1
	 *         for j ← i + 1 to |Peptide|
	 *             add PrefixMass(j) − PrefixMass(i) to CyclicSpectrum
	 *             if i > 0 and j < |Peptide|
	 *                 add peptideMass - (PrefixMass(j) − PrefixMass(i)) to CyclicSpectrum
	 *     return sorted list CyclicSpectrum
	 * @throws IOException
	 */
	public static List<Integer> CyclicSpectrum(String peptide) {
		List<Integer> prefixMass = new LinkedList<>();
		prefixMass.add(0);
		for (int i = 0; i < peptide.length(); i++) {
			prefixMass.add(prefixMass.get(i) + Constant.MASSTABLE.get(peptide.charAt(i)));
		}
		List<Integer> cyclicSpectrum = new LinkedList<>();
		cyclicSpectrum.add(0);
		int peptideMass = prefixMass.get(peptide.length());
		for (int i = 0; i < prefixMass.size(); i++) {
			for (int j = i + 1; j < prefixMass.size(); j++) {
				cyclicSpectrum.add(prefixMass.get(j) - prefixMass.get(i));
				if(i > 0 && j < peptide.length())
					cyclicSpectrum.add(peptideMass - (prefixMass.get(j) - prefixMass.get(i)));
			}
		}
		Collections.sort(cyclicSpectrum);
		return cyclicSpectrum;
	}


	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_98_4.txt"), Charset.forName("UTF-8"));
		String peptide = text.replace("\n", "");
		printListInOneline(CyclicSpectrum(peptide));	}
}
