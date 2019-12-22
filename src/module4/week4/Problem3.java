package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.Constant.nucle;

public class Problem3 {

    /**
     * CODE CHALLENGE: Solve the Converting a Peptide into a Peptide Vector Problem.
     *      Given: An amino acid string P.
     *      Return: The peptide vector of P (in the form of space-separated integers).
     */
    public static List<Integer>  convertPeptideToPeptideVector(String acid) {
        List<Integer> res = new LinkedList<>();
        return res;
    }
    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_10335_10.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        int n = Integer.parseInt(splits[0]);
    }


}
