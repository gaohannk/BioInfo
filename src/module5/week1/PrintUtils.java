package module5.week1;


import module5.week1.TreeNode;

import static common.Constant.INDEX_REV;

public class PrintUtils {
	public static void printTrie(TreeNode root) {
		for(int i=0;i<root.child.length;i++){
			TreeNode child = root.child[i];
			if(child!=null){
				System.out.println(root.label + "->"+child.label +":" + INDEX_REV.get(i));
				printTrie(root.child[i]);
			}
		}
	}
}
