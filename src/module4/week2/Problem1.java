package module4.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static module3.common.PrintUtils.printMatrix;
import static module4.PrintUtils.printGraph;

public class Problem1 {

	public static final DecimalFormat format  = new DecimalFormat("#.##");
	/**
	 * UPGMA(D, n)
	 *   Clusters ← n single-element clusters labeled 1, ... , n
	 *   construct a graph T with n isolated nodes labeled by single elements 1, ... , n
	 * for every node v in T
	 *         Age(v) ← 0
	 * while there is more than one cluster
	 * find the two closest clusters Ci and Cj
	 * merge Ci and Cj into a new cluster Cnew with |Ci| + |Cj| elements
	 * add a new node labeled by cluster Cnew to T
	 * connect node Cnew to Ci and Cj by directed edges
	 * Age(Cnew) ← DCi, Cj / 2
	 * remove the rows and columns of D corresponding to Ci and Cj
	 * remove Ci and Cj from Clusters
	 * add a row/column to D for Cnew by computing D(Cnew, C) for each C in Clusters
	 * add Cnew to Clusters
	 * root ← the node in T corresponding to the remaining cluster
	 * for each edge (v, w) in T
	 * length of (v, w) ← Age(v) - Age(w)
	 * return T
	 */
	/**
	 * Code Challenge: Implement UPGMA.
	 * <p>
	 * Input: An integer n followed by a space separated n x n distance matrix.
	 * Output: An adjacency list for the ultrametric tree returned by UPGMA. Edge weights should be accurate to two decimal places (answers in the sample dataset below are provided to three decimal places).
	 * <p>
	 * Note on formatting: The adjacency list must have consecutive integer node labels starting from 0. The n leaves must be labeled 0, 1, ..., n - 1 in order of their appearance in the distance matrix. Labels for internal nodes may be labeled in any order but must start from n and increase consecutively.
	 */

	public static Map<Node, List<Node>> UPGMA(int[][] matrix, int n) {
		Map<Node, List<Node>> graph = new LinkedHashMap<>();
		List<Cluster> clusterList = new LinkedList<>();
		int nodeIdCount = n;

		// Convert matrix to HashMap
		for (int i = 0; i < n; i++) {
			Node node = new Node(i, 0);
			graph.put(node, new LinkedList<>());
			List<Node> list = new LinkedList<>();
			list.add(node);
			Cluster cluster = new Cluster(i, list, node);
			clusterList.add(cluster);
		}

		while (clusterList.size() > 1) {
			Cluster[] closetCluster = getClosestCluster(clusterList, matrix);
			double distance = getDistanceBetweenCluster(closetCluster[0], closetCluster[1], matrix);
			double age =  distance/ 2;
			Node newNode = new Node(nodeIdCount++, age);
			Cluster mergeCluster = mergeCluster(closetCluster, newNode);

			List<Node> edges = new LinkedList<>();

			double newDis1 = newNode.age - closetCluster[0].headNode.age;
			double newDis2 = newNode.age - closetCluster[1].headNode.age;
			edges.add(new Node(closetCluster[0].id, newDis1));
			edges.add(new Node(closetCluster[1].id, newDis2));
			graph.put(newNode, edges);
			graph.get(closetCluster[0].headNode).add(new Node(newNode.id, newDis1));
			graph.get(closetCluster[1].headNode).add(new Node(newNode.id, newDis2));

			clusterList.remove(closetCluster[0]);
			clusterList.remove(closetCluster[1]);
			clusterList.add(mergeCluster);
		}
		return graph;
	}

	private static Cluster mergeCluster(Cluster[] closetCluster, Node newNode) {
		List<Node> mergeNode = new LinkedList<>(closetCluster[0].nodes);
		mergeNode.addAll(closetCluster[1].nodes);
		return new Cluster(newNode.id, mergeNode, newNode);
	}

	private static Cluster[] getClosestCluster(List<Cluster> clusterList, int[][] matrix) {
		double dis = Integer.MAX_VALUE;
		Cluster[] res = new Cluster[2];
		for (int i = 0; i < clusterList.size(); i++) {
			for (int j = i; j < clusterList.size(); j++) {
				if (j != i) {
					double curDistance = getDistanceBetweenCluster(clusterList.get(i), clusterList.get(j), matrix);
					if (curDistance < dis) {
						res[0] = clusterList.get(i);
						res[1] = clusterList.get(j);
						dis = curDistance;
					}
				}
			}
		}
		return res;
	}

	private static double getDistanceBetweenCluster(Cluster c1, Cluster c2,int[][] matrix) {
		double dis = 0.0;
		for (Node node1 : c1.nodes) {
			for (Node node2 : c2.nodes) {
				dis += matrix[node1.id][node2.id];
			}
		}
		return dis / (double) c1.size / (double) c2.size;
	}


	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_10332_8.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		int[][] matrix = new int[n][n];
		for (int i = 1; i < splits.length; i++) {
			List<Integer> list = Arrays.stream(splits[i].split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
			for (int k = 0; k < n; k++) {
				matrix[i - 1][k] = list.get(k);
			}
		}
		printGraph(UPGMA(matrix, n));
	}
}
