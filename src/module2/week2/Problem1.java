package module2.week2;

import java.util.*;

import static common.PrintUtils.printCycle;
import static module2.week2.PrintUtils.readFromFile;
import static module2.week2.PrintUtils.writeToFile;

public class Problem1 {
    public static Set<String> outEdgeNode = new HashSet<>();

    /**
     * EulerianCycle(Graph)
     *         form a cycle Cycle by randomly walking in Graph (don't visit the same edge twice!)
     *         while there are unexplored edges in Graph
     *             select a node newStart in Cycle with still unexplored edges
     *             form Cycle’ by traversing Cycle (starting at newStart) and then randomly walking
     *             Cycle ← Cycle’
     *         return Cycle
     *
     *  Input: The adjacency list of an Eulerian directed graph.
     *  Output: An Eulerian cycle in this graph.
     */
    public static List<String> EulerianCycle(Map<String, List<String>> graph) {
        for (String node : graph.keySet()) {
            outEdgeNode.add(node);
        }

        List<String> finalCycle = new LinkedList<>();

        while (outEdgeNode.size() != 0) {
            String current = getNodehasOutEdge(finalCycle, graph);
            List<String> cycle = new LinkedList<>();
            cycle = formACycle(current, cycle, graph);
//            System.out.println("Current: ");
//            printCycle(cycle);
            removeNoOutEdgeNode(graph);
            mergeCycle(finalCycle, cycle);
//            System.out.println("Merge: ");
//            printCycle(finalCycle);

        }
        return finalCycle;
    }

    private static String getNodehasOutEdge(List<String> finalCycle, Map<String, List<String>> graph) {
        for (String node : finalCycle) {
            if (graph.get(node).size() != 0) {
                return node;
            }
        }
        return graph.keySet().iterator().next();
    }


    private static void mergeCycle(List<String> finalCycle, List<String> cycle) {
        for (int i = 0; i < finalCycle.size(); i++) {
            if (finalCycle.get(i).equals(cycle.get(0))) {
                for (int j = cycle.size() - 1; j >= 0; j--) {
                    finalCycle.add(i, cycle.get(j));
                }
                return;
            }
        }
        // First Cycle
        finalCycle.addAll(cycle);
        finalCycle.add(cycle.get(0));
    }

    private static List<String> formACycle(String current, List<String> cycle, Map<String, List<String>> graph) {
        if (cycle.size() > 0 && cycle.get(0).equals(current)) {
            //cycle.add(current);
            return cycle;
        }
        cycle.add(current);
        String next = graph.get(current).remove(0);
        return formACycle(next, cycle, graph);
    }

    private static void removeNoOutEdgeNode(Map<String, List<String>> graph) {
        for (String node : graph.keySet()) {
            if (graph.get(node).size() == 0) {
                outEdgeNode.remove(node);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, List<String>> graph = readFromFile("./resource/module2/dataset_203_2.txt");
        List<String> finalCycle = EulerianCycle(graph);
        printCycle(finalCycle);
        writeToFile(finalCycle, "./resource/module2/output1.txt");
    }

}
