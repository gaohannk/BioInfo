package module2.week1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PrintUtils {

    public static List<String> readFromFile() throws IOException {
        String file = Files.readString(Path.of("./dataset_198_10.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        List<String> nodes = List.of(splits);
        return nodes;
    }

    public static List<String> readFromStdin() {
        List<String> nodes = new LinkedList<>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            nodes.add(scan.nextLine());
        }
        return nodes;
    }

    public static void writeToFile(String res, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(res);
        writer.flush();
        writer.close();
    }
}
