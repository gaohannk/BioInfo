package module4.week1;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module4.week1.Problem2.LimbLength;

public class Problem3 {
	/**
	 * Code Challenge: Implement AdditivePhylogeny to solve the Distance-Based Phylogeny Problem.
	 * <p>
	 * Input: An integer n followed by a space-separated n x n distance matrix.
	 * Output: A weighted adjacency list for the simple tree fitting this matrix.
	 */
	/**
	 * AdditivePhylogeny(D)
	 *     n ← number of rows in D
	 *     if n = 2
	 *         return the tree consisting of a single edge of length D1,2
	 *     limbLength ← Limb(D, n)
	 *     for j ← 1 to n - 1
	 *         Dj,n ← Dj,n - limbLength
	 *         Dn,j ← Dj,n
	 *     (i, k) ← two leaves such that Di,k = Di,n + Dn,k
	 *     x ← Di,n
	 *     D ← D﻿ with row n and column n removed
	 *     T ← AdditivePhylogeny(D)
	 *     v ← the (potentially new) node in T at distance x from i on the path between i and k
	 *     add leaf n back to T by creating a limb (v, n) of length limbLength
	 *     return T
	 */
	public static Map<Integer, List<Pair>> AdditivePhylogeny(int[][] matrix, int n) throws Exception {
		if (n == 2) {
			Map<Integer, List<Pair>> graph = new HashMap<>();
			graph.put(0, List.of(new Pair(1, matrix[0][1])));
			graph.put(1, List.of(new Pair(0, matrix[1][0])));
			System.out.println("init:");
			printGraph(graph);
			return graph;
		}
		int limbLen = LimbLength(matrix, n, n - 1);
//		for (int j = 1; j < n - 1; j++) {
//			matrix[j][n - 1] -= limbLen;
//			matrix[n - 1][j] = matrix[n - 1][j];
//		}
		int[] iAndK = findIandK(matrix, n, limbLen);
		System.out.println("current n " + n+ " i:k  " + iAndK[0] + ":" + iAndK[1]);
		int x = matrix[iAndK[0]][n-1] - limbLen;
		System.out.println("x:" + x);
		Map<Integer, List<Pair>> graph = AdditivePhylogeny(matrix, n - 1);
		System.out.println("recursive");
		printGraph(graph);
		int v = getNodeAtDistance(iAndK[0], iAndK[1]-1, x, graph);
		if (graph.get(v) != null) {
			graph.get(v).add(new Pair(n - 1, limbLen));
		} else {
			graph.put(v, List.of(new Pair(n - 1, limbLen)));
		}
		return graph;
	}

	private static void printGraph(Map<Integer, List<Pair>> graph) {
		for (int node : graph.keySet()) {
			for (Pair p : graph.get(node)) {
				System.out.println(node + "->" + p.des + ":" + p.dis);
			}
		}
	}

	public static int[] findIandK(int[][] matrix, int n, int limbLength) throws Exception {
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				if (matrix[i][k] == matrix[i][n-1] + matrix[n-1][k] - 2 * limbLength) {
					return new int[]{i, k};
				}
			}
		}
		throw new Exception("Shouldn't happen.");
	}

	public static int getNodeAtDistance(int i, int k, int x, Map<Integer, List<Pair>> graph) throws Exception {
		List<Integer> path = getPath(i, k, graph); // this returns the nodes in the path between i and k
		System.out.println(path);
		int dist = 0;
		for (int idx = 1; idx < path.size(); idx++) {
			int nextLen = 0;
			for (Pair p : graph.get(path.get(idx - 1))) {
				if (p.dis == path.get(idx)) {
					nextLen = p.dis;
					break;
				}
			}
			dist += nextLen;
			if (dist == x) {
				return path.get(i); // yay, we found a node exactly at the required distance
			} else if (dist > x) {  // need to create a new node btw. path[i-1] and path[i]
				int newNodeId = graph.size();
				graph.get(path.get(idx)).add(new Pair(newNodeId, dist - x));
				int newToLastNode = nextLen - (dist - x);
				graph.get(path.get(idx - 1)).add(new Pair(newNodeId, newToLastNode));
				int finalIdx = idx;
				graph.get(path.get(idx - 1)).removeIf(p -> p.des == path.get(finalIdx));
				return newNodeId;
			}
		}
		throw new Exception("Shouldn't get here.");
	}

	private static List<Integer> getPath(int i, int k, Map<Integer, List<Pair>> graph) {
		List<Integer> res = new LinkedList<>();
		res.add(i);
		boolean visited[] = new boolean[graph.size()];
		visited[i] = true;
		dfs(k, graph, i, res, visited);
		System.out.println("path:"+ res);
		return res;
	}

	private static void dfs(int k, Map<Integer, List<Pair>> graph, int cur, List<Integer> list, boolean[] visited) {
		for (Pair neibour : graph.get(cur)) {
			if (!visited[neibour.des]) {
				list.add(neibour.des);
				visited[neibour.des] = true;
				if (neibour.des != k) {
					dfs(k, graph, neibour.des, list, visited);
					list.remove(list.size() - 1);
					visited[neibour.des] = false;
				} else {
					return;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String text = Files.readString(Path.of("./resource/module4/dataset_10330_6.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		int[][] matrix = new int[n][n];
		for (int i = 1; i < splits.length; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 1][k] = list.get(k);
			}
		}
		Map<Integer, List<Pair>> graph = AdditivePhylogeny(matrix, n);
		for (int node : graph.keySet()) {
			for (Pair p : graph.get(node)) {
				System.out.println(node + "->" + p.des + ":" + p.dis);
			}
		}
	}
}
