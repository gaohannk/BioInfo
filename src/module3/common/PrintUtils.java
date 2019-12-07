package module3.common;

import module2.week2.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;

public class PrintUtils {
    public static <T extends Collection<E>, E> String printGraph(Map<String, T> graph) {
        StringBuilder sb = new StringBuilder();
        for (String key : graph.keySet()) {
            sb.append(key + " -> ");
            for (E adjNode : graph.get(key)) {
                sb.append(adjNode.toString() + ",");
            }
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static void printPairReads(List<Pair<String>> pairReads) {
        printListInOneline(pairReads.stream().map(pair -> pair.read1 + "|" + pair.read2).collect(Collectors.toList()));
    }

    public static  void printMatrix(char[][] matrix, String delimeter) {
        for (char[] line : matrix) {
            for (char e : line) {
                System.out.print(e + delimeter);
            }
            System.out.println();
        }
    }

    public static void printMatrix(int[][] matrix, String delimeter) {
        for (int[] line : matrix) {
            for (int e : line) {
                System.out.print(e + delimeter);
            }
            System.out.println();
        }
    }

    public static String OutputLCS2(char[][] backtrack, String s, int i, int j, int cur) {
        if (i == 0 && j == 0) {
            return "";
        }
        if (backtrack[i][j] == 'D') {
            return OutputLCS2(backtrack, s, i - 1, j, cur) + "-";
        } else if (backtrack[i][j] == 'R') {
            char c = s.charAt(cur--);
            return OutputLCS2(backtrack, s, i, j - 1, cur) + c;
        } else {
            char c = s.charAt(cur--);
            return OutputLCS2(backtrack, s, i - 1, j - 1, cur) + c;
        }
    }

    public static String OutputLCS(char[][] backtrack, String s, int i, int j, int cur) {
        if (i == 0 && j == 0) {
            return "";
        }
        if (backtrack[i][j] == 'D') {
            char c = s.charAt(cur--);
            return OutputLCS(backtrack, s, i - 1, j, cur) + c;
        } else if (backtrack[i][j] == 'R') {
            return OutputLCS(backtrack, s, i, j - 1, cur) + "-";
        } else {
            char c = s.charAt(cur--);
            return OutputLCS(backtrack, s, i - 1, j - 1, cur) + c;
        }
    }
}
