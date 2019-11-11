package module2.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static module2.week1.PrintUtils.constructToString;
import static module2.week1.PrintUtils.writeToFile;

public class Problem4 {
    /**
     * De Bruijn Graph from a String Problem: Construct the de Bruijn graph of a string.
     *      Input: An integer k and a string Text.
     *      Output: DeBruijnk(Text).
     * @param k
     * @param text
     * @return
     */
    public static Map<String, List<String>> DeBruijnGraphFromStringProblem(int k, String text) {
        Map<String, List<String>> graph = new HashMap<>();

        for (int i = 0; i <= text.length() - k; i++) {
            String kmer = text.substring(i, i + k);
            String prefix = kmer.substring(0, k - 1);
            String surfix = kmer.substring(1, k);

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
        String file = Files.readString(Path.of("./resource/module2/dataset_199_6.txt"), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        int k = Integer.parseInt(splits[0]);
        String text = splits[1];

        Map<String, List<String>> graph = DeBruijnGraphFromStringProblem(k, text);
        String res = constructToString(graph);
        System.out.println(res);
        writeToFile(res, "./resource/module2/output1_4.txt");
    }
}
