package module2.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static module2.common.PrintUtils.printGraph;
import static module2.week1.PrintUtils.writeToFile;

public class Problem5 {

    /**
     * DeBruijn(Patterns)
     *     dB ← graph in which every k-mer in Patterns is isolated edge between its prefix and suffix
     *     dB ← graph resulting from ﻿gluing all nodes in dB with identical labels
     *     return dB
     * @param kmerlist
     * @return
     */
    public static Map<String, List<String>> DeBruijnGraphFromkmersProblem(List<String> kmerlist) {
        Map<String, List<String>> graph = new HashMap<>();
        int k = kmerlist.get(0).length();
        for (int i = 0; i < kmerlist.size(); i++) {
            String kmer = kmerlist.get(i);
            String prefix = kmer.substring(0, k - 1);
            String surfix = kmer.substring(1);

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

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("./resource/module2/dataset_200_8.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        List<String> kmerList = List.of(splits);
        Map<String, List<String>> graph = DeBruijnGraphFromkmersProblem(kmerList);
        String res = printGraph(graph);
        System.out.println(res);
        writeToFile(res, "./resource/module2/output1_5.txt");
    }
}
