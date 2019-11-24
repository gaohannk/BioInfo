package module2.week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;

import static module2.common.PrintUtils.printPairReads;
import static module2.week2.Problem2.EulerianPath;
import static module2.week2.Problem6.StringSpelledByGappedPatterns;

public class Problem7 {
    /**
     * Code Challenge: Solve the String Reconstruction from Read-Pairs Problem.
     * Input: Integers k and d followed by a collection of paired k-mers PairedReads.
     * Output: A string Text with (k, d)-mer composition equal to PairedReads.
     *
     * @throws IOException
     */

    public static String StringReconstructionFromReadPairsProblem(int k, int d, List<Pair<String>> pairReads) throws Exception {
        Map<String, List<String>> graph = DeBruijnGraphFromKDmersProblem(pairReads, k);
        //System.out.println("Graph is : ");
        //System.out.println(printGraph2(graph));
        List<String> path = EulerianPath(graph);
        pairReads = constructPairReads(path);
        return StringSpelledByGappedPatterns(pairReads, k, d);
    }

    public static List<Pair<String>> constructPairReads(List<String> path) {
        List<Pair<String>> pairReads = new LinkedList<>();
        for (int i = 0; i < path.size(); i++) {
            String read1 = path.get(i).split("\\|")[0];
            String read2 = path.get(i).split("\\|")[1];
            pairReads.add(new Pair<>(read1, read2));
        }
        return pairReads;
    }

    /**
     * DeBruijn(Patterns)
     * dB ← graph in which every k-mer in Patterns is isolated edge between its prefix and suffix
     * dB ← graph resulting from ﻿gluing all nodes in dB with identical labels
     * return dB
     *
     * @param kmerlist
     * @return
     */
    public static Map<String, List<String>> DeBruijnGraphFromKDmersProblem(List<Pair<String>> kmerlist, int k) {
        Map<String, List<String>> graph = new HashMap<>();
        for (int i = 0; i < kmerlist.size(); i++) {
            String kmerRead1 = kmerlist.get(i).read1;
            String kmerRead2 = kmerlist.get(i).read2;
            String prefixRead1 = kmerRead1.substring(0, k - 1);
            String prefixRead2 = kmerRead2.substring(0, k - 1);
            String surfixRead1 = kmerRead1.substring(1);
            String surfixRead2 = kmerRead2.substring(1);
            String prefix = prefixRead1 + "|" + prefixRead2;
            String surfix = surfixRead1 + "|" + surfixRead2;

            if (graph.containsKey(prefix)) {
                graph.get(prefix).add(surfix);
            } else {
                List<String> adj = new LinkedList<>();
                adj.add(surfix);
                graph.put(prefix, adj);
            }
        }
        return graph;
    }

    public static void main(String[] args) throws Exception {
        String file = Files.readString(Path.of("./resource/module2/dataset_204_16.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        int k = Integer.parseInt(splits[0].split(" ")[0]);
        int d = Integer.parseInt(splits[0].split(" ")[1]);
        List<String> kmer =  Arrays.stream(Arrays.copyOfRange(splits, 1,splits.length)).collect(Collectors.toList());
        //printListInOneline(kmer);
        List<Pair<String>> pairReads = constructPairReads(kmer);
        printPairReads(pairReads);
        System.out.println(StringReconstructionFromReadPairsProblem(k, d, pairReads));
    }

}
