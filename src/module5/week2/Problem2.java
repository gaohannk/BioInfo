package module5.week2;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class Problem2 {
    /**
     * Burrows-Wheeler Transform Construction Problem: Construct the Burrows-Wheeler transform of a string.
     *
     * Input: A string Text.
     * Output: BWT(Text).
     */

    public static String BurrowsWheelerTransformConstruction(String text) {
        List<String> matrix = new LinkedList<>();
        for (int i = 0; i < text.length(); i++) {
            String rotate = text.substring(text.length()-i) + text.substring(0, text.length()-i);
            matrix.add(rotate);
        }
        Collections.sort(matrix);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            res.append(matrix.get(i).charAt(text.length()-1));
        }
        return res.toString();
    }


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_297_5.txt"), Charset.forName("UTF-8"));
        text = text.trim();
        System.out.println(BurrowsWheelerTransformConstruction(text));
    }
}
