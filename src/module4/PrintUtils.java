package module4;

import module4.week2.Cluster;
import module4.week2.Node;

import java.util.List;
import java.util.Map;

public class PrintUtils {
	public static void printGraph(Map<Node, List<Node>> graph) {
		for (Node node : graph.keySet()) {
			for (Node p : graph.get(node)) {
				System.out.println(node.id + "->" + p.id + ":" + String.format("%.3f", p.age));
			}
		}
	}

	private static void printMatrix(Map<Integer, Map<Integer, Double>> matrix) {
		System.out.println("Matrix as Value");
		for (int row : matrix.keySet()) {
			for (int column : matrix.get(row).keySet()) {
				System.out.print(matrix.get(row).get(column) + " ");
			}
			System.out.println();
		}
	}

	private static void printMatrixasMap(Map<Integer, Map<Integer, Double>> matrix) {
		System.out.println("Matrix as Map");
		for (int row : matrix.keySet()) {
			System.out.print(row + ":");
			for (int column : matrix.get(row).keySet()) {
				System.out.print(column + ",");
			}
			System.out.println();
		}
	}

	private static void printCluster(List<Cluster> clusters) {
		System.out.println("Cluster");
		for (Cluster cluster : clusters) {
			System.out.println("id: " + cluster.id + " size: " + cluster.size);
			for(Node node: cluster.nodes){
				System.out.print("node:" + node.id);
			}
			System.out.println();
		}
	}
}
