package module5.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


public class Problem3 {
    /**
     * Inverse Burrows-Wheeler Transform Problem: Reconstruct a string from its Burrows-Wheeler transform.
     *
     * Input: A string Transform (with a single "\$$" symbol).
     * Output: The string Text such that BWT(Text) = Transform.
     */

    public static String InverseBurrowsWheelerTransform(String text) {
        StringBuilder res = new StringBuilder();
        char[] bwt = text.toCharArray();
        char[] first = Arrays.copyOf(bwt, bwt.length);
        Arrays.sort(first);
        char searchSymbol = '$';
        int idxInFirst = 0;
        for (int i = 0; i < text.length(); i++) {
            idxInFirst = getIndexInFirstColumn(first, searchSymbol, idxInFirst);
            idxInFirst = getLastSymbolIndex(bwt, searchSymbol, idxInFirst);
            searchSymbol = first[idxInFirst];
            res.append(searchSymbol);
        }
        return res.toString();
    }

    private static int getIndexInFirstColumn(char[] first, char searchSymbol, int index) {
        int count = 0;
        for (int i = 0; i <= index; i++) {
            if (first[i] == searchSymbol) {
                count++;
            }
        }
        return count;
    }

    private static int getLastSymbolIndex(char[] bwt, char lastSymbol, int idxInFirst) {
        int count = 0;
        for (int i = 0; i < bwt.length; i++) {
            if (bwt[i] == lastSymbol) {
                count++;
                if (count == idxInFirst) {
                    return i;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_299_10.txt"), Charset.forName("UTF-8"));
        text = text.trim();
        System.out.println(InverseBurrowsWheelerTransform("G$CAGCTAGGG"));
    }
}
