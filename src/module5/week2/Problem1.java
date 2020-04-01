package module5.week2;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;



public class Problem1 {
    /**
     * Suffix Array Construction Problem: Construct the suffix array of a string.
     *
     * Input: A string Text.
     * Output: SuffixArray(Text).
     */
    public static SuffixArray[] SuffixArrayConstruction(String text) {
        SuffixArray[] res = new SuffixArray[text.length()];
        for (int i = 0; i < text.length(); i++) {
            res[i] = new SuffixArray(text.substring(i), i);
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module5/dataset_310_2.txt"), Charset.forName("UTF-8"));
        text = text.trim();
        SuffixArray[] res = SuffixArrayConstruction(text);
        Arrays.sort(res, new Comparator<SuffixArray>() {
            @Override
            public int compare(SuffixArray o1, SuffixArray o2) {
                return o1.text.compareTo(o2.text);
            }
        });

        for (int i = 0; i < text.length(); i++) {
            System.out.print(res[i].start + ", ");
        }
    }

    private static class SuffixArray {
        String text;
        int start;

        public SuffixArray(String text, int start) {
            this.text = text;
            this.start = start;
        }
    }
}
