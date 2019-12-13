package module3.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static module3.common.Constant.AMINO;
import static module3.common.Constant.PM250;
import static module3.common.PrintUtils.*;

public class Problem2 {

    public static int maxAlignmentScore = Integer.MIN_VALUE;
    public static final int SIGMA = 5;
    public static int maxI = 0, maxJ = 0;

    /**
     * Local Alignment Problem: Find the highest-scoring local alignment between two strings.
     *
     * Input: Strings v and w as well as a matrix score.
     * Output: Substrings of v and w whose global alignment score (as defined by score) is maximized among all substrings of v and w.
     */
    public static char[][] LocalAlignment(String v, String w) {
        int[][] s = new int[v.length() + 1][w.length() + 1];
        char[][] backtrack = new char[v.length() + 1][w.length() + 1];

        for (int i = 1; i <= v.length(); i++) {
            s[i][0] = Math.max(s[i - 1][0] - SIGMA, 0);
            if (s[i][0] == 0) {
                backtrack[i][0] = ' ';
            }
            if (s[i][0] == s[i - 1][0]) {
                backtrack[i][0] = 'D';
            }

        }
        for (int j = 1; j <= w.length(); j++) {
            s[0][j] = Math.max(s[0][j - 1] - SIGMA, 0);
            if (s[0][j] == 0) {
                backtrack[0][j] = ' ';
            }
            if (s[0][j] == s[0][j - 1]) {
                backtrack[0][j] = 'R';
            }

        }

        for (int i = 1; i <= v.length(); i++) {
            for (int j = 1; j <= w.length(); j++) {
                int match = PM250[AMINO.indexOf(v.charAt(i - 1))][AMINO.indexOf(w.charAt(j - 1))];
                s[i][j] = Math.max(Math.max(Math.max(s[i - 1][j], s[i][j - 1]) - SIGMA, s[i - 1][j - 1] + match), 0);
                if (maxAlignmentScore < s[i][j]) {
                    maxI = i;
                    maxJ = j;
                    maxAlignmentScore = s[i][j];
                }
                if (s[i][j] == 0) {
                    backtrack[i][j] = ' '; // SPACE stop
                } else if (s[i][j] == s[i][j - 1] - SIGMA) {
                    backtrack[i][j] = 'R';
                } else if (s[i][j] == s[i - 1][j] - SIGMA) {
                    backtrack[i][j] = 'D';
                } else if (s[i][j] == s[i - 1][j - 1] + match) {
                    backtrack[i][j] = 'C'; // 'C' lower right corner
                }
            }
        }

        return backtrack;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_247_10.txt"), Charset.forName("UTF-8"));
        String v = text.split("\n")[0];
        String w = text.split("\n")[1];
        char[][] backtrack = LocalAlignment(v, w);
        printMatrix(backtrack, ", ");
        System.out.println(maxAlignmentScore);
//        System.out.println(maxI);
//        System.out.println(maxJ);

        System.out.println(OutputLCS(backtrack, v, maxI, maxJ, maxI - 1));
        System.out.println(OutputLCS2(backtrack, w, maxI, maxJ, maxJ - 1));
    }
}
