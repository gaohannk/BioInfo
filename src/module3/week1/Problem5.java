package module3.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Problem5 {


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module3/dataset_245_5.txt"), Charset.forName("UTF-8"));
        String v = text.split("\n")[0];
        String w = text.split("\n")[1];
    }
}
