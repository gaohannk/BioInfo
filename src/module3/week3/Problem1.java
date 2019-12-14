package module3.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static module3.common.Constant.AMINO;
import static module3.common.Constant.BLOSUM62;
import static module3.common.PrintUtils.OutputLCS;
import static module3.common.PrintUtils.OutputLCS2;

public class Problem1 {
	public static int maxAlignmentScore = Integer.MIN_VALUE;
	public static final int GAP_OPEN_PENALTY = 11;
	public static final int GAP_EXTENSION_PENALTY = 1;
	public static char[][] backtrack;
	public static char[][] lowertrack;
	public static char[][] uppertrack;

	/**
	 * Code Challenge: Solve the Alignment with Affine Gap Penalties Problem.
	 * <p>
	 * Input: Two amino acid strings v and w (each of length at most 100).
	 * Output: The maximum alignment score between v and w, followed by an alignment of v and w achieving this maximum score. Use the BLOSUM62 scoring matrix, a gap opening penalty of 11, and a gap extension penalty of 1.
	 */
	public static void AlignmentWithAffineGapProblem(String v, String w) {
		int[][] middle = new int[v.length() + 1][w.length() + 1];
		int[][] lower = new int[v.length() + 1][w.length() + 1];
		int[][] upper = new int[v.length() + 1][w.length() + 1];
		backtrack = new char[v.length() + 1][w.length() + 1];
		lowertrack = new char[v.length() + 1][w.length() + 1];
		uppertrack = new char[v.length() + 1][w.length() + 1];

		for (int i = 1; i <= v.length(); i++) {
			if (i == 1) {
				middle[i][0] = middle[i - 1][0] - GAP_OPEN_PENALTY;
				lower[i][0] = lower[i - 1][0] - GAP_OPEN_PENALTY;
			} else {
				middle[i][0] = middle[i - 1][0] - GAP_EXTENSION_PENALTY;
				lower[i][0] = lower[i - 1][0] - GAP_EXTENSION_PENALTY;
			}
			backtrack[i][0] = 'D';

		}
		for (int j = 1; j <= w.length(); j++) {
			if (j == 1) {
				middle[0][j] = middle[0][j - 1] - GAP_OPEN_PENALTY;
				upper[0][j] = lower[0][j - 1] - GAP_OPEN_PENALTY;
			} else {
				middle[0][j] = middle[0][j - 1] - GAP_EXTENSION_PENALTY;
				upper[0][j] = lower[0][j - 1] - GAP_EXTENSION_PENALTY;
			}
			backtrack[0][j] = 'R';
		}

		for (int i = 1; i <= v.length(); i++) {
			for (int j = 1; j <= w.length(); j++) {
				lower[i][j] = Math.max(lower[i - 1][j] - GAP_EXTENSION_PENALTY, middle[i - 1][j] - GAP_OPEN_PENALTY);
				upper[i][j] = Math.max(upper[i][j - 1] - GAP_EXTENSION_PENALTY, middle[i][j - 1] - GAP_OPEN_PENALTY);
				int match = BLOSUM62[AMINO.indexOf(v.charAt(i - 1))][AMINO.indexOf(w.charAt(j - 1))];
				middle[i][j] = Math.max(Math.max(lower[i][j], upper[i][j]), middle[i - 1][j - 1] + match);

				if (lower[i][j] == lower[i - 1][j] - GAP_EXTENSION_PENALTY) {
					lowertrack[i][j] = 'E'; // Extension
				} else if (lower[i][j] == middle[i - 1][j] - GAP_OPEN_PENALTY) {
					lowertrack[i][j] = 'J'; // Jump Back to Middle
				}

				if (upper[i][j] == upper[i][j - 1] - GAP_EXTENSION_PENALTY) {
					uppertrack[i][j] = 'E'; // Extension
				} else if (upper[i][j] == middle[i][j - 1] - GAP_OPEN_PENALTY) {
					uppertrack[i][j] = 'J'; // Jump Back to Middle
				}

				if (middle[i][j] == lower[i][j]) {
					backtrack[i][j] = 'D';
				} else if (middle[i][j] == upper[i][j]) {
					backtrack[i][j] = 'R';
				} else if (middle[i][j] == middle[i - 1][j - 1] + match) {
					backtrack[i][j] = 'C'; // C lower right corner
				}
			}
		}
		maxAlignmentScore = middle[v.length()][w.length()];
	}

	/**
	 * @param s
	 * @param i
	 * @param j
	 * @param cur
	 * @param matrixId 0 middleTrack, 1 lowerTrack, 2 upperTrack
	 * @return
	 */
	public static String OutputLCS2(String s, int i, int j, int cur, int matrixId) {
		if (i == 0 && j == 0) {
			return "";
		}
		if (matrixId == 0) {
			if (backtrack[i][j] == 'D') {
				return OutputLCS2(s, i, j, cur, 1);
			} else if (backtrack[i][j] == 'R') {
				return OutputLCS2(s, i, j, cur, 2);
			} else if (backtrack[i][j] == 'C') {
				char c = s.charAt(cur--);
				return OutputLCS2(s, i - 1, j - 1, cur, 0) + c;
			}
		} else if (matrixId == 1) {
			if (lowertrack[i][j] == 'E') {
				return OutputLCS2(s, i - 1, j, cur, 1) + "-";
			} else if (lowertrack[i][j] == 'J') {
				return OutputLCS2(s, i - 1, j, cur, 0) + "-";
			}
		} else if (matrixId == 2) {
			if (uppertrack[i][j] == 'E') {
				char c = s.charAt(cur--);
				return OutputLCS2(s, i, j - 1, cur, 2) + c;
			} else if (uppertrack[i][j] == 'J') {
				char c = s.charAt(cur--);
				return OutputLCS2(s, i, j - 1, cur, 0) + c;
			}
		}
		return "";
	}

	public static String OutputLCS(String s, int i, int j, int cur, int matrixId) {
		if (i == 0 && j == 0) {
			return "";
		}
		if (matrixId == 0) {
			if (backtrack[i][j] == 'D') {
				return OutputLCS(s, i, j, cur, 1);
			} else if (backtrack[i][j] == 'R') {
				return OutputLCS(s, i, j, cur, 2);
			} else if (backtrack[i][j] == 'C') {
				char c = s.charAt(cur--);
				return OutputLCS(s, i - 1, j - 1, cur, 0) + c;
			}
		} else if (matrixId == 1) {
			if (lowertrack[i][j] == 'E') {
				char c = s.charAt(cur--);
				return OutputLCS(s, i - 1, j, cur, 1) + c;
			} else if (lowertrack[i][j] == 'J') {
				char c = s.charAt(cur--);
				return OutputLCS(s, i - 1, j, cur, 0) + c;
			}
		} else if (matrixId == 2) {
			if (uppertrack[i][j] == 'E') {
				return OutputLCS(s, i, j - 1, cur, 2) + "-";
			} else if (uppertrack[i][j] == 'J') {
				return OutputLCS(s, i, j - 1, cur, 0) + "-";
			}
		}

		return "";
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_249_8.txt"), Charset.forName("UTF-8"));
		String v = text.split("\n")[0];
		String w = text.split("\n")[1];
		AlignmentWithAffineGapProblem(v, w);
		System.out.println(maxAlignmentScore);
		System.out.println(OutputLCS(v, v.length(), w.length(), v.length() - 1, 0));
		System.out.println(OutputLCS2(w, v.length(), w.length(), w.length() - 1, 0));
	}
}
