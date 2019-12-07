package module3.common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;


public class Constant {

    public static int[][] BLOSUM62 = new int[20][20];
    public static int[][] PM250 = new int[20][20];

    public static String AMINO = "ACDEFGHIKLMNPQRSTVWY";


    static {
        String text = null;

        try {
            text = Files.readString(Path.of("./resource/common/BLOSUM62.txt"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++) {
            String[] splits = text.split("\n")[i + 1].split("\\s+");
            for (int j = 0; j < 20; j++) {
                BLOSUM62[i][j] = Integer.parseInt(splits[j+1]);
            }
        }

        try {
            text = Files.readString(Path.of("./resource/common/PAM250.txt"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++) {
            String[] splits = text.split("\n")[i + 1].split("\\s+");
            for (int j = 0; j < 20; j++) {
                PM250[i][j] = Integer.parseInt(splits[j+1]);
            }
        }
    }
}
