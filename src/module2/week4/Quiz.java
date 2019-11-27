package module2.week4;

import java.io.IOException;
import java.util.List;
import static module2.week4.Problem1.CyclopeptideScoring;
import static module2.week4.Problem2.LinearScore;

import static module2.week4.Problem7.SpectralConvolution;

public class Quiz {
	public static void main(String[] args) throws IOException {
		String peptide = "MAMA";
		List<Integer> spectrum = List.of(0, 71, 98, 99, 131, 202, 202, 202, 202, 202, 299, 333, 333, 333, 503);
		System.out.println(CyclopeptideScoring(peptide, spectrum));
		peptide = "PEEP";
		spectrum = List.of(0, 97, 129, 129, 129, 194, 226, 323, 323, 355, 452);
		System.out.println(LinearScore(peptide, spectrum));
		System.out.println(SpectralConvolution(List.of(0, 57, 118, 179, 236, 240, 301)));

	}

}
