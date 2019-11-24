package module2.common;

import java.util.*;

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
}
