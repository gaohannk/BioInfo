package module5.week4;

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
     Probability of an Outcome Given a Hidden Path Problem: Compute the probability that an HMM will emit a string given its hidden path.

     Input: A string x = x1 . . . xn emitted by an HMM (Σ, States, Transition, Emission) and a hidden path π = π1 . . . πn.
     Output: The conditional probability Pr(x|π) that x will be emitted given that the HMM follows the hidden path π.
     Code Challenge: Solve the Probability of an Outcome Given a Hidden Path Problem.

     Input: A string x, followed by the alphabet from which x was constructed, followed by a hidden path π, followed by the states States and emission matrix Emission of an HMM (Σ, States, Transition, Emission).
     Output: The conditional probability Pr(x|π) that x will be emitted given that the HMM follows the hidden path π.
     */
    public static double ProbabilityGivenHiddenPathProblem(String seq, List<List<Double>> matrix) {

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
        System.out.println(ProbabilityGivenHiddenPathProblem(seq, matrix));
    }
}
