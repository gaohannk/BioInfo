package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static module2.week3.Constant.MASSTABLE;

public class Problem5 {

	/**
	 * Counting Peptides with Given Mass Problem: Compute the number of peptides of given mass.
	 * Input: An integer m.
	 * Output: The number of linear peptides having integer mass m.
	 *
	 * @throws IOException
	 */
	private static long CountingPeptideswithGivenMass(int n) {
		long table[] = new long[n+1];
		table[0] = 1;
		for (int i = 1; i < n+1; i++) {
			Set<Character> keys = MASSTABLE.keySet();
			// I and L, K and Q has same mass.
			keys.remove('I');
			keys.remove('K');
			for (char amino : keys) {
				if (i - MASSTABLE.get(amino) >= 0) {
					table[i] += table[i - MASSTABLE.get(amino)];
				}
			}
		}
		return table[n];
	}


	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_99_2.txt"), Charset.forName("UTF-8"));
		int n = Integer.parseInt(text.replace("\n", ""));
		System.out.println(CountingPeptideswithGivenMass(n));
	}
}
