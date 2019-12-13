package module3.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static module3.common.PrintUtils.OutputLCS;
import static module3.common.PrintUtils.OutputLCS2;

public class Problem5 {

    public static int maxAlignmentScore = Integer.MIN_VALUE;
    public static final int SIGMA = 2;
    public static String alignmentV;
    public static String alignmentW;

    /**
     * Overlap Alignment Problem: Construct a highest-scoring overlap alignment between two strings.
     * Input: Two strings and a matrix score.
     * Output: A highest-scoring overlap alignment between the two strings as defined by the scoring matrix score.
     */
    public static int OverlapAlignment(String v, String w) {
        int maxScore = Integer.MIN_VALUE;
        for (int i = 0; i < v.length(); i++) {
            for (int j = 0; j < w.length(); j++) {
                String vPrime = v.substring(i);
                String wPrime = w.substring(0, j);
                char[][] backtrack = GlobalAlignment(vPrime, wPrime);
                if (maxAlignmentScore > maxScore) {
                    maxScore = maxAlignmentScore;
                    alignmentV = OutputLCS(backtrack, vPrime, vPrime.length(), wPrime.length(), vPrime.length() - 1);
                    alignmentW = OutputLCS2(backtrack, wPrime, vPrime.length(), wPrime.length(), wPrime.length() - 1);
                }
            }
        }

        return maxScore;
    }

    public static char[][] GlobalAlignment(String v, String w) {
        int[][] s = new int[v.length() + 1][w.length() + 1];
        char[][] backtrack = new char[v.length() + 1][w.length() + 1];

        for (int i = 1; i <= v.length(); i++) {
            s[i][0] = s[i - 1][0] - SIGMA;
            backtrack[i][0] = 'D';
        }
        for (int j = 1; j <= w.length(); j++) {
            s[0][j] = s[0][j - 1] - SIGMA;
            backtrack[0][j] = 'R';
        }

        for (int i = 1; i <= v.length(); i++) {
            for (int j = 1; j <= w.length(); j++) {
                int match = v.charAt(i - 1) == w.charAt(j - 1) ? 1 : -SIGMA;
                s[i][j] = Math.max(Math.max(s[i - 1][j], s[i][j - 1]) - SIGMA, s[i - 1][j - 1] + match);
                if (s[i][j] == s[i - 1][j] - SIGMA) {
                    backtrack[i][j] = 'D';
                } else if (s[i][j] == s[i][j - 1] - SIGMA) {
                    backtrack[i][j] = 'R';
                } else if (s[i][j] == s[i - 1][j - 1] + match) {
                    backtrack[i][j] = 'C'; // C lower right corner
                }
            }
        }
        maxAlignmentScore = s[v.length()][w.length()];
        return backtrack;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_248_7.txt"), Charset.forName("UTF-8"));
        String v = text.split("\n")[0];
        String w = text.split("\n")[1];
        System.out.println(OverlapAlignment(v, w));
        System.out.println(alignmentV);
        System.out.println(alignmentW);
    }
}
