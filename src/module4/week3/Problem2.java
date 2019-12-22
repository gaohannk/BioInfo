package module4.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.Constant.nucle;

public class Problem2 {

	/**
	 Code Challenge: Solve the Small Parsimony in an Unrooted Tree Problem.

	 Input: An integer n followed by an adjacency list for an unrooted binary tree with n leaves labeled by DNA strings.
	 Output: The minimum parsimony score of this tree, followed by the adjacency list of the tree corresponding to labeling internal nodes by DNA strings in order to minimize the parsimony score of the tree.
	 */

	/**
	 * SmallParsimony(T, Character)
	 * for each node v in tree T
	 * Tag(v) ← 0
	 * if v is a leaf
	 * Tag(v) ← 1
	 * for each symbol k in the alphabet
	 * if Character(v) = k
	 * sk(v) ← 0
	 * else
	 * sk(v) ← ∞
	 * while there exist ripe nodes in T
	 * v ← a ripe node in T
	 * Tag(v) ← 1
	 * for each symbol k in the alphabet
	 * sk(v) ← minimumall symbols i {si(Daughter(v))+αi,k} + minimumall symbols j {sj(Son(v))+αj,k}
	 * return minimum over all symbols k {sk(v)}
	 */
	/**
	 * With the backtracking, what I did was this:
	 * <p>
	 * 1) start at the root.
	 * <p>
	 * 2) For each child, get candidates with lowest scores (already calculated).
	 * <p>
	 * 3) If one of those candidates is the same as the parent, use that, otherwise just pick any one (I used the first one)
	 *
	 * @param root
	 * @return
	 */
	public static Node SmallParsimonyInUnrootedTree(Node root) {
		// post order traversal
		for (int idx = 0; idx < root.character.length; idx++) {
			init(root, idx);
			helper(root);
			int min = Integer.MAX_VALUE;
			for (char c : nucle) {
				int value = root.symbolMap.get(c);
				if (min > value) {
					min = value;
					root.character[idx] = c;
				}
			}
			assignSymbol(root, idx);
		}
		calculateParsimonyScore(root);
		return root;
	}

	private static void assignSymbol(Node node, int idx) {
		if (node.left == null || node.right == null) {
			return;
		}
		getSymbolWithMinValue(node.left, idx, node.character[idx]);
		getSymbolWithMinValue(node.right, idx, node.character[idx]);
		assignSymbol(node.left, idx);
		assignSymbol(node.right, idx);
	}

	public static void getSymbolWithMinValue(Node node, int idx, char parentSymbol) {
		int min = Integer.MAX_VALUE;
		Set<Character> candidate = new HashSet<>();
		for (char c : nucle) {
			int value = node.symbolMap.get(c);
			if (min > value) {
				min = value;
				candidate.clear();
				candidate.add(c);
			} else if (min == value) {
				candidate.add(c);
			}
		}
		for (char c : candidate) {
			if (c == parentSymbol) {
				node.character[idx] = c;
				return;
			}
			node.character[idx] = c;
		}
	}

	private static int calculateParsimonyScore(Node root) {
		if (root.left == null || root.right == null) {
			return 0;
		}
		int sum = 0;
		sum += calculateParsimonyScore(root.left) + calculateParsimonyScore(root.right)
				+ getHammingDistance(root.character, root.left.character) + getHammingDistance(root.character, root.right.character);
		root.score = sum;
		return sum;
	}

	private static int getHammingDistance(char[] v, char[] w) {
		int count = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] != w[i])
				count++;
		}
		return count;
	}

	private static void init(Node node, int idx) {
		if (node.left == null || node.right == null) {
			for (char c : nucle) {
				if (c == node.character[idx]) {
					node.symbolMap.put(c, 0);
				} else {
					node.symbolMap.put(c, Integer.MAX_VALUE);
				}
			}
			return;
		}
		init(node.left, idx);
		init(node.right, idx);
	}

	private static void helper(Node node) {
		if (node.left == null || node.right == null) {
			return;
		}
		helper(node.left);
		helper(node.right);

		for (char c : nucle) {
			int min = Integer.MAX_VALUE;
			for (char i : nucle) {
				for (char j : nucle) {
					if (node.left.symbolMap.get(i) == Integer.MAX_VALUE || node.right.symbolMap.get(j) == Integer.MAX_VALUE) {
						continue;
					}
					min = Math.min(min, node.left.symbolMap.get(i) + (i == c ? 0 : 1) + node.right.symbolMap.get(j) + (j == c ? 0 : 1));
				}
			}
			node.symbolMap.put(c, min);
		}
	}

	private static void printEdgeInTree(Node root) {
		if (root == null) {
			return;
		}
		if (root.left != null) {
			System.out.println(getEdge(root.character, root.left.character));
			System.out.println(getEdge(root.left.character, root.character));
		}
		if (root.right != null) {
			System.out.println(getEdge(root.character, root.right.character));
			System.out.println(getEdge(root.right.character, root.character));
		}
		printEdgeInTree(root.left);
		printEdgeInTree(root.right);
	}

	private static String getEdge(char[] src, char[] dest) {
		StringBuilder sb = new StringBuilder();
		for (char c : src) {
			sb.append(c);
		}
		sb.append("->");
		for (char c : dest) {
			sb.append(c);
		}
		sb.append(":");
		sb.append(getHammingDistance(src, dest));
		return sb.toString();
	}

	private static Node[] constructTree(String[] splits, int n) {
		int leaves = 0;
		int id = -1;
		Map<Integer, Node> nodeMap = new HashMap<>();
		for (int i = 1; i < 1 + 2 * n; i += 4) {
			id = Integer.parseInt(splits[i + 1].split("->")[0]);
			char[] leftChars = splits[i + 1].split("->")[1].toCharArray();
			char[] rightChars = splits[i + 3].split("->")[1].toCharArray();
			Node node = new Node(id, leftChars.length);
			Node left = new Node(leaves++, leftChars);
			Node right = new Node(leaves++, rightChars);
			node.left = left;
			node.right = right;
			nodeMap.put(node.id, node);
		}
		for (int i = 2 * n + 1; i < splits.length - 2; i += 4) {
			id = Integer.parseInt(splits[i + 1].split("->")[0]);
			int left = Integer.parseInt(splits[i + 1].split("->")[1]);
			int right = Integer.parseInt(splits[i + 3].split("->")[1]);
			Node node = new Node(id, nodeMap.get(left).character.length);
			node.left = nodeMap.get(left);
			node.right = nodeMap.get(right);
			nodeMap.put(node.id, node);
		}
		return new Node[]{nodeMap.get(id - 1), nodeMap.get(id)};
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_10335_12.txt"), Charset.forName("UTF-8"));
		String[] splits = text.split("\n");
		int n = Integer.parseInt(splits[0]);
		Node[] root = constructTree(splits, n);
		root[0] = SmallParsimonyInUnrootedTree(root[0]);
        root[1] = SmallParsimonyInUnrootedTree(root[1]);
        System.out.println(root[0].score+ root[1].score+ getHammingDistance(root[0].character, root[1].character));
		printEdgeInTree(root[0]);
	}
}
