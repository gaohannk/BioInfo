package module5.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Problem3 {
    /**
     * Code Challenge: Solve the Suffix Tree Construction Problem.
     *
     * Input: A string Text.
     * Output: The edge labels of SuffixTree(Text). You may return these strings in any order.
     */

    /**
     * ModifiedSuffixTrieConstruction(Text)
     * Trie ← a graph consisting of a single node root
     * for i ← 0 to |Text| - 1
     * currentNode ← root
     * for j ← i to |Text| - 1
     * currentSymbol ← j-th symbol of Text
     * if there is an outgoing edge from currentNode labeled by currentSymbol
     * currentNode ← ending node of this edge
     * else
     * add a new node newNode to Trie
     * add an edge newEdge connecting currentNode to newNode in Trie
     * Symbol(newEdge) ← currentSymbol
     * Position(newEdge) ← j
     * currentNode ← newNode
     * if currentNode is a leaf in Trie
     * assign label i to this leaf
     * return Trie
     */

    public static Map<Character, Integer> INDEX = new HashMap<>() {{
        put('A', 0);
        put('C', 1);
        put('G', 2);
        put('T', 3);
        put('$', 4);
    }};

    public static Map<Integer, Character> INDEX_REV = new HashMap<>() {{
        put(0, 'A');
        put(1, 'C');
        put(2, 'G');
        put(3, 'T');
        put(4, '$');
    }};

    public static SuffixTreeNode SuffixTreeConstruction(String text) {
        SuffixTreeNode root = new SuffixTreeNode(5);
        for (int i = 0; i < text.length(); i++) {
            SuffixTreeNode cur = root;
            for (int j = i; j < text.length(); j++) {
                char symbol = text.charAt(j);
                if (cur.children[INDEX.get(symbol)] == null) {
                    cur.children[INDEX.get(symbol)] = new SuffixTreeNode(5);
                    if (cur.start[INDEX.get(symbol)] == null) {
                        cur.start[INDEX.get(symbol)] = j;
                        cur.length[INDEX.get(symbol)] = 1;
                    }
                    cur = cur.children[INDEX.get(symbol)];
                } else {
                    cur = cur.children[INDEX.get(symbol)];
                }
            }
            cur.isLeaf = true;
            cur.startIdx = i;
        }
        return root;
    }

    public SuffixTreeNode compressSuffixTree(SuffixTreeNode root) {
        if (root.isLeaf) {
            return root;
        }
        StringBuilder sb = new StringBuilder();
        SuffixTreeNode cur = root;
        int index = -1;
        if (isOneChild(root)) {
            index = getChildIndex(root);
            while (isOneChild(cur)) {
                for (int i = 0; i < cur.children.length; i++) {
                    if (cur.children[i] != null) {
                        sb.append(INDEX_REV.get(i));
                        cur = cur.children[i];
                    }
                }
            }
            root.length[index] = sb.length();
            root.children[index] = compressSuffixTree(cur);
        } else {
            for (int i = 0; i < root.children.length; i++) {
                if (root.children[i] != null) {
                    root.children[i] = compressSuffixTree(root.children[i]);
                }
            }
        }
        return root;
    }

    private int getChildIndex(SuffixTreeNode root) {
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
                return i;
            }
        }
        return -1;
    }

    private boolean isOneChild(SuffixTreeNode root) {
        int count = 0;
        for (SuffixTreeNode child : root.children) {
            if (child != null) {
                count++;
            }
        }
        return count == 1;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/1.txt"), Charset.forName("UTF-8"));
        //printEdge(SuffixTreeConstruction(text), text);
        System.out.println("AAATCCCCCCCTCGCGACTTCTAGAGAAGAAGAGTACT".length());
    }

    public static void printEdge(SuffixTreeNode root, String text) {
        for (int i = 0; i < root.children.length; i++) {
            SuffixTreeNode child = root.children[i];
            if (child != null && !child.isLeaf) {
                System.out.print(child.start[i] +":");
                System.out.print(child.start[i] + child.length[i]);

                System.out.println(text.substring(child.start[i], child.start[i] + child.length[i]));
                printEdge(root.children[i], text);
            }
        }
    }
}
