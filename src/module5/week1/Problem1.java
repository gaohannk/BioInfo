package module5.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constant.INDEX;
import static module5.week1.PrintUtils.printTrie;


public class Problem1 {
    /**
     * Trie Construction Problem: Construct a trie from a set of patterns.
     *
     * Input: A collection of strings Patterns.
     * Output: Trie(Patterns).
     * The most obvious way to construct Trie(Patterns) is by iteratively adding each string from Patterns to the growing trie, as implemented by the following algorithm.
     *
     * TrieConstruction(Patterns)
     * Trie ← a graph consisting of a single node root
     * for each string Pattern in Patterns
     * currentNode ← root
     * for i ← 0 to |Pattern| - 1
     * currentSymbol ← Pattern[i]
     * if there is an outgoing edge from currentNode with label currentSymbol
     * currentNode ← ending node of this edge
     * else
     * add a new node newNode to Trie
     * add a new edge from currentNode to newNode with label currentSymbol
     * currentNode ← newNode
     * return Trie
     */
    public static TreeNode ConstructionProblem(List<String> patterns) {
        TreeNode root = new TreeNode(0);
        int id=1;
        for (String pattern : patterns) {
            TreeNode cur = root;
            for(char symbol : pattern.toCharArray()){
                if(cur.child[INDEX.get(symbol)] == null){
                    cur.child[INDEX.get(symbol)] = new TreeNode(id++);
                    cur = cur.child[INDEX.get(symbol)];
                }else{
                    cur = cur.child[INDEX.get(symbol)];
                }
            }
        }
        return root;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_294_4.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        List<String> patterns = Arrays.stream(splits).collect(Collectors.toList());
        TreeNode root = ConstructionProblem(patterns);
        printTrie(root);
    }
}
