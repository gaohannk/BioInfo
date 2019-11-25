package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;
import static module2.week3.Constant.MASSTABLE;
import static module2.week3.Problem4.LinearSpectrum;
import static module2.week3.Problem4_2.CyclicSpectrum;

public class Problem7 {

	/**
	 * CyclopeptideSequencing(Spectrum)
	 *         CandidatePeptides ← a set containing only the empty peptide
	 *         FinalPeptides ← empty list of strings
	 *         while CandidatePeptides is nonempty
	 *             CandidatePeptides ← Expand(CandidatePeptides)
	 *             for each peptide Peptide in CandidatePeptides
	 *                 if Mass(Peptide) = ParentMass(Spectrum)
	 *                     if Cyclospectrum(Peptide) = Spectrum and Peptide is not in FinalPeptides
	 *                         append Peptide to FinalPeptides
	 *                     remove Peptide from CandidatePeptides
	 *                 else if Peptide is not consistent with Spectrum
	 *                     remove Peptide from CandidatePeptides
	 *         return FinalPeptides
	 */
	private static List<String> CyclopeptideSequencing(List<Integer> spectrum) {
		List<String> finalPeptides = new LinkedList<>();
		List<String> candidatePeptides = new LinkedList<>();
		candidatePeptides.add("");
		while(!candidatePeptides.isEmpty()){
			List<String> expend = expend(candidatePeptides);
			Iterator<String> it = expend.iterator();
			while (it.hasNext()){
				String peptides = it.next();
				int parentMassOfSpectrum = parentMass(spectrum);
				if(mass(peptides) == parentMassOfSpectrum){
					// equal() method compare each element in list
					if(CyclicSpectrum(peptides).equals(spectrum) && !finalPeptides.contains(peptides)){
						finalPeptides.add(peptides);
					}
					it.remove();
				} else if (!consistent(LinearSpectrum(peptides), spectrum)){
					it.remove();
				}
			}
			candidatePeptides.clear();
			candidatePeptides.addAll(expend);
		}
		return convertToMassFormat(finalPeptides);
	}

	private static List<String> convertToMassFormat(List<String> finalPeptides) {
		return finalPeptides.stream().map( peptide -> {
			StringBuilder sb = new StringBuilder();
			for(char c: peptide.toCharArray()) {
				sb.append(Integer.toString(MASSTABLE.get(c))+ '-');
			}
			return sb.deleteCharAt(sb.length()-1).toString();
		}).collect(Collectors.toList());
	}

	/**
	 * Given an experimental spectrum Spectrum of a cyclic peptide, a linear peptide is consistent with Spectrum
	 * if every mass in its theoretical spectrum is contained in Spectrum. If a mass appears more than once in the theoretical
	 * spectrum of the linear peptide, then it must appear at least that many times in Spectrum in order for the linear
	 * peptide to be consistent with Spectrum.
	 * @param candidateSpectrum
	 * @param spectrum
	 * @return
	 */
	private static boolean consistent(List<Integer> candidateSpectrum, List<Integer> spectrum) {
		Map<Integer, Integer> candidateMap = new HashMap<>();
		Map<Integer, Integer> spectrumMap = new HashMap<>();
		for(int mass: candidateSpectrum){
			candidateMap.put(mass, candidateMap.getOrDefault(mass, 0) + 1);
		}
		for(int mass: spectrum){
			spectrumMap.put(mass, spectrumMap.getOrDefault(mass, 0) + 1);
		}
		for(int mass: candidateMap.keySet()){
			if(candidateMap.get(mass) > spectrumMap.getOrDefault(mass, 0 )){
				return false;
			}
		}
		return true;
	}

	private static int parentMass(List<Integer> spectrum) {
		return spectrum.stream().max(Integer::compareTo).get();
	}

	private static int mass(String peptides) {
		int mass = 0;
		for(char amino: peptides.toCharArray()){
			mass += MASSTABLE.get(amino);
		}
		return mass;
	}

	private static List<String> expend(List<String> candidatePeptides) {
		List<String> expend = new LinkedList<>();
		for(String peptides: candidatePeptides){
			for(char amino: Constant.NoDupMassAlphabet){
				expend.add(peptides + amino);
			}
		}
		return expend;
	}


	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_100_6.txt"), Charset.forName("UTF-8"));
		List<Integer> spectrum = Arrays.stream(text.replace("\n", "").split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		printListInOneline(CyclopeptideSequencing(spectrum));
	}
}
