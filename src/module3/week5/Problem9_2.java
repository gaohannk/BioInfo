package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static common.Constant.dic;


public class Problem9_2 {
	/**
	 * Shared k-mers Problem: Given two strings, find all their shared k-mers.
	 * <p>
	 * Input: An integer k and two strings.
	 * Output: All k-mers shared by these strings, in the form of ordered pairs (x, y) corresponding to starting positions of these k-mers in the respective strings.
	 */
	public static List<List<Integer>> SharedKmersProblem(String v, String w, int k) {
		List<List<Integer>> list = new LinkedList<>();
		for (int i = 0; i <= v.length() - k; i++) {
			for (int j = 0; j <= w.length() - k; j++) {
				if (v.substring(i, i + k).equals(w.substring(j, j + k))
						|| v.substring(i, i + k).equals(complement(w.substring(j, j + k)))) {
					list.add(List.of(i,j));
				}
			}
		}
		list.sort(Comparator.comparingInt(o -> o.get(0)));
		return list;
	}

	private static String complement(String dna) {
		StringBuilder complement = new StringBuilder();
		for (char c : dna.toCharArray()) {
			complement.append(dic.get(c));
		}
		return complement.reverse().toString();
	}

	public static void main(String[] args) throws IOException {
		String text1 = Files.readString(Path.of("./resource/module3/E_coli.txt"), Charset.forName("UTF-8"));
		String text2 = Files.readString(Path.of("./resource/module3/Salmonella_enterica.txt"), Charset.forName("UTF-8"));
		List<List<Integer>> kmers = SharedKmersProblem(text1,text2, 30);
		System.out.println(kmers.size());
	}
}
