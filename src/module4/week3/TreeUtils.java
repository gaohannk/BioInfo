package module4.week3;

import static module4.week3.Node.getHammingDistance;

public class TreeUtils {
    private static void printTree(Node root) {
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
        printTree(root.left);
        printTree(root.right);
    }

    private static String getEdge(Node src, Node dest) {
        StringBuilder sb = new StringBuilder();
        sb.append(src.id);
        sb.append("->");
        sb.append(dest.id);
        return sb.toString();
    }

    static void printTree(Node[] root) {
        printTree(root[0]);
        printTree(root[1]);
        System.out.println(getEdge(root[0], root[1]));
        System.out.println(getEdge(root[1], root[0]));
        System.out.println();
    }

    public static void printEdgeInTree(Node root) {
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

    public static void printEdgeInTree(Node[] root) {
        printEdgeInTree(root[0]);
        printEdgeInTree(root[1]);
        System.out.println(getEdge(root[0].character, root[1].character));
        System.out.println(getEdge(root[1].character, root[0].character));
    }

    public static String getEdge(char[] src, char[] dest) {
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
}
