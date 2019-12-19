package module4.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module3.common.PrintUtils.printMatrix;

public class Quiz2_5 {
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
		String text = Files.readString(Path.of("./resource/module4/quiz2_5.txt"), Charset.forName("UTF-8"));
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
