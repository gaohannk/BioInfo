package module2.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrintUtils {
    public static String printGraph(Map<String, Set<String>> graph) {
        StringBuilder sb = new StringBuilder();
        for (String key : graph.keySet()) {
            sb.append(key + " -> ");
            for (String adjNode : graph.get(key)) {
                sb.append(adjNode + ",");
            }
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static String printGraph2(Map<String, List<String>> graph) {
        StringBuilder sb = new StringBuilder();
        for (String key : graph.keySet()) {
            sb.append(key + " -> ");
            for (String adjNode : graph.get(key)) {
                sb.append(adjNode + ",");
            }
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
