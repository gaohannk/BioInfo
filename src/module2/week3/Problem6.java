package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Problem6 {

	/**
	 * Exercise Break: How many subpeptides does a linear peptide of given length n have? (Include the empty peptide and the entire peptide.)
	 *
	 *     Input: An integer n.
	 *     Output: The number of subpeptides of a linear peptide of length n.
	 *
	 * @throws IOException
	 */
	private static int NumOfSubpeptidesOfLinearPeptide(int n) {
		return n*(n+1)/2 +1;
	}


	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_100_3.txt"), Charset.forName("UTF-8"));
		int n = Integer.parseInt(text.replace("\n", ""));
		System.out.println(NumOfSubpeptidesOfLinearPeptide(n));
	}
}
