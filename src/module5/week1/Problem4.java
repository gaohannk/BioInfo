package module5.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Problem4 {
    /**
     Tree Coloring Problem: Color the internal nodes of a tree given the colors of its leaves.

     Input: An adjacency list, followed by color labels for leaf nodes.
     Input: Color labels for all nodes, in any order.
     */
    /**
     * TreeColoring(ColoredTree)
     * while ColoredTree has ripe nodes
     * for each ripe node v in ColoredTree
     * if there exist differently colored children of v
     * Color(v) ← "purple"
     * else
     * Color(v) ← color of all children of v
     * return ColoredTree
     */

    public class TreeNode {
        public List<TreeNode> childs;
        public int label;

        public TreeNode(int label) {
            this.label = label;
        }

        public boolean isLeaf() {
            for (TreeNode c : childs) {
                if (c != null) {
                    return false;
                }
            }
            return true;
        }
    }

    public static TreeNode TreeColoringProblem(TreeNode node) {

        return node;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_294_8.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        Map<Integer, String> colorMap = new HashMap<>();
        for (int i = 0; i < splits.length; i++) {
            if (splits[i].indexOf("->") == -1) {

            } else {
                colorMap.put(Integer.parseInt(splits[i].split(":")[0]), splits[i].split(":")[1]);
            }
        }
        TreeNode root = TreeColoringProblem(null);
    }
}
