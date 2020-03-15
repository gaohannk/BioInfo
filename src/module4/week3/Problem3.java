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
    public static Node[] NearestNeighborsOfTree(Node src, Node dest) {
		Node srcLeft = src.left;
		Node srcRight = src.right;
		Node destLeft = dest.left;
		Node destLight = dest.right;

		Node newLeftRoot = new Node(src);
		newLeftRoot.right = destLeft;
		newLeftRoot.left = srcLeft;


		Node newRightRoot = new Node(dest);
		newRightRoot.right = destLight;
		newRightRoot.left = srcRight;
		return new Node[]{newLeftRoot, newRightRoot};
    }

	public static Node[] NearestNeighborsOfTree2(Node src, Node dest) {
		Node srcLeft = src.left;
		Node srcRight = src.right;
		Node destLeft = dest.left;
		Node destLight = dest.right;

		Node newLeftRoot = new Node(src);
		newLeftRoot.right = destLight;
		newLeftRoot.left = srcLeft;

		Node newRightRoot = new Node(dest);
		newRightRoot.right = srcRight;
		newRightRoot.left = destLeft;
		return new Node[]{newLeftRoot, newRightRoot};
    }


    private static Node[] constructTree(Map<Integer, List<Integer>> map, int src, int dest) {
        Node leftRoot = new Node(src);
        Node rightRoot = new Node(dest);
        helper(leftRoot, map, dest);
        helper(rightRoot, map, src);
        return new Node[]{leftRoot, rightRoot};
    }

    private static void helper(Node root, Map<Integer, List<Integer>> map, int parent) {
        for (int node : map.get(root.id)) {
            if (node != parent) {
                if (root.left == null) {
                    root.left = new Node(node);
					helper(root.left, map, root.id);
				} else if (root.right == null) { // else if, only choose one
                    root.right = new Node(node);
					helper(root.right, map, root.id);
				}
            }
        }
	}


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_10336_6.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        int src = Integer.parseInt(splits[0].split(" ")[0]);
        int dest = Integer.parseInt(splits[0].split(" ")[1]);
		Map<Integer, List<Integer>> map = new HashMap<>();

		for (int i = 1; i < splits.length; i++) {
            int[] edge = new int[2];
            edge[0] = Integer.parseInt(splits[i].split("->")[0]);
            edge[1] = Integer.parseInt(splits[i].split("->")[1]);
			map.putIfAbsent(edge[0], new LinkedList<>());
			map.get(edge[0]).add(edge[1]);
        }

		Node[] root = constructTree(map, src, dest);

		Node[] newRoot = NearestNeighborsOfTree(root[0], root[1]);
		TreeUtils.printTree(newRoot);

		newRoot = NearestNeighborsOfTree2(root[0], root[1]);
		TreeUtils.printTree(newRoot);
	}
}
