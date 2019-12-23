package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static module2.common.Constant.MASSTABLE_REV;

public class Problem5 {

	/**
	 * CODE CHALLENGE: Solve the Converting a Peptide Vector into a Peptide Problem.
	 * Given: A space-delimited binary vector P.
	 * Return: An amino acid string whose binary peptide vector matches P. For masses
	 * with more than one amino acid, any choice may be used.
	 */
	public static String convertPeptideVectorToPeptide(int[] vector) {
		List<Integer> list = new LinkedList<>();
		list.add(0);
		for (int i = 0; i < vector.length; i++) {
			if (vector[i] == 1) {
				list.add(i + 1);
			}
		}
		StringBuilder res = new StringBuilder();
		for (int i = 1; i < list.size(); i++) {
			res.append(MASSTABLE_REV.get(list.get(i) - list.get(i - 1)));
		}
		return res.toString();
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_11813_8.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		String[] acid = splits[0].split(" ");
		int[] vector = Arrays.stream(acid).mapToInt(Integer::parseInt).toArray();
		System.out.println(convertPeptideVectorToPeptide(vector));
	}
}
