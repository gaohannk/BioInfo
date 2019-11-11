package module2.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static common.PrintUtils.printListByLine;
import static module2.week1.Problem5.DeBruijnGraphFromkmersProblem;
import static module2.week2.Problem2.*;

public class Problem3 {
    public static int k;

    /**
     * The de Bruijn Graph Construction Problem;
     * The Eulerian Path Problem;
     * The String Spelled by a Genome Path Problem.
     * <p>
     * StringReconstruction(Patterns)
     * dB ← DeBruijn(Patterns)
     * path ← EulerianPath(dB)
     * Text﻿ ← PathToGenome(path)
     * return Text
     *
     * @param kmerlist
     * @return
     */
    public static String StringReconstruction(List<String> kmerlist) {
        Map<String, List<String>> graph = DeBruijnGraphFromkmersProblem(kmerlist);
        List<String> path = EulerianPath(graph);
        printListByLine(path);
        return PathToGenome(path);
    }

    private static String PathToGenome(List<String> path) {
        StringBuilder genme = new StringBuilder();
        genme.append(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            String kmer = path.get(i);
            genme.append(kmer.charAt(kmer.length() - 1));
        }
        return genme.toString();
    }

    public static void main(String[] args) throws Exception {
        List<String> kmerList = readFromFile("./resource/module2/dataset_203_7.txt");
        String res = StringReconstruction(kmerList);
        System.out.println(res);
    }

    private static List<String> readFromFile(String path) throws IOException {
        String file = Files.readString(Path.of(path), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        k = Integer.parseInt(splits[0]);
        List<String> patterns = new LinkedList<>();
        for (int i = 1; i < splits.length; i++) {
            patterns.add(splits[i]);
        }
        return patterns;
    }

}
