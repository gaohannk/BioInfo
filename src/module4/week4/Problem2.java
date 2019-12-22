package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.Constant.nucle;

public class Problem2 {

	/**
	 CODE CHALLENGE: Solve the Decoding an Ideal Spectrum Problem.
	 Given: A space-delimited list of integers Spectrum.
	 Return: An amino acid string that explains Spectrum.
	 */
	/**
	 * DecodingIdealSpectrum(Spectrum)
	 *      construct Graph(Spectrum)
	 *      for each path Path from source to sink in Graph(Spectrum)
	 *           Peptide ← the amino acid string spelled by the edge labels of Path
	 *           if IdealSpectrum(Peptide) = Spectrum
	 *                 return Peptide
	 * @param spectrum
	 * @return
	 */
	public static String DecodingIdealSpectrum(List<Integer> spectrum) {
		return "root";
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_10335_12.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
	}
}
