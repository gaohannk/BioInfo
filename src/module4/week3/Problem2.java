package module4.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.Constant.nucle;
import static module4.week3.Node.getHammingDistance;
import static module4.week3.TreeUtils.*;

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
     */

    public static Node[] SmallParsimonyInUnrootedTree(Node[] root) {
        // post order traversal
        // process for each char
        Node vitrualRoot = new Node(-1, root[0].character.length, root[0], root[1]);
        for (int idx = 0; idx < vitrualRoot.character.length; idx++) {
            init(vitrualRoot, idx);
            populateSymbolMap(vitrualRoot);
            int min = Integer.MAX_VALUE;
            for (char c : nucle) {
                int value = vitrualRoot.symbolMap.get(c);
                if (min >= value) {
                    min = value;
                    vitrualRoot.character[idx] = c;
                }
            }
            assignSymbol(vitrualRoot, idx);
        }
        calculateParsimonyScore(vitrualRoot);
        return new Node[]{vitrualRoot.left, vitrualRoot.right};
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

    private static void populateSymbolMap(Node node) {
        if (node.left == null || node.right == null) {
            return;
        }
        populateSymbolMap(node.left);
        populateSymbolMap(node.right);

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


    /**
     * Assume the input is:
     * 1. every two line is two direction of edge
     * 2. every four line is left right child of the same parent
     * 3. leaf before internal node
     *
     * @param splits
     * @param n
     */
    @Deprecated
    private static Node constructTree(String[] splits, int n) {
        int leaves = 0;
        int id = -1;
        int len = splits[1].split("->")[0].toCharArray().length;
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 1; i < 1 + 2 * n; i += 4) {
            id = Integer.parseInt(splits[i + 1].split("->")[0]);
            char[] leftChars = splits[i + 1].split("->")[1].toCharArray();
            char[] rightChars = splits[i + 3].split("->")[1].toCharArray();
            Node left = new Node(leaves++, leftChars);
            Node right = new Node(leaves++, rightChars);
            Node node = new Node(id, len, left, right);
            node.left = left;
            node.right = right;
            nodeMap.put(node.id, node);
        }
        for (int i = 2 * n + 1; i < splits.length - 2; i += 4) {
            id = Integer.parseInt(splits[i + 1].split("->")[0]);
            int left = Integer.parseInt(splits[i + 1].split("->")[1]);
            int right = Integer.parseInt(splits[i + 3].split("->")[1]);
            Node node = new Node(id, len, nodeMap.get(left), nodeMap.get(right));
            nodeMap.put(node.id, node);
        }
        id++;
        Node root = new Node(id, len, nodeMap.get(id - 2), nodeMap.get(id - 1));
        return root;
    }

    private static Node[] constructTree2(Map<Integer, List<String>> nodeToLeaf, Map<Integer, List<Integer>> parentToChild, int src, int dest, int len) {
        Node leftRoot = new Node(src, len);
        Node rightRoot = new Node(dest, len);
        int[] id = new int[]{0};
        helper(leftRoot, nodeToLeaf, parentToChild, len, id, dest);
        helper(rightRoot, nodeToLeaf, parentToChild, len, id, dest);
        return new Node[]{leftRoot, rightRoot};
    }

    private static void helper(Node root, Map<Integer, List<String>> nodeToLeaf, Map<Integer, List<Integer>> parentToChild, int len, int[] id, int maxId) {
    	for (int node : parentToChild.getOrDefault(root.id, Collections.emptyList())) {
    		if(node!= maxId-1) {
				if (root.left == null) {
					root.left = new Node(node, len);
					helper(root.left, nodeToLeaf, parentToChild, len, id, maxId);
				} else if (root.right == null) { // else if, only choose one
					root.right = new Node(node, len);
					helper(root.right, nodeToLeaf, parentToChild, len, id, maxId);
				}
			}
        }
        for (String sequence : nodeToLeaf.getOrDefault(root.id, Collections.emptyList())) {
            if (root.left == null) {
                root.left = new Node(id[0], sequence.toCharArray());
            } else if (root.right == null) { // else if, only choose one
                root.right = new Node(id[0], sequence.toCharArray());
            }
            id[0]++;
        }
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_10335_12.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
//        int n = Integer.parseInt(splits[0]);
//        Node root = constructTree(splits, n);
//        root = SmallParsimonyInUnrootedTree(root);
//        System.out.println(root.left.score + root.right.score + getHammingDistance(root.left.character, root.right.character));
//        printEdgeInTree(root.left);
//        printEdgeInTree(root.right);
//        System.out.println(getEdge(root.left.character, root.right.character));
//        System.out.println(getEdge(root.right.character, root.left.character));

        Map<Integer, List<String>> nodeToLeaf = new HashMap<>();
        Map<Integer, List<Integer>> parentToChild = new HashMap<>();
        int maxId = 0;
        for (int i = 1; i < splits.length; i++) {
            String left = splits[i].split("->")[0];
            String right = splits[i].split("->")[1];
            boolean isLeftDigit = Character.isDigit(left.charAt(0));
            boolean isRightDigit = Character.isDigit(right.charAt(0));
            if (!isLeftDigit) {
                continue;
            }
            int parentId = Integer.parseInt(left);
            if (isLeftDigit && isRightDigit) {
                int childId = Integer.parseInt(right);
                if (parentId > childId) {
                    parentToChild.putIfAbsent(parentId, new LinkedList<>());
                    parentToChild.get(parentId).add(childId);
                }
            } else if (isLeftDigit && !isRightDigit) {
                nodeToLeaf.putIfAbsent(parentId, new LinkedList<>());
                nodeToLeaf.get(parentId).add(right);
            }
            maxId = Math.max(parentId, maxId);
        }

        int len = nodeToLeaf.values().iterator().next().get(0).length();
        Node[] root = constructTree2(nodeToLeaf, parentToChild, maxId-1, maxId, len);
		root = SmallParsimonyInUnrootedTree(root);
        int score = root[0].score + root[1].score + getHammingDistance(root[0].character, root[1].character);
        System.out.println(score);
        printEdgeInTree(root);
    }
}
