package module3.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Problem4 {

    public static int maxAlignmentScore = Integer.MIN_VALUE;
    public static String[][][] backtrack;


    /**
     * Multiple Alignment Problem: Find the highest-scoring alignment between multiple strings under a given scoring matrix.
     *
     * Input: A collection of t strings and a t-dimensional matrix Score.
     * Output: A multiple alignment of these strings whose score (as defined by the matrix Score) is maximized among all possible alignments of these strings.
     */
    public static void MultipleAlignment(String v, String w, String x) {
        int[][][] s = new int[v.length() + 1][w.length() + 1][x.length() + 1];
        backtrack = new String[v.length() + 1][w.length() + 1][x.length() + 1];


        for (int i = 1; i <= v.length(); i++) {
            backtrack[i][0][0] = "Down";
        }
        for (int j = 1; j <= w.length(); j++) {
            backtrack[0][j][0] = "Right";
        }
        for (int k = 1; k <= x.length(); k++) {
            backtrack[0][0][k] = "Deep";
        }
        for (int i = 1; i <= v.length(); i++) {
            for (int j = 1; j <= w.length(); j++) {
                backtrack[i][j][0] = "DownRight";
            }
        }
        for (int j = 1; j <= w.length(); j++) {
            for (int k = 1; k <= x.length(); k++) {
                backtrack[0][j][k] = "RightDeep";
            }
        }
        for (int i = 1; i <= v.length(); i++) {
            for (int k = 1; k <= x.length(); k++) {
                backtrack[i][0][k] = "DownDeep";
            }
        }


        for (int i = 1; i <= v.length(); i++) {
            for (int j = 1; j <= w.length(); j++) {
                for (int k = 1; k <= x.length(); k++) {
                    int match = 0;
                    if (v.charAt(i - 1) == w.charAt(j - 1) && w.charAt(j - 1) == x.charAt(k - 1)) {
                        match = 1;
                    }
                    s[i][j][k] = Collections.max(List.of(s[i - 1][j][k], s[i][j - 1][k], s[i][j][k - 1], s[i - 1][j - 1][k], s[i - 1][j][k - 1], s[i][j - 1][k - 1], s[i - 1][j - 1][k - 1] + match));
                    if (s[i][j][k] == s[i - 1][j][k]) {
                        backtrack[i][j][k] = "Down";
                    } else if (s[i][j][k] == s[i][j - 1][k]) {
                        backtrack[i][j][k] = "Right";
                    } else if (s[i][j][k] == s[i][j][k - 1]) {
                        backtrack[i][j][k] = "Deep";
                    } else if (s[i][j][k] == s[i - 1][j - 1][k]) {
                        backtrack[i][j][k] = "DownRight";
                    } else if (s[i][j][k] == s[i][j - 1][k - 1]) {
                        backtrack[i][j][k] = "RightDeep";
                    } else if (s[i][j][k] == s[i - 1][j][k - 1]) {
                        backtrack[i][j][k] = "DownDeep";
                    } else if (s[i][j][k] == s[i - 1][j - 1][k - 1] + match) {
                        backtrack[i][j][k] = "Corner";
                    }
                }
            }
        }
        maxAlignmentScore = s[v.length()][w.length()][x.length()];
        return;
    }

    public static String OutputLCS(String[][][] backtrack, String s, int i, int j, int k, int cur) {
        if (i == 0 && j == 0 && k == 0) {
            return "";
        }
        if (backtrack[i][j][k] == "Down") {
            char c = s.charAt(cur--);
            return OutputLCS(backtrack, s, i - 1, j, k, cur) + c;
        } else if (backtrack[i][j][k] == "Right") {
            return OutputLCS(backtrack, s, i, j - 1, k, cur) + "-";
        } else if (backtrack[i][j][k] == "Deep") {
            return OutputLCS(backtrack, s, i, j, k - 1, cur) + "-";
        } else if (backtrack[i][j][k] == "DownRight") {
            char c = s.charAt(cur--);
            return OutputLCS(backtrack, s, i - 1, j - 1, k, cur) + c;
        } else if (backtrack[i][j][k] == "RightDeep") {
            return OutputLCS(backtrack, s, i, j - 1, k - 1, cur) + "-";
        } else if (backtrack[i][j][k] == "DownDeep") {
            char c = s.charAt(cur--);
            return OutputLCS(backtrack, s, i - 1, j, k - 1, cur) + c;
        } else if (backtrack[i][j][k] == "Corner") {
            char c = s.charAt(cur--);
            return OutputLCS(backtrack, s, i - 1, j - 1, k - 1, cur) + c;
        }
        return "";
    }

    public static String OutputLCS2(String[][][] backtrack, String s, int i, int j, int k, int cur) {
        if (i == 0 && j == 0 && k == 0) {
            return "";
        }
        if (backtrack[i][j][k] == "Down") {
            return OutputLCS2(backtrack, s, i - 1, j, k, cur) + "-";
        } else if (backtrack[i][j][k] == "Right") {
            char c = s.charAt(cur--);
            return OutputLCS2(backtrack, s, i, j - 1, k, cur) + c;
        } else if (backtrack[i][j][k] == "Deep") {
            return OutputLCS2(backtrack, s, i, j, k - 1, cur) + "-";
        } else if (backtrack[i][j][k] == "DownRight") {
            char c = s.charAt(cur--);
            return OutputLCS2(backtrack, s, i - 1, j - 1, k, cur) + c;
        } else if (backtrack[i][j][k] == "RightDeep") {
            char c = s.charAt(cur--);
            return OutputLCS2(backtrack, s, i, j - 1, k - 1, cur) + c;
        } else if (backtrack[i][j][k] == "DownDeep") {
            return OutputLCS2(backtrack, s, i - 1, j, k - 1, cur) + "-";
        } else if (backtrack[i][j][k] == "Corner") {
            char c = s.charAt(cur--);
            return OutputLCS2(backtrack, s, i - 1, j - 1, k - 1, cur) + c;
        }
        return "";
    }

    public static String OutputLCS3(String[][][] backtrack, String s, int i, int j, int k, int cur) {
        if (i == 0 && j == 0 && k == 0) {
            return "";
        }
        if (backtrack[i][j][k] == "Down") {
            return OutputLCS3(backtrack, s, i - 1, j, k, cur) + "-";
        } else if (backtrack[i][j][k] == "Right") {
            return OutputLCS3(backtrack, s, i, j - 1, k, cur) + "-";
        } else if (backtrack[i][j][k] == "Deep") {
            char c = s.charAt(cur--);
            return OutputLCS3(backtrack, s, i, j, k - 1, cur) + c;
        } else if (backtrack[i][j][k] == "DownRight") {
            return OutputLCS3(backtrack, s, i - 1, j - 1, k, cur) + "-";
        } else if (backtrack[i][j][k] == "RightDeep") {
            char c = s.charAt(cur--);
            return OutputLCS3(backtrack, s, i, j - 1, k - 1, cur) + c;
        } else if (backtrack[i][j][k] == "DownDeep") {
            char c = s.charAt(cur--);
            return OutputLCS3(backtrack, s, i - 1, j, k - 1, cur) + c;
        } else if (backtrack[i][j][k] == "Corner") {
            char c = s.charAt(cur--);
            return OutputLCS3(backtrack, s, i - 1, j - 1, k - 1, cur) + c;
        }
        return "";
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_251_5.txt"), Charset.forName("UTF-8"));
        String v = text.split("\n")[0];
        String w = text.split("\n")[1];
        String x = text.split("\n")[2];

        MultipleAlignment(v, w, x);
        System.out.println(maxAlignmentScore);
        System.out.println(OutputLCS(backtrack, v, v.length(), w.length(), x.length(), v.length() - 1));
        System.out.println(OutputLCS2(backtrack, w, v.length(), w.length(), x.length(), w.length() - 1));
        System.out.println(OutputLCS3(backtrack, x, v.length(), w.length(), x.length(), x.length() - 1));

    }
}
