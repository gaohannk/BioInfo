package module3.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;
import static module3.common.Constant.AMINO;
import static module3.common.Constant.BLOSUM62;

public class Problem1 {

	public static int approxReversalDistance = 0;

	/**
	 * Code Challenge: Implement GreedySorting.
	 *
	 *     Input: A permutation P.
	 *     Output: The sequence of permutations corresponding to applying GreedySorting to P, ending with the identity permutation.
	 */
	/** GreedySorting(P)
	 *         approxReversalDistance ← 0
	 *         for k = 1 to |P|
	 *             if element k is not sorted
	 *                 apply the k-sorting reversal to P
	 *                 approxReversalDistance ← approxReversalDistance + 1
	 *             if k-th element of P is −k
	 *                 apply the k-sorting reversal to P
	 *                 approxReversalDistance ← approxReversalDistance + 1
	 *         return approxReversalDistance
	 */
	public static List<String> GreedySorting(List<String> input) {

		List<String> res = new LinkedList<>();
		for(int i=1;i<= input.size();i++){
			if(Math.abs(Integer.parseInt(input.get(i-1))) != i){
				int pos;
				if(input.indexOf("+"+ i)!= -1){
					pos = input.indexOf("+" + i);
				} else{
					pos = input.indexOf("-"+ i);
				}
				List<String> sublist = input.subList(i-1, pos+1);
				List<String> reverseList = new LinkedList<>();
				for(int j = sublist.size()-1;j>=0;j--){
					String cur = sublist.get(j);
					String element = cur.charAt(0) == '+' ? "-" + cur.substring(1) :  "+" + cur.substring(1);
					reverseList.add(element);
				}input.removeAll(sublist);
				input.addAll(i-1, reverseList);
				res.add(String.join(" ", input));
				approxReversalDistance++;
			}
			if(Integer.parseInt(input.get(i-1)) == -i){
				input.remove(i-1);
				input.add(i-1, "+"+ i);
				res.add(String.join(" ", input));
				approxReversalDistance++;
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_286_4.txt"), Charset.forName("UTF-8"));
		String[] input = text.replace("\n", "").split(" ");
		//GreedySorting(Arrays.stream(input).collect(Collectors.toList()));
		printListByLine(GreedySorting(Arrays.stream(input).collect(Collectors.toList())));
	}
}
