package module5.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Problem1 {
    /**
     * Probability of a Hidden Path Problem: Compute the probability of a hidden path.
     *
     * Input: A hidden path π in an HMM (Σ, States, Transition, Emission).
     * Output: The probability of this path, Pr(π).
     * Code Challenge: Solve the Probability of a Hidden Path Problem.
     *
     * Input: A hidden path π followed by the states States and transition matrix Transition of an HMM (Σ, States, Transition, Emission).
     * Output: The probability of this path, Pr(π).
     * Note: You may assume that transitions from the initial state occur with equal probability.
     */
    public static double ProbabilityOfaHiddenPath(String seq, List<List<Double>> matrix) {

    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_26255_8.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        String seq = splits[0];
        int numState = splits[2].split(" ").length;
        List<List<Double>> matrix = new LinkedList<>();
        for (int i = 5; i < splits.length; i++) {
            List<Double> line = Arrays.stream(splits[i].split(" ")).map(x -> Double.parseDouble(x)).collect(Collectors.toList());
            matrix.add(line);
        }
        System.out.println(ProbabilityOfaHiddenPath(seq, matrix));
    }
}
