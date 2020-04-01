package module5.week1;

public class TreeNode {
    public TreeNode[] child = new TreeNode[4];
    public int label;

    public TreeNode(int label) {
        this.label = label;
    }

    public boolean isLeaf() {
        for (TreeNode c : child) {
            if (c != null) {
                return false;
            }
        }
        return true;
    }
}
