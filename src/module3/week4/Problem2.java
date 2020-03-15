package module3.week4;

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
     * Number of Breakpoints Problem: Find the number of breakpoints in a permutation.
     * <p>
     * Input: A permutation.
     * Output: The number of breakpoints in this permutation.
     */
    public static int NumberOfBreakpoints(List<Integer> input) {
        int count = 0;
        int n = input.size();
        input.add(0, 0);
        input.add(n + 1);
        for (int i = 0; i < input.size() - 1; i++) {
            if (input.get(i) + 1 != input.get(i + 1)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_287_6.txt"), Charset.forName("UTF-8"));
        String[] input = text.replace("\n", "").split(" ");
        List<Integer> list = new LinkedList<>();
        for (String x : input) {
            list.add(Integer.parseInt(x));
        }
        System.out.println(NumberOfBreakpoints(list));
    }
}
