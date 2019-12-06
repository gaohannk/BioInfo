package module3.common;

import module2.week2.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;

public class PrintUtils {
    public static <T extends Collection<E>, E> String printGraph(Map<String, T> graph) {
        StringBuilder sb = new StringBuilder();
        for (String key : graph.keySet()) {
            sb.append(key + " -> ");
            for (E adjNode: graph.get(key)) {
                sb.append(adjNode.toString() + ",");
            }
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        sb.append("\n");
        return sb.toString();
    }
    public static void printPairReads(List<Pair<String>> pairReads) {
        printListInOneline(pairReads.stream().map( pair -> pair.read1+ "|"+ pair.read2).collect(Collectors.toList()));
    }
}
