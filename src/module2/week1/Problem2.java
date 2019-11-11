package module2.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Problem2 {
    /**
     * String Spelled by a Genome Path Problem. Reconstruct a string from its genome path.
     * Input: A sequence path of k-mers Pattern1, … ,Patternn such that the last k - 1 symbols of Patterni are equal to the first k-1 symbols
     * of Patterni+1 for 1 ≤ i ≤ n-1.
     * Output: A string Text of length k+n-1 such that the i-th k-mer in Text is equal to Patterni (for 1 ≤ i ≤ n).
     *
     * @param path
     * @return
     */
    public static String ReconstructStringFromGenomePath(List<String> path) {
        StringBuilder res = new StringBuilder();
        res.append(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            res.append(path.get(i).charAt(path.get(i).length() - 1));
        }
        return res.toString();
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("./dataset_199_6.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        List<String> path = List.of(splits);
        System.out.print(ReconstructStringFromGenomePath(path));
    }
}

