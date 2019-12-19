package module4.week2;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Problem2 {

	/**
	 NeighborJoining(D)
	 n ← number of rows in D
	 if n = 2
	 T ← tree consisting of a single edge of length D1,2
	 return T
	 D* ← neighbor-joining matrix constructed from the distance matrix D
	 find elements i and j such that D*i,j is a minimum non-diagonal element of D*
	 Δ ← (TotalDistanceD(i) - TotalDistanceD(j)) /(n - 2)
	 limbLengthi ← (1/2)(Di,j + Δ)
	 limbLengthj ← (1/2)(Di,j - Δ)
	 add a new row/column m to D so that Dk,m = Dm,k = (1/2)(Dk,i + Dk,j - Di,j) for any k
	 D ← D with rows i and j removed
	 D ← D with columns i and j removed
	 T ← NeighborJoining(D)
	 add two new limbs (connecting node m with leaves i and j) to the tree T
	 assign length limbLengthi to Limb(i)
	 assign length limbLengthj to Limb(j)
	 return T
	 */
	/**
	 * Code Challenge: Implement NeighborJoining.
	 * <p>
	 * Input: An integer n, followed by an n x n distance matrix.
	 * Output: An adjacency list for the tree resulting from applying the neighbor-joining algorithm. Edge-weights should be accurate to two decimal places (they are provided to three decimal places in the sample output below).
	 */

	public static Map<Integer, List<Pair>> NeighborJoining(int[][] matrix, int n) {
		Map<Integer, Map<Integer, Double>> matrixAsMap = createMatrix(matrix, n);
		Set<Integer> validRowOrColumn = new HashSet<>();
		for (int i = 0; i < n; i++) {
			validRowOrColumn.add(i);
		}
		return helper(matrixAsMap, n, n, validRowOrColumn);
	}

	private static Map<Integer, List<Pair>> helper(Map<Integer, Map<Integer, Double>> matrixAsMap, int n, int idx, Set<Integer> validRowOrColumn) {
		Map<Integer, List<Pair>> graph = new LinkedHashMap<>();
		if (n == 2) {
			for (int row : validRowOrColumn) {
				graph.put(row, new LinkedList<>());
				for (int col : validRowOrColumn) {
					if (row != col) {
						graph.get(row).add(new Pair(col, matrixAsMap.get(row).get(col)));
					}
				}
			}
			return graph;
		}

		Map<Integer, Double> totalDistance = getTotalDistance(matrixAsMap, validRowOrColumn);
		Map<Integer, Map<Integer, Double>> neighbourMatrix = calulateNeighborJoiningMatrix(matrixAsMap, totalDistance, n, validRowOrColumn);
		int[] index = getMinumumInMatrix(neighbourMatrix, n);

		matrixAsMap.put(idx, new HashMap<>());
		for (int k : validRowOrColumn) {
			matrixAsMap.get(idx).put(k, (matrixAsMap.get(k).get(index[0]) + matrixAsMap.get(k).get(index[1]) - matrixAsMap.get(index[0]).get(index[1])) / 2);
			matrixAsMap.get(k).put(idx, (matrixAsMap.get(k).get(index[0]) + matrixAsMap.get(k).get(index[1]) - matrixAsMap.get(index[0]).get(index[1])) / 2);
		}
		matrixAsMap.get(idx).put(idx, 0.0);

		validRowOrColumn.remove(index[0]);
		validRowOrColumn.remove(index[1]);
		validRowOrColumn.add(idx);
		graph = helper(matrixAsMap, n - 1, idx + 1, validRowOrColumn);

		double delta = (totalDistance.get(index[0]) - totalDistance.get(index[1])) / (n - 2);
		double limbLen1 = (matrixAsMap.get(index[0]).get(index[1]) + delta) / 2;
		double limbLen2 = (matrixAsMap.get(index[0]).get(index[1]) - delta) / 2;

		addEdgeInGraph(idx, graph, index, limbLen1, limbLen2);

		return graph;
	}

	private static void addEdgeInGraph(int idx, Map<Integer, List<Pair>> graph, int[] index, double limbLen1, double limbLen2) {
		if (graph.get(idx) == null) {
			graph.put(idx, new LinkedList<>());
		}
		graph.get(idx).add(new Pair(index[0], limbLen1));
		graph.get(idx).add(new Pair(index[1], limbLen2));
		graph.put(index[0], new LinkedList<>());
		graph.put(index[1], new LinkedList<>());
		graph.get(index[0]).add(new Pair(idx, limbLen1));
		graph.get(index[1]).add(new Pair(idx, limbLen2));
	}

	private static int[] getMinumumInMatrix(Map<Integer, Map<Integer, Double>> neighbourMatrix, int n) {
		double min = Integer.MAX_VALUE;
		int index[] = new int[2];
		for (int row : neighbourMatrix.keySet()) {
			for (int col : neighbourMatrix.get(row).keySet()) {
				if (neighbourMatrix.get(row).get(col) < min && row != col) {
					min = neighbourMatrix.get(row).get(col);
					index[0] = row;
					index[1] = col;
				}
			}
		}
		return index;
	}

	private static Map<Integer, Map<Integer, Double>> calulateNeighborJoiningMatrix(Map<Integer, Map<Integer, Double>> matrix, Map<Integer, Double> totalDistance, int n,
																					Set<Integer> validRowOrColumn) {
		Map<Integer, Map<Integer, Double>> res = new LinkedHashMap<>();
		for (int row : validRowOrColumn) {
			res.put(row, new HashMap<>());
			for (int col : validRowOrColumn) {
				if (col != row) {
					res.get(row).put(col, (n - 2) * matrix.get(row).get(col) - totalDistance.get(row) - totalDistance.get(col));
				} else {
					res.get(row).put(col, 0.0);
				}
			}
		}
		return res;
	}

	private static Map<Integer, Double> getTotalDistance(Map<Integer, Map<Integer, Double>> matrix, Set<Integer> validRowOrColumn) {
		Map<Integer, Double> totalDistance = new LinkedHashMap<>();
		for (int row : validRowOrColumn) {
			double total = 0;
			for (int col : validRowOrColumn) {
				total += matrix.get(row).get(col);
			}
			totalDistance.put(row, total);
		}
		return totalDistance;
	}

	private static Map<Integer, Map<Integer, Double>> createMatrix(int[][] matrix, int n) {
		Map<Integer, Map<Integer, Double>> res = new LinkedHashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (res.get(i) != null) {
					res.get(i).put(j, matrix[i][j] * 1.0);
				} else {
					Map<Integer, Double> map = new HashMap<>();
					map.put(j, matrix[i][j] * 1.0);
					res.put(i, map);
				}
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_10333_7.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		int[][] matrix = new int[n][n];
		for (int i = 1; i < splits.length; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 1][k] = list.get(k);
			}
		}
		printGraph(NeighborJoining(matrix, n));
	}

	private static void printGraph(Map<Integer, List<Pair>> graph) {
		for (int node : graph.keySet()) {
			for (Pair p : graph.get(node)) {
				System.out.println(node + "->" + p.des + ":" + String.format("%.3f", p.dis));
			}
		}
	}
}
