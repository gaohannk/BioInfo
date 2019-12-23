package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.Constant.nucle;
import static module2.common.Constant.MASSTABLE;

public class Problem3 {

	/**
	 * CODE CHALLENGE: Solve the Converting a Peptide into a Peptide Vector Problem.
	 * Given: An amino acid string P.
	 * Return: The peptide vector of P (in the form of space-separated integers).
	 */
	public static int[] convertPeptideToPeptideVector(String acid) {
		List<Integer> list = new LinkedList<>();
		int mass = 0;
		for (int i = 0; i < acid.length(); i++) {
			mass += MASSTABLE.get(acid.charAt(i));
			list.add(mass);
		}
		System.out.println(list);
		int[] peptideVector = new int[list.get(list.size() - 1)];
		for (int i : list) {
			peptideVector[i - 1] = 1;
		}
		return peptideVector;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_11813_6.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		String acid = splits[0];
		int[] vector = convertPeptideToPeptideVector(acid);
		for (int i = 0; i < vector.length; i++) {
			System.out.print(vector[i] + " ");
		}
	}
}
