package module3.week3;

import module3.common.Node;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static module3.common.Constant.AMINO;
import static module3.common.Constant.BLOSUM62;

public class Problem3 {

	public static final int SIGMA = 5;
	public static char backtrack = 'N';
	public static List<Node> path = new ArrayList<>();

	/**
	 * Code Challenge: Implement LinearSpaceAlignment to solve the Global Alignment Problem for a large dataset.
	 *
	 *     Input: Two long (10000 amino acid) protein strings written in the single-letter amino acid alphabet.
	 *     Output: The maximum alignment score of these strings, followed by an alignment achieving this maximum score. Use the BLOSUM62 scoring matrix and indel penalty σ = 5.
	 */
	/**
	 * LinearSpaceAlignment(v, w, top, bottom, left, right)
	 * if left = right
	 * output path formed by bottom − top vertical edges
	 * if top = bottom
	 * output path formed by right − left horizontal edges
	 * middle ← ⌊ (left + right)/2⌋
	 * midEdge ← MiddleEdge(v, w, top, bottom, left, right)
	 * midNode ← vertical coordinate of the initial node of midEdge
	 * LinearSpaceAlignment(v, w, top, midNode, left, middle)
	 * output midEdge
	 * if midEdge = "→" or midEdge = "↘"
	 * middle ← middle + 1
	 * if midEdge = "↓" or midEdge ="↘"
	 * midNode ← midNode + 1
	 * LinearSpaceAlignment(v, w, midNode, bottom, middle, right)
	 */
	public static void LinearSpaceAlignment(String v, String w, int top, int bottom, int left, int right) {
		if (left >= right) {
			return;
		}
		if (top >= bottom) {
			return;
		}
		Node middleEdge = MiddleEdgeInLinearSpaceProblem(v, w, top, bottom, left, right);
//		System.out.println(top + "," + bottom + "," + left + "," + right);
//		System.out.println(middleEdge.x + "," + middleEdge.y + "," + middleEdge.direct);
		path.add(middleEdge);
		int nextBottom = middleEdge.x;
		int nextRight = middleEdge.y;
		LinearSpaceAlignment(v, w, top, nextBottom, left, nextRight);
		int nextTop = middleEdge.x;
		int nextLeft = middleEdge.y;
		if (middleEdge.direct == 'D' || middleEdge.direct == 'C') {
			nextTop++;
		}
		if (middleEdge.direct == 'R' || middleEdge.direct == 'C') {
			nextLeft++;
		}
		LinearSpaceAlignment(v, w, nextTop, bottom, nextLeft, right);
	}

	public static Node MiddleEdgeInLinearSpaceProblem(String v, String w, int top, int bottom, int left, int right) {
		int maxAlignmentScore = Integer.MIN_VALUE;
		int m = (left + right) / 2;
		int score;
		Node maxScoreMiddleEdge = null;
		for (int i = top + 1; i <= bottom; i++) {
			int fromSource = GlobalAlignment(v.substring(0, i), w.substring(0, m));
			String vr = new StringBuilder(v).reverse().toString();
			String wr = new StringBuilder(w).reverse().toString();
			int toSink = GlobalAlignment(vr.substring(0, v.length() - i), wr.substring(0, w.length() - m));
			score = fromSource + toSink;
			if (maxAlignmentScore < score) {
				maxAlignmentScore = score;
				if (backtrack == 'D') {
					maxScoreMiddleEdge = new Node(i, m, 'D');
				} else if (backtrack == 'R') {
					maxScoreMiddleEdge = new Node(i, m, 'R');
				} else if (backtrack == 'C') {
					maxScoreMiddleEdge = new Node(i, m, 'C');
				}
			}
		}
		return maxScoreMiddleEdge;
	}

	public static int GlobalAlignment(String v, String w) {
		int[] prev = new int[v.length() + 1];
		int[] cur = new int[v.length() + 1];

		for (int i = 1; i <= v.length(); i++) {
			prev[i] = prev[i - 1] - SIGMA;
		}

		for (int j = 1; j <= w.length(); j++) {
			for (int i = 1; i <= v.length(); i++) {
				int match = BLOSUM62[AMINO.indexOf(v.charAt(i - 1))][AMINO.indexOf(w.charAt(j - 1))];
				cur[i] = Math.max(Math.max(cur[i - 1] - SIGMA, prev[i] - SIGMA), prev[i - 1] + match);
				if (cur[i] == cur[i - 1] - SIGMA) {
					backtrack = 'D';
				} else if (cur[i] == prev[i] - SIGMA) {
					backtrack = 'R';
				} else if (cur[i] == prev[i - 1] + match) {
					backtrack = 'C';
				}
			}
			prev = Arrays.copyOf(cur, cur.length);
		}
		return cur[v.length()];
	}

	public static String OutputLCS(String s) {
		int cur = 0;
		String res = "";
		for (int i = 0; i < path.size(); i++) {
			Node node = path.get(i);
			if (node.direct == 'D') {
				res += s.charAt(cur);
				cur++;
			} else if (node.direct == 'R') {
				res += "-";
			} else if (node.direct == 'C') {
				res += s.charAt(cur);
				cur++;
			}
		}
		return res;
	}

	public static String OutputLCS2(String s) {
		int cur = 0;
		String res = "";
		for (int i = 0; i < path.size(); i++) {
			Node node = path.get(i);
			if (node.direct == 'D') {
				res += "-";
			} else if (node.direct == 'R') {
				res += s.charAt(cur);
				cur++;
			} else if (node.direct == 'C') {
				res += s.charAt(cur);
				cur++;
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_250_12.txt"), Charset.forName("UTF-8"));
		String v = text.split("\n")[0];
		String w = text.split("\n")[1];
		LinearSpaceAlignment(v, w, 0, v.length(), 0, w.length());
		Collections.sort(path);
		System.out.println(OutputLCS(v));
		System.out.println(OutputLCS2(w));
	}
}
