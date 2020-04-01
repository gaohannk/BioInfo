package module5.week1;


public class SuffixTreeNode {
    public SuffixTreeNode[] children;
    public int label;
    //public List<String> edge;
    public Integer[] start;
    public Integer[] length;
    boolean isLeaf;
    public Integer startIdx;

    public SuffixTreeNode(int label, int size) {
        this.label = label;
        this.start = new Integer[size] ;
        this.length = new Integer[size] ;
        this.children = new SuffixTreeNode[size];
    }

    public SuffixTreeNode(int size) {
        this. start = new Integer[size] ;
        this.length = new Integer[size] ;
        this.children = new SuffixTreeNode[size];
    }

    public boolean isLeaf() {
        for (SuffixTreeNode c : children) {
            if (c != null) {
                return false;
            }
        }
        return true;
    }
}
