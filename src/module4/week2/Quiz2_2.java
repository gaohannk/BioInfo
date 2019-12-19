package module4.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module3.common.PrintUtils.printMatrix;

public class Quiz2_2 {
	private static int Discrepancy(int[][] matrix, int[][] Tree, int n) {
		int res = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j && i < j) {
					res += (matrix[i][j] - Tree[i][j]) * (matrix[i][j] - Tree[i][j]);
				}
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/quiz2_2.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		int[][] matrix = new int[n][n];
		for (int i = 1; i < 1 + n; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 1][k] = list.get(k);
			}
		}
		int[][] tree = new int[n][n];
		for (int i = 2 + n; i < splits.length; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 1][k] = list.get(k);
			}
		}
		System.out.println(Discrepancy(matrix, tree, n));
	}
}
