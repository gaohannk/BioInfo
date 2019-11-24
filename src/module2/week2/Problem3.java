package module2.week2;

import java.util.*;

import static common.ReadUtils.readKmerList;
import static module2.week1.Problem5.DeBruijnGraphFromkmersProblem;
import static module2.week2.Problem2.*;

public class Problem3 {

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
        return PathToGenome(path);
    }

    public static String PathToGenome(List<String> path) {
        StringBuilder genme = new StringBuilder();
        genme.append(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            String kmer = path.get(i);
            genme.append(kmer.charAt(kmer.length() - 1));
        }
        return genme.toString();
    }

    public static void main(String[] args) throws Exception {
        List<String> kmerList = readKmerList("./resource/module2/dataset_203_7.txt");
        String text = StringReconstruction(kmerList);
        System.out.println(text);
    }

}
