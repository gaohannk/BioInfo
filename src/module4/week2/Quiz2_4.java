package module4.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module3.common.PrintUtils.printMatrix;

public class Quiz2_4 {
	private static int[][] calulateNeighborJoiningMatrix(int[][] matrix, int n) {
		int[][] res = new int[n][n];
		int[] totalDistance = getTotalDistance(matrix, n);
		for (int i =0 ;i< n;i++) {
			for (int j = 0;j< n;j++) {
				if (i != j) {
					res[i][j]= (n - 2) * matrix[i][j]- totalDistance[i] - totalDistance[j];
				}
			}
		}
		return res;
	}

	private static int[]  getTotalDistance(int[][]  matrix, int n) {
		int[] totalDistance = new int[n];
		for (int i =0 ;i< n;i++) {
			int total = 0;
			for (int j = 0;j< n;j++) {
				total += matrix[i][j];
			}
			totalDistance[i]= total;
		}
		return totalDistance;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/quiz2_4.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		int[][] matrix = new int[n][n];
		for (int i = 1; i < splits.length; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 1][k] = list.get(k);
			}
		}
		printMatrix(calulateNeighborJoiningMatrix(matrix, n), " ");
	}
}
