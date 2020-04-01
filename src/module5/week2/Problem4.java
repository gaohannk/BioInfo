package module5.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;


public class Problem4 {
    /**
     * Burrows-Wheeler Transform Construction Problem: Construct the Burrows-Wheeler transform of a string.
     *
     * Input: A string Text.
     * Output: BWT(Text).
     */

    public static String BurrowsWheelerTransformConstruction(String genmoText) {

        return "";
    }


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_294_8.txt"), Charset.forName("UTF-8"));
        text = text.trim();
        System.out.println(BurrowsWheelerTransformConstruction(text));
    }
}
