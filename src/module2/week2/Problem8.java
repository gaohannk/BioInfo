package module2.week2;

import java.io.IOException;
import java.util.*;

import static common.PrintUtils.*;
import static module2.week2.PrintUtils.readFromFile;

public class Problem8 {
    /**
     * Code Challenge: Implement MaximalNonBranchingPaths.
     *      Input: The adjacency list of a graph whose nodes are integers.
     *      Output: The collection of all maximal nonbranching paths in this graph.
     *  MaximalNonBranchingPaths(Graph)
     *         Paths ← empty list
     *         for each node v in Graph
     *             if v is not a 1-in-1-out node
     *                 if out(v) > 0
     *                     for each outgoing edge (v, w) from v
     *                         Path ← the path consisting of single edge (v, w)
     *                         while w is a 1-in-1-out node
     *                             extend NonBranchingPath by the edge (w, u)
     *                             w ← u
     *                         add NonBranchingPath to the set Paths
     *         for each isolated cycle Cycle in Graph
     *             add Cycle to Paths
     *         return Paths
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
