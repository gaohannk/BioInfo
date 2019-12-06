package module3.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Problem2 {

    /**
     * ManhattanTourist(n, m, Down, Right)
     *
     * Code Challenge: Find the length of a longest path in the Manhattan Tourist Problem.
     * Input: Integers n and m, followed by an n × (m + 1) matrix Down and an (n + 1) × m matrix Right. The two matrices are separated by the "-" symbol.
     * Output: The length of a longest path from source (0, 0) to sink (n, m) in the rectangular grid whose edges are defined by the matrices Down and Right.
     */
    public static int ManhattanTouristProblem(int n, int m, List<List<Integer>> downMatrix, List<List<Integer>> rightMatrix) {
        int[][] s = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            s[i][0] = s[i - 1][0] + downMatrix.get(i - 1).get(0);
        }
        for (int j = 1; j <= m; j++) {
            s[0][j] = s[0][j - 1] + rightMatrix.get(0).get(j - 1);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                s[i][j] = Math.max(s[i - 1][j] + downMatrix.get(i - 1).get(j), s[i][j - 1] + rightMatrix.get(i).get(j - 1));
            }
        }
        return s[n][m];
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_261_10.txt"), Charset.forName("UTF-8"));
        String splits = text.split("\n")[0];
        int n = Integer.parseInt(splits.split(" ")[0]);
        int m = Integer.parseInt(splits.split(" ")[1]);
        List<List<Integer>> downMatrix = new LinkedList<>();
        List<List<Integer>> rightMatrix = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            downMatrix.add(Arrays.stream(text.split("\n")[i + 1].split(" "))
                    .map(e -> Integer.parseInt(e)).collect(Collectors.toList()));
        }
        for (int i = 0; i < n + 1; i++) {
            rightMatrix.add(Arrays.stream(text.split("\n")[i + n + 2].split(" "))
                    .map(e -> Integer.parseInt(e)).collect(Collectors.toList()));
        }
        System.out.println(ManhattanTouristProblem(n, m, downMatrix, rightMatrix));
    }
}
