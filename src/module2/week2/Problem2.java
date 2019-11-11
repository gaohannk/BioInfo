package module2.week2;

import java.util.*;

import static common.PrintUtils.*;
import static module2.week2.PrintUtils.readFromFile;
import static module2.week2.PrintUtils.writeToFile;


public class Problem2 {

    /**
     * Code Challenge: Solve the Eulerian Path Problem.
     *      Input: The adjacency list of a directed graph that has an Eulerian path.
     *      Output: An Eulerian path in this graph.
     * @return
     * @param graph
     */
    protected static List<String> EulerianPath(Map<String, List<String>> graph) {
        Map<String, Integer> outdegreeMap = new HashMap<>();
        Map<String, Integer> indegreeMap = new HashMap<>();

        contructDegreeMap(graph, outdegreeMap, indegreeMap);
        String out = findUnbalancedOutNode(outdegreeMap, indegreeMap);
        String in = findUnbalancedInNode(outdegreeMap, indegreeMap);

        Set<String> outEdgeNode = new HashSet<>();
        List<String> finalPath = new LinkedList<>();

        for (String node : graph.keySet()) {
            outEdgeNode.add(node);
        }

        while (outEdgeNode.size() != 0) {
            List<String> cycle = new LinkedList<>();
            String current = getNodehasOutEdgeForEulerianPath(finalPath, out, graph);
            cycle = formACycleForEulerianPath(current, cycle, in, out, graph, indegreeMap);
            removeNoOutEdgeNode(graph, outEdgeNode);
            mergeCycleForEulerianPath(finalPath, cycle, in, out, indegreeMap);

        }
        return finalPath;
    }

    private static String findUnbalancedOutNode(Map<String, Integer> outdegreeMap,  Map<String, Integer> indegreeMap) {
        for (String key : outdegreeMap.keySet()) {
            if (indegreeMap.getOrDefault(key, 0) < outdegreeMap.get(key)) {
                return key;
            }
        }
        return "";
    }

    private static String findUnbalancedInNode(Map<String, Integer> outdegreeMap,  Map<String, Integer> indegreeMap) {
        for (String key : indegreeMap.keySet()) {
            if (indegreeMap.get(key) > outdegreeMap.getOrDefault(key, 0)) {
                return key;
            }
        }
        return "";
    }


    private static void contructDegreeMap(Map<String, List<String>> graph, Map<String, Integer> outdegreeMap, Map<String, Integer> indegreeMap) {
        for (String outNode : graph.keySet()) {
            outdegreeMap.put(outNode, graph.getOrDefault(outNode, new LinkedList<>()).size());
            for (String inNode : graph.get(outNode)) {
                indegreeMap.put(inNode, indegreeMap.getOrDefault(inNode, 0) + 1);
            }
        }
    }


    private static String getNodehasOutEdgeForEulerianPath(List<String> finalCycle, String out, Map<String, List<String>> graph) {
        for (String node : finalCycle) {
            if (graph.get(node).size() != 0) {
                return node;
            }
        }
        return out;
    }

    private static void mergeCycleForEulerianPath(List<String> finalCycle, List<String> cycle, String inNode, String outNode, Map<String, Integer> indegreeMap) {
        if (cycle.get(cycle.size() - 1) == inNode && cycle.get(0) != outNode && indegreeMap.get(inNode) == 0) {
            cycle.remove(0);
            finalCycle.addAll(cycle);
            return;
        }
        for (int i = finalCycle.size() - 1; i >= 0; i--) {
            if (finalCycle.get(i).equals(cycle.get(0))) {
                finalCycle.remove(i);
                for (int j = cycle.size() - 1; j >= 0; j--) {
                    finalCycle.add(i, cycle.get(j));
                }
                return;
            }
        }
        // First Cycle
        finalCycle.addAll(cycle);
    }

    private static void removeNoOutEdgeNode(Map<String, List<String>> graph, Set<String> outEdgeNode) {
        for (String node : graph.keySet()) {
            if (graph.get(node).size() == 0) {
                outEdgeNode.remove(node);
            }
        }
    }

    private static List<String> formACycleForEulerianPath(String current,
                                                          List<String> cycle,
                                                          String inNode,
                                                          String outNode,
                                                          Map<String, List<String>> graph,
                                                          Map<String, Integer> indegreeMap) {
        // tail
        if (current.equals(inNode) && indegreeMap.get(inNode) == 0) {
            cycle.add(current);
            return cycle;
        }
        // middle
        if ((cycle.size() > 0 && cycle.get(0).equals(current))) {
            cycle.add(current);
            return cycle;
        }
        // head
        if (cycle.size() > 1 && cycle.get(0).equals(outNode) && cycle.get(1).equals(current)) {
            cycle.add(current);
            return cycle;
        }
        cycle.add(current);

        List<String> candidate = new LinkedList<>(graph.get(current));
        for (String next : candidate) {
            if (graph.get(current).size() > 1) {
                if (!next.equals(inNode)) {
                    graph.get(current).remove(next);
                    formACycleForEulerianPath(next, cycle, inNode, outNode, graph, indegreeMap);
                    break;
                } else {
                    continue;
                }
            } else {
                graph.get(current).remove(next);
                indegreeMap.put(next, indegreeMap.get(next) - 1);
                formACycleForEulerianPath(next, cycle, inNode, outNode, graph, indegreeMap);
                break;
            }
        }
        return cycle;
    }

    public static void main(String[] args) throws Exception {
        Map<String, List<String>> graph = readFromFile("./resource/module2/dataset_203_6.txt");
        List<String> finalPath = EulerianPath(graph);
        printCycle(finalPath);
        writeToFile(finalPath, "./resource/module2/output2.txt");
    }
}
