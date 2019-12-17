package module4.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module3.common.PrintUtils.printMatrix;

public class Problem2 {
	/**
	 * Code Challenge: Solve the Limb Length Problem.
	 * <p>
	 * Input: An integer n, followed by an integer j between 0 and n - 1, followed by a space-separated additive distance matrix D (whose elements are integers).
	 * Output: The limb length of the leaf in Tree(D) corresponding to row j of this distance matrix (use 0-based indexing)
	 */
	public static int LimbLength(int[][] matrix, int n, int j) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				if(k!=i && k!=j && i!=j) {
					min = Math.min(min, (matrix[i][j] + matrix[j][k] - matrix[i][k]) / 2);
				}
			}
		}
		return min;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_10329_11.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		int j = Integer.parseInt(splits[1]);
		int[][] matrix = new int[n][n];
		for (int i = 2; i < splits.length; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 2][k] = list.get(k);
			}
		}
		System.out.println(LimbLength(matrix, n, j));
	}
}
