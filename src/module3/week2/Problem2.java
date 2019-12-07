package module3.week2;

import java.io.IOException;

import static module3.common.Constant.AMINO;
import static module3.common.Constant.PM250;

public class Problem2 {

    public static int maxAlignmentScore = Integer.MIN_VALUE;

    /**
     * Local Alignment Problem: Find the highest-scoring local alignment between two strings.
     *
     * Input: Strings v and w as well as a matrix score.
     * Output: Substrings of v and w whose global alignment score (as defined by score) is maximized among all substrings of v and w.
     */
    public static int LocalAlignment(String v, String w) {


        for (int i = 0; i < v.length(); i++) {
            for (int j = i+1; j < v.length(); j++) {
                for (int k = 0; k < w.length(); k++) {
                    for (int l = k+1; l < w.length(); l++) {
                        int score = GlobalAlignment(v.substring(i, j), w.substring(k, l));
                        maxAlignmentScore = maxAlignmentScore >= score?maxAlignmentScore : score;
                    }
                }
            }
        }
        return maxAlignmentScore;
    }

    public static int GlobalAlignment(String v, String w) {
        int[][] s = new int[v.length() + 1][w.length() + 1];
        char[][] backtrack = new char[v.length() + 1][w.length() + 1];

        for (int i = 1; i <= v.length(); i++) {
            s[i][0] = s[i - 1][0] -5;
            backtrack[i][0] = 'D';
        }
        for (int j = 1; j <= w.length(); j++) {
            s[0][j] = s[0][j - 1] - 5;
            backtrack[0][j] = 'R';
        }

        for (int i = 1; i <= v.length(); i++) {
            for (int j = 1; j <= w.length(); j++) {
                int match = PM250[AMINO.indexOf(v.charAt(i - 1))][AMINO.indexOf(w.charAt(j - 1))];
                s[i][j] = Math.max(Math.max(s[i - 1][j], s[i][j - 1]) - 5, s[i - 1][j - 1] + match);
                if (s[i][j] == s[i - 1][j] - 5) {
                    backtrack[i][j] = 'D';
                } else if (s[i][j] == s[i][j - 1] - 5) {
                    backtrack[i][j] = 'R';
                } else if (s[i][j] == s[i - 1][j - 1] + match) {
                    backtrack[i][j] = 'C'; // C lower right corner
                }
            }
        }
        return s[v.length()][w.length()];
    }

    public static void main(String[] args) throws IOException {
//        String text = Files.readString(Path.of("./resource/module3/dataset_247_3.txt"), Charset.forName("UTF-8"));
//        String v = text.split("\n")[0];
//        String w = text.split("\n")[1];
        String v= "MEANLY";
        String w = "PENALTY";
        int score = LocalAlignment(v, w);
        System.out.println(score);
//        System.out.println(maxAlignmentScore);
//        System.out.println(OutputLCS(backtrack, v, v.length(), w.length(), v.length()-1));
//        System.out.println(OutputLCS2(backtrack, w, v.length(), w.length(), w.length()-1));
    }
}
