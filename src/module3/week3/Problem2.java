package module3.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static module3.common.Constant.AMINO;
import static module3.common.Constant.BLOSUM62;

public class Problem2 {

	public static int maxAlignmentScore;
	public static final int SIGMA = 5;
	public static char backtrack = 'N';
	public static String maxScoreMiddleEdge;

	/**
	 * Code Challenge: Solve the Middle Edge in Linear Space Problem (for protein strings).
	 * <p>
	 * Input: Two amino acid strings.
	 * Output: A middle edge in the alignment graph in the form "(i, j) (k, l)", where (i, j) connects to (k, l). To compute scores, use the BLOSUM62 scoring matrix and a (linear) indel penalty equal to 5.
	 */
	public static String MiddleEdgeInLinearSpaceProblem(String v, String w) {
		int m = w.length() / 2;
		int[] score = new int[v.length() + 1];
		for (int i = 1; i <= v.length(); i++) {
			int fromSource = GlobalAlignment(v.substring(0, i), w.substring(0, m));
			String vr = new StringBuilder(v).reverse().toString();
			String wr = new StringBuilder(w).reverse().toString();

			int toSink = GlobalAlignment(vr.substring(0, v.length() - i), wr.substring(0, w.length() - m));
			score[i] = fromSource + toSink;
			if (maxAlignmentScore < score[i]) {
				maxAlignmentScore = score[i];
				if (backtrack == 'D') {
					maxScoreMiddleEdge = new StringBuilder().append("(" + i + ", " + m + ") ").append("(" + (i + 1) + ", " + m + ")").toString();
				} else if (backtrack == 'R') {
					maxScoreMiddleEdge = new StringBuilder().append("(" + i + ", " + m + ") ").append("(" + i + ", " + (m + 1) + ")").toString();
				} else if (backtrack == 'C') {
					maxScoreMiddleEdge = new StringBuilder().append("(" + i + ", " + m + ") ").append("(" + (i + 1) + ", " + (m + 1) + ")").toString();
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

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_250_12.txt"), Charset.forName("UTF-8"));
		String v = text.split("\n")[0];
		String w = text.split("\n")[1];
		String edge = MiddleEdgeInLinearSpaceProblem(v, w);
		System.out.println(edge);
	}
}
