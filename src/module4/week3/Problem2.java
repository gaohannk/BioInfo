package module4.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    public static Node SmallParsimonyInUnrootedTree(Node root) {
        // post order traversal
        for (int idx = 0; idx < root.character.length; idx++) {
            init(root, idx);
            helper(root, idx);
        }
        return root;
    }

    private static int getParsimonyScore(Node root) {
        if (root.left == null || root.right == null) {
            return 0;
        }
        int sum = 0;
        sum += getParsimonyScore(root.left) + getParsimonyScore(root.right) + getHammingDistance(root.character, root.left.character) + getHammingDistance(root.character, root.right.character);
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

    private static void helper(Node node, int idx) {
        if (node.left == null || node.right == null) {
            return;
        }
        helper(node.left, idx);
        helper(node.right, idx);
        int globalMin = Integer.MAX_VALUE;

        for (char c : node.symbolMap.keySet()) {
            int min = Integer.MAX_VALUE;
            for (char i : node.symbolMap.keySet()) {
                for (char j : node.symbolMap.keySet()) {
                    if (node.left.symbolMap.get(i) == Integer.MAX_VALUE || node.right.symbolMap.get(j) == Integer.MAX_VALUE) {
                        continue;
                    }
                    min = Math.min(min, node.left.symbolMap.get(i) + (i == c ? 0 : 1) + node.right.symbolMap.get(j) + (j == c ? 0 : 1));
                }
            }
            node.symbolMap.put(c, min);
            if (globalMin >= min) {
                globalMin = min;
                node.character[idx] = c;
            }
        }
    }

    private static void printEdgeInTree(Node root) {
        if (root.left == null || root.right == null) {
            return;
        }
        System.out.println(getEdge(root.character, root.left.character));
        System.out.println(getEdge(root.left.character, root.character));
        System.out.println(getEdge(root.character, root.right.character));
        System.out.println(getEdge(root.right.character, root.character));
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

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_10335_10.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        int n = Integer.parseInt(splits[0]);
        int leaves = 0;
        int id = -1;
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 1; i < 1 + n; i += 2) {
            id = Integer.parseInt(splits[i].split("->")[0]);
            char[] leftChars = splits[i].split("->")[1].toCharArray();
            char[] rightChars = splits[i + 1].split("->")[1].toCharArray();
            Node node = new Node(id, leftChars.length);
            Node left = new Node(leaves++, leftChars);
            Node right = new Node(leaves++, rightChars);
            node.left = left;
            node.right = right;
            nodeMap.put(node.id, node);
        }
        for (int i = n + 1; i < splits.length; i += 2) {
            id = Integer.parseInt(splits[i].split("->")[0]);
            int left = Integer.parseInt(splits[i].split("->")[1]);
            int right = Integer.parseInt(splits[i + 1].split("->")[1]);
            Node node = new Node(id, nodeMap.get(left).character.length);
            node.left = nodeMap.get(left);
            node.right = nodeMap.get(right);
            nodeMap.put(node.id, node);
        }
        Node root = SmallParsimonyInUnrootedTree(nodeMap.get(id));
        int score = getParsimonyScore(root);
        System.out.println(score);
        printEdgeInTree(root);
    }

    private static void printId(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("Id is:" + root.id);
        printId(root.left);
        printId(root.right);
    }

    private static void printChars(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("Chars is:" + Arrays.toString(root.character));
        printChars(root.left);
        printChars(root.right);
    }
}
