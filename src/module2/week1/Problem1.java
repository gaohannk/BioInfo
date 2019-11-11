package module2.week1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.PrintUtils.writeSetToFile;

public class Problem1 {

    /**
     * String Reconstruction Problem: Reconstruct a string from its k-mer composition.
     * Input: An integer k and a string Text.
     * Output: Compositionk(Text) (the k-mers can be provided in any order).
     * @param text
     * @param k
     * @return
     */
    public static Set<String> StringCompositionProblem(String text, int k) {
        Set<String> res = new HashSet<>();
        for (int i = 0; i <= text.length() - k; i++) {
            res.add(text.substring(i, i + k));
        }
        return res;
    }


    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("./dataset_199_6.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        int k = Integer.parseInt(splits[0]);
        String text = splits[1];

        writeSetToFile(StringCompositionProblem(text, k));
    }
}
