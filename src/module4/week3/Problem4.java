package module4.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static module4.week3.Node.getHammingDistance;
import static module4.week3.Problem2.SmallParsimonyInUnrootedTree;
import static module4.week3.Problem3.NearestNeighborsOfTree;
import static module4.week3.TreeUtils.printEdgeInTree;
import static module4.week3.TreeUtils.printTree;

public class Problem4 {

    /**
     * Code Challenge: Implement the nearest neighbor interchange heuristic for the Large Parsimony Problem.
     *
     * Input: An integer n, followed by an adjacency list for an unrooted binary tree whose n leaves are labeled by DNA strings and whose internal nodes are labeled by integers.
     * Output: The parsimony score and unrooted labeled tree obtained after every step of the nearest neighbor interchange heuristic. Each step should be separated by a blank line.
     * Note: Depending on how your code breaks ties, you may obtain a different solution than the one we provide.  As a result, the parsimony score at each step may vary.
     *
     * NearestNeighborInterchange(Strings)
     * score ← ∞
     * generate an arbitrary unrooted binary tree Tree with |Strings| leaves
     * label the leaves of Tree by arbitrary strings from Strings
     * solve  the  Small Parsimony in an Unrooted Tree Problem for Tree
     * label the internal nodes of Tree according to a most parsimonious labeling
     * newScore ← the parsimony score of Tree
     * newTree ← Tree
     * while newScore < score
     * score ← newScore
     * Tree ← newTree
     * for each internal edge e in Tree
     * for each nearest neighbor NeighborTree of Tree with respect to the edge e
     * solve the Small Parsimony in an Unrooted Tree Problem for NeighborTree
     * neighborScore ← the minimum parsimony score of NeighborTree
     * if neighborScore < newScore
     * newScore ← neighborScore
     * newTree ← NeighborTree
     * return newTree
     **/
    public static Node[] NearestNeighborInterchange(Node[] root) {
        int score = Integer.MAX_VALUE;

        Node[] newRoot = SmallParsimonyInUnrootedTree(root);
        int newScore = root[0].score + root[1].score + getHammingDistance(root[0].character, root[1].character);
        while (newScore < score) {
            score = newScore;
            root = newRoot;
            List<Node[]> internalEdges = getInternalEdge(root);
            //internalEdges.forEach(a->printEdgeInTree(a));
            for (Node[] edge : internalEdges) {
                Node[] neibourRoot = NearestNeighborsOfTree(edge[0], edge[1]);
                System.out.println("++++++");
                printTree(neibourRoot);
                System.out.println("++++++");
                neibourRoot = SmallParsimonyInUnrootedTree(neibourRoot);
                int neibourScore = neibourRoot[0].score + neibourRoot[1].score + getHammingDistance(neibourRoot[0].character, neibourRoot[1].character);
                SmallParsimonyInUnrootedTree(root);
                if (neibourScore < newScore) {
                    newScore = neibourScore;
                    newRoot = neibourRoot;
                }
            }
            System.out.println(newScore);
            printEdgeInTree(newRoot);
        }
        return newRoot;
    }

    private static List<Node[]> getInternalEdge(Node[] root) {
        List<Node[]> list = new LinkedList<>();
        helper(root[0], list, root[1]);
        helper(root[1], list,  root[0]);
        list.add(new Node[]{root[0], root[1]});
        return list;
    }

    private static void helper(Node node, List<Node[]> list, Node parent) {
        if (node.left != null) {
            Node left = node.left;
            node.left = parent;
            list.add(new Node[]{node, left});
            helper(left, list, node);
        }
        if (node.right != null) {
            Node right = node.right;
            node.right = parent;
            list.add(new Node[]{node, right});
            helper(right, list, node);
        }
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
            if (node != maxId - 1) {
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
        String text = Files.readString(Path.of("./resource/module4/dataset_10336_8.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");

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
        System.out.println(nodeToLeaf);
        System.out.println(parentToChild);
        int len = nodeToLeaf.values().iterator().next().get(0).length();
        Node[] root = constructTree2(nodeToLeaf, parentToChild, maxId - 1, maxId, len);
        printTree(root);
        root = NearestNeighborInterchange(root);
        int score = root[0].score + root[1].score + getHammingDistance(root[0].character, root[1].character);
        System.out.println(score);
        printEdgeInTree(root);
    }
}
