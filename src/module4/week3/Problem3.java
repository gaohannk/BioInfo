package module4.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Problem3 {

	/**
	 * Code Challenge: Solve the Nearest Neighbors of a Tree Problem.
	 * <p>
	 * Input: Two internal nodes a and b specifying an edge e, followed by an adjacency list of an unrooted binary tree.
	 * Output: Two adjacency lists representing the nearest neighbors of the tree with respect to e. Separate the adjacency lists with a blank line.
	 * Extra Dataset
	 **/
	// TODO alogirthm is incorrect, need to change
	public static Node[] NearestNeighborsOfTree(Node src, Node dest) {
		Node left = src.left;
		Node right = dest.right;
		src.left = right;
		dest.right = left;
		return new Node[]{src, dest};
	}

	public static Node[] NearestNeighborsofTree2(Node src, Node dest) {
		Node leftRight = src.right;
		Node rightRight = dest.right;
		src.right = rightRight;
		dest.right = leftRight;
		return new Node[]{src, dest};
	}


	private static void printEdgeInTree(Node root) {
		if (root == null) {
			return;
		}
		if (root.left != null) {
			System.out.println(getEdge(root, root.left));
			System.out.println(getEdge(root.left, root));
		}
		if (root.right != null) {
			System.out.println(getEdge(root, root.right));
			System.out.println(getEdge(root.right, root));
		}
		printEdgeInTree(root.left);
		printEdgeInTree(root.right);
	}

	private static String getEdge(Node src, Node dest) {
		StringBuilder sb = new StringBuilder();
		sb.append(src.id);
		sb.append("->");
		sb.append(dest.id);
		return sb.toString();
	}

	private static Map<Integer, Node> constructTree(String[] splits) {
		int id;
		Map<Integer, Node> nodeMap = new HashMap<>();
		for (int i = 1; i < splits.length - 2; i += 4) {
			id = Integer.parseInt(splits[i + 1].split("->")[0]);
			int left = Integer.parseInt(splits[i + 1].split("->")[1]);
			int right = Integer.parseInt(splits[i + 3].split("->")[1]);
			Node node = new Node(id);
			Node leftNode = new Node(left);
			Node rightNode = new Node(right);
			node.left = leftNode;
			node.right = rightNode;
			nodeMap.put(node.id, node);
		}

		int left = Integer.parseInt(splits[splits.length-1].split("->")[0]);
		int right = Integer.parseInt(splits[splits.length-1].split("->")[1]);

		return nodeMap;
	}

	static class Node {
		public Node left;
		public Node right;
		public int id;

		public Node(int id) {
			this.id = id;
		}

		public Node(int id, Node left, Node right) {
			this.id = id;
			this.left = left;
			this.right = right;
		}

		public String toString() {
			return "Node{" +
					"id=" + id +
					", left=" + left +
					", right=" + right +
					'}';
		}
	}


	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_10336_6.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int src = Integer.parseInt(splits[0].split(" ")[0]);
		int dest = Integer.parseInt(splits[0].split(" ")[1]);
		Map<Integer, Node> map = constructTree(splits);
//		System.out.println(map);
//		Node left = map.get(src);
//		Node right = map.get(dest);
//		printEdgeInTree(map.get(src));
//		printEdgeInTree(map.get(dest));
//		System.out.println(getEdge(left, right));
//		System.out.println(getEdge(right, left));

		Node[] root = NearestNeighborsOfTree(map.get(src), map.get(dest));
		System.out.println();
		printEdgeInTree(root[0]);
		printEdgeInTree(root[1]);
		System.out.println(getEdge(root[0], root[1]));
		System.out.println(getEdge(root[1], root[0]));
		System.out.println();

		map = constructTree(splits);
		root = NearestNeighborsofTree2(map.get(src), map.get(dest));
		printEdgeInTree(root[0]);
		printEdgeInTree(root[1]);
		System.out.println(getEdge(root[0], root[1]));
		System.out.println(getEdge(root[1], root[0]));
	}
}