package module2.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;
import static common.PrintUtils.printListInOneline;
import static module2.week3.Problem7.parentMass;

public class Problem7 {
	/**
	 * Spectral Convolution Problem: Compute the convolution of a spectrum.
	 *
	 *     Input: A collection of integers Spectrum in increasing order..
	 *     Output: The list of elements in the convolution of Spectrum. If an element has multiplicity k, it should appear exactly k times; you may return the elements in any order.
	 * @throws IOException
	 */
	public static List<Integer> SpectralConvolution(List<Integer> spectrum){
		List<Integer> convolution = new LinkedList<>();
		for(int i=0;i<spectrum.size();i++){
			for(int j=i+1;j<spectrum.size();j++){
				convolution.add(Math.abs(spectrum.get(i)-spectrum.get(j)));
			}
		}
		return convolution;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_104_4.txt"), Charset.forName("UTF-8"));
		List<Integer> spectrum = Arrays.stream(text.replace("\n", "").split(" "))
				.map( e-> Integer.parseInt(e)).collect(Collectors.toList());
		printListInOneline(SpectralConvolution(spectrum));
	}
}
