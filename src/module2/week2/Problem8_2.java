package module2.week2;

import java.io.IOException;
import java.util.*;

import static common.PrintUtils.printPath;
import static module2.week2.PrintUtils.readFromFile;

public class Problem8_2 {
    /**
     * Contig Generation Problem: Generate the contigs from a collection of reads (with imperfect coverage).
     * Input: A collection of k-mers Patterns.
     * Output: All contigs in DeBruijn(Patterns).
     * @throws IOException
     */
    public static List<List<String>> MaximalNonBranchingPaths(Map<String, List<String>> graph){
        Map<String, Integer> outdegreeMap = new HashMap<>();
        Map<String, Integer> indegreeMap = new HashMap<>();

        contructDegreeMap(graph, outdegreeMap, indegreeMap);
        Set<String> visited = new HashSet<>();
        List<List<String>> res = new LinkedList<>();
        for(String node: graph.keySet()){
            if(!isOneInOneOutNode(node, outdegreeMap, indegreeMap)){
                if(outdegreeMap.getOrDefault(node,0) > 0){
                    visited.add(node);
                    for(String outNode: graph.get(node)){
                        List<String> path = new LinkedList<>();
                        path.add(node);
                        visited.add(outNode);
                        if(isOneInOneOutNode(outNode, outdegreeMap, indegreeMap)){
                            path.add(outNode);
                            dfs(path, outNode, graph, outdegreeMap, indegreeMap, visited);
                        }else{
                            path.add(outNode);
                        }
                        res.add(path);
                    }
                }
            }
        }
        for(String node: graph.keySet()){
            if(!visited.contains(node)){
                List<String> path = new LinkedList<>();
                path.add(node);
                helper(path, node, graph,visited);
                res.add(path);
            }
        }

        return res;
    }

    private static void helper(List<String> path, String node, Map<String, List<String>> graph, Set<String> visited) {
        for(String outNode: graph.get(node)){
            if(!visited.contains(outNode)){
                path.add(outNode);
                visited.add(outNode);
                helper(path, outNode, graph, visited);
            }
        }
    }

    private static void dfs(List<String> path, String node, Map<String, List<String>> graph, Map<String, Integer> outdegreeMap, Map<String, Integer> indegreeMap, Set<String> visited) {
        for(String outNode: graph.get(node)){
            visited.add(outNode);
            if(isOneInOneOutNode(outNode, outdegreeMap, indegreeMap)){
                path.add(outNode);
                dfs(path, outNode, graph, outdegreeMap,indegreeMap, visited);
            }else{
                path.add(outNode);
                return;
            }
        }
    }

    private static boolean isOneInOneOutNode(String node, Map<String, Integer> outdegreeMap, Map<String, Integer> indegreeMap) {
        return outdegreeMap.getOrDefault(node,0) == 1 && indegreeMap.getOrDefault(node,0 ) == 1;
    }

    private static void contructDegreeMap(Map<String, List<String>> graph, Map<String, Integer> outdegreeMap, Map<String, Integer> indegreeMap) {
        for (String outNode : graph.keySet()) {
            outdegreeMap.put(outNode, graph.getOrDefault(outNode, new LinkedList<>()).size());
            for (String inNode : graph.get(outNode)) {
                indegreeMap.put(inNode, indegreeMap.getOrDefault(inNode, 0) + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> graph = readFromFile("./resource/module2/dataset_6207_2.txt");
        List<List<String>> paths = MaximalNonBranchingPaths(graph);
        for(List<String> path : paths) {
            printPath(path);
        }
    }
}
