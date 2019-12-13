package module3.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Problem4 {
    /**
	 *   OutputLCS(backtrack, v, i, j)
	 *         if i = 0 or j = 0
	 *             return ""
	 *         if backtracki, j = "↓"
	 *             ﻿return OutputLCS(backtrack, v, i - 1, j)
	 *         else if backtracki, j = "→"
	 *             return OutputLCS(backtrack, v, i, j - 1)
	 *         else
	 *             return OutputLCS(backtrack, v, i - 1, j - 1) + vi
     * Code Challenge: Use OutputLCS (reproduced below) to solve the Longest Common Subsequence Problem.
     *
     * Input: Two strings s and t.
     * Output: A longest common subsequence of s and t. (Note: more than one solution may exist, in which case you may output any one.)
     */
    public static String OutputLCS(char[][] backtrack, String v, int i, int j) {
        if (i == 0 || j == 0) {
            return "";
        }
        if (backtrack[i][j] == 'D') {
            return OutputLCS(backtrack, v, i - 1, j);
        } else if (backtrack[i][j] == 'R') {
            return OutputLCS(backtrack, v, i, j - 1);
        } else {
            return OutputLCS(backtrack, v, i - 1, j - 1) + v.charAt(i - 1);
        }
    }

	/**
	 *  LCSBackTrack(v, w)
	 *         for i ← 0 to |v|
	 *             si, 0 ← 0
	 *         for j ← 0 to |w|
	 *             s0, j ← 0
	 *         for i ← 1 to |v|
	 *             for j ← 1 to |w|
	 *                 match ← 0
	 *                 if vi-1 = wj-1
	 *                     match ← 1
	 *                 si, j ← max{si-1, j , si,j-1 , si-1, j-1 + match }
	 *                 if si,j = si-1,j
	 *                     Backtracki, j ← "↓"
	 *                 else if si, j = si, j-1
	 *                     Backtracki, j ← "→"
	 *                 else if si, j = si-1, j-1 + match
	 *                     Backtracki, j ← "↘"
	 *         return Backtrack
	 * @param v
	 * @param w
	 * @return
	 */
    public static char[][] LCSBackTrack(String v, String w) {
        int[][] s = new int[v.length() + 1][w.length() + 1];
        char[][] backtrack = new char[v.length() + 1][w.length() + 1];
        for (int i = 1; i <= v.length(); i++) {
            for (int j = 1; j <= w.length(); j++) {
                int match = v.charAt(i - 1) == w.charAt(j - 1) ? 1 : 0;

                s[i][j] = Math.max(Math.max(s[i - 1][j], s[i][j - 1]), s[i - 1][j - 1] + match);
                if (s[i][j] == s[i - 1][j]) {
                    backtrack[i][j] = 'D';
                } else if (s[i][j] == s[i][j - 1]) {
                    backtrack[i][j] = 'R';
                } else if (s[i][j] == s[i - 1][j - 1] + match) {
                    backtrack[i][j] = 'C'; // T lower right corner
                }
            }
        }
        return backtrack;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_245_5.txt"), Charset.forName("UTF-8"));
        String v = text.split("\n")[0];
        String w = text.split("\n")[1];
        System.out.println(OutputLCS(LCSBackTrack(v, w), v, v.length(), w.length()));
    }
}
