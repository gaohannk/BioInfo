package module2.week2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrintUtils {

    private static void printGraph(Map<Integer, List<Integer>> graph) {
        StringBuilder sb = new StringBuilder();
        for (int node : graph.keySet()) {
            sb.append(node + "->");
            for (int i = 0; i < graph.get(node).size(); i++) {
                sb.append(graph.get(node).get(i) + ",");
            }
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void writeToFile(List<String> list, String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i) + "->");
        }
        sb.delete(sb.length() - 2, sb.length());
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(sb.toString());
        writer.flush();
        writer.close();
    }

    public static Map<String, List<String>> readFromFile(String path) throws IOException {
        String file = Files.readString(Path.of(path), Charset.forName("UTF-8"));
        String splits[] = file.split("\n");
        Map<String, List<String>> graph = new HashMap<>();
        for (String split : splits) {
            splitLine(graph, split);
        }
        return graph;
    }

    public static Map<String, List<String>> readFromStdin() throws IOException {
        Map<String, List<String>> graph = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            splitLine(graph, line);
        }
        sc.close();
        return graph;
    }

    private static void splitLine(Map<String, List<String>> graph, String line) {
        String start = line.split(" -> ")[0];
        String nodes = line.split(" -> ")[1];
        List<String> list = List.of(nodes.split(","));
        graph.put(start, list.stream().collect(Collectors.toList()));
    }
}
