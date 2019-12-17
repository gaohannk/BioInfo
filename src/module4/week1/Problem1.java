package module4.week1;

import module4.common.Pair;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static module3.common.PrintUtils.printMatrix;

public class Problem1 {
	/**
	 * Distances Between Leaves Problem: Compute the distances between leaves in a weighted tree.
	 * <p>
	 * Input:  An integer n followed by the adjacency list of a weighted tree with n leaves.
	 * Output: An n x n matrix (di,j), where di,j is the length of the path between leaves i and j.
	 *
	 */
	public static int[][] DistancesBetweenLeaves(Map<Integer, List<Pair>> graph, int n) {
		int[][] res = new int[n][n];
		for(Integer leaf : graph.keySet()){
			if(graph.get(leaf).size() == 1){
				int sum = 0;
				boolean visited[] = new boolean[graph.keySet().size()];
				visited[leaf] = true;
				dfs(graph, sum, res, leaf, leaf, n, visited);
			}
		}
		return res;
	}

	private static void dfs(Map<Integer, List<Pair>> graph, int sum, int[][] res, Integer leaf, int cur, int n, boolean[] visited) {
		for(Pair neibour : graph.get(cur)){
			if(!visited[neibour.des]) {
				if (neibour.des >= n) {
					visited[neibour.des] = true;
					dfs(graph, sum + neibour.dis, res, leaf, neibour.des, n, visited);
					visited[neibour.des] = false;
				} else {
					if (neibour.des != leaf) {
						res[leaf][neibour.des] = sum + neibour.dis;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_10328_12.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		Map<Integer, List<Pair>> graph = new HashMap<>();
		for (int i = 1; i < splits.length; i++) {
			int id = Integer.parseInt(splits[i].split("->")[0]);
			String str = splits[i].split("->")[1];
			Pair pair = new Pair(Integer.parseInt(str.split(":")[0]), Integer.parseInt(str.split(":")[1]));
			if (graph.get(id) == null) {
				List<Pair> list = new LinkedList<>();
				list.add(pair);
				graph.put(id, list);
			} else {
				graph.get(id).add(pair);
			}
		}
		printMatrix(DistancesBetweenLeaves(graph, n), "	");
	}
}
