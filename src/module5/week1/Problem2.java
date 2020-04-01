package module5.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.Constant.INDEX;
import static common.PrintUtils.printListByLine;
import static module5.week1.Problem1.ConstructionProblem;


public class Problem2 {
    /**
     * Code Challenge: Implement TrieMatching to solve the Multiple Pattern Matching Problem.
     *
     * Input: A string Text and a collection of strings Patterns.
     * Output: All starting positions in Text where a string from Patterns appears as a substring.
     */

    public static List<Integer> TrieMatching(List<String> patterns, String genmoText) {
        TreeNode root = ConstructionProblem(patterns);
        List<Integer> res = new LinkedList<>();
        int start = 0;
        while (genmoText.length() != 0) {
            String pattern = PrefixTrieMatching(genmoText, root);
            if (pattern != null) {
                res.add(start);
            }
            genmoText = genmoText.substring(1);
            start++;
        }

        return res;
    }

    /**
     * PrefixTrieMatching(Text, Trie)
     * symbol ← first letter of Text
     * v ← root of Trie
     * while forever
     * if v is a leaf in Trie
     * output the pattern spelled by the path from the root to v
     * else if there is an edge (v, w) in Trie labeled by symbol
     * symbol ← next letter of Text
     * v ← w
     * else
     * return "no matches found"
     *
     * @param text
     * @param trie
     */
    public static String PrefixTrieMatching(String text, TreeNode trie) {
        int i = 0;
        TreeNode cur = trie;
        StringBuilder pattern = new StringBuilder();
        while (i <= text.length()) {
            if (cur.isLeaf()) {
                return pattern.toString();
            }
            if(i==text.length()){
                break;
            }
            char c = text.charAt(i);
            if (cur.child[INDEX.get(c)] == null) {
               break;
            }
            pattern.append(c);
            cur = cur.child[INDEX.get(c)];
            i++;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_294_8.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        List<String> patterns = Arrays.stream(splits).collect(Collectors.toList());
        String genmoText = patterns.remove(0);
        printListByLine(TrieMatching(patterns, genmoText));
    }
}
