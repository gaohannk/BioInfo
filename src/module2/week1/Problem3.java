package module2.week1;

import java.io.IOException;

import java.util.*;

import static module2.week1.PrintUtils.contructToString;
import static module2.week1.PrintUtils.readFromFile;


public class Problem3 {
    public static void main(String[] args) throws IOException {
        List<String> nodes = readFromFile();
//      List<String> nodes = readFromStdin();
        Map<String, Set<String>> graph = OverlapGraphProblem(nodes);
        String res = contructToString(graph);
        System.out.println(res);
        //writeToFile(res);
    }

    /**
     * Overlap Graph Problem: Construct the overlap graph of a collection of k-mers.
     * Input: A collection Patterns of k-mers.
     * Output: The overlap graph Overlap(Patterns).
     *
     * @param
     * @throws IOException
     */
    public static Map<String, Set<String>> OverlapGraphProblem(List<String> nodes) {
        Map<String, Set<String>> adjList = new HashMap<>();
        int k = nodes.get(0).length();
        for (String node : nodes) {
            if (adjList.containsKey(node)) {
                continue;
            }
            Set<String> adjNode = new HashSet<>();
            for (String adj : nodes) {
                if (node.substring(1).equals(adj.substring(0, k - 1))) {
                    adjNode.add(adj);
                }
            }
            if (adjNode.size() > 0) {
                adjList.put(node, adjNode);
            }
        }
        return adjList;
    }
}
