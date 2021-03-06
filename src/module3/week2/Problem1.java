package module3.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static module3.common.Constant.AMINO;
import static module3.common.Constant.BLOSUM62;
import static module3.common.PrintUtils.OutputLCS;
import static module3.common.PrintUtils.OutputLCS2;

public class Problem1 {

    public static int maxAlignmentScore;
    public static final int SIGMA = 5;

    /**
     * Code Challenge: Solve the Global Alignment Problem.
     *
     * Input: Two protein strings written in the single-letter amino acid alphabet.
     * Output: The maximum alignment score of these strings followed by an alignment achieving this maximum score. Use the BLOSUM62 scoring matrix for matches and mismatches as well as the indel penalty σ = 5.
     */
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
                int match = BLOSUM62[AMINO.indexOf(v.charAt(i - 1))][AMINO.indexOf(w.charAt(j - 1))];
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
        String text = Files.readString(Path.of("./resource/module3/dataset_247_3.txt"), Charset.forName("UTF-8"));
        String v = text.split("\n")[0];
        String w = text.split("\n")[1];
        char[][] backtrack = GlobalAlignment(v, w);
        System.out.println(maxAlignmentScore);
        System.out.println(OutputLCS(backtrack, v, v.length(), w.length(), v.length() - 1));
        System.out.println(OutputLCS2(backtrack, w, v.length(), w.length(), w.length() - 1));
    }
}
