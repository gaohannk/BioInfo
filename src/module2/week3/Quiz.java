package module2.week3;

import module2.common.Constant;

import java.util.List;

import static module2.week3.Problem1.ProteinTranslation;
import static module2.week3.Problem4.LinearSpectrum;
import static module2.week3.Problem4_2.CyclicSpectrum;
import static module2.week3.Problem7.consistent;

public class Quiz {

	private static long NumOfDNATranslateToProtein(String protein) {
		int num = 1;
		for (char amino : protein.toCharArray()) {
			num *= Constant.CountTable.get(amino);
		}
		return num;
	}

	public static void main(String[] args) {
		List<String> RNAs = List.of("CCUCGUACUGAUAUUAAU", "CCCAGUACCGAGAUGAAU", "CCCAGGACUGAGAUCAAU", "CCUCGUACAGAAAUCAAC");
		for (String RNA : RNAs) {
			System.out.println(ProteinTranslation(RNA));
		}

		System.out.println(NumOfDNATranslateToProtein("SYNGE"));

		List<Integer> spectrum = List.of(0, 71, 101, 113, 131, 184, 202, 214, 232, 285, 303, 315, 345, 416);
		List<String> peptides = List.of("TLAM", "TAIM", "TMLA", "MAIT", "TMIA", "MTAI");
		for (String peptide : peptides) {
			System.out.println(CyclicSpectrum(peptide).equals(spectrum));
		}

		spectrum = List.of(0, 71, 99, 101, 103, 128, 129, 199, 200, 204, 227, 230, 231, 298, 303, 328, 330, 332, 333);

		peptides = List.of("TCE", "QCV", "CTV", "AQV", "TCQ", "VAQ");
		for (String peptide : peptides) {
			System.out.println(consistent(LinearSpectrum(peptide), spectrum));
		}
	}
}
