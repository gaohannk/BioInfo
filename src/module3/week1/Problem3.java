package module3.week1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem3 {

    int V; // No. of vertices
	int num;

    List<Integer> adjListArray[];

    public Problem3(int V) {
        this.V = V;
        List<Integer> adjListArray[] = new LinkedList[V];
        this.adjListArray = adjListArray;

        for (int i = 0; i < V; i++) {
            adjListArray[i] = new LinkedList<>();
        }
    }

    // Utility function to add edge
    public void addEdge(int src, int dest) {

        this.adjListArray[src].add(dest);

    }

    private void allTopologicalSortsUtil(boolean[] visited,
                                         int[] indegree, ArrayList<Integer> stack) {
        // To indicate whether all topological are found or not
        boolean flag = false;

        for (int i = 0; i < this.V; i++) {
            // If indegree is 0 and not yet visited then only choose that vertex
            if (!visited[i] && indegree[i] == 0) {

                // including in result
                visited[i] = true;
                stack.add(i);
                for (int adjacent : this.adjListArray[i]) {
                    indegree[adjacent]--;
                }
                allTopologicalSortsUtil(visited, indegree, stack);

                // resetting visited, res and indegree for backtracking
                visited[i] = false;
                stack.remove(stack.size() - 1);
                for (int adjacent : this.adjListArray[i]) {
                    indegree[adjacent]++;
                }

                flag = true;
            }
        }
        // We reach here if all vertices are visited.
        if (!flag) {
            stack.forEach(i -> System.out.print(i + " "));
            System.out.println();
            num++;
        }
    }

    public void allTopologicalSorts() {
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[this.V];
        int[] indegree = new int[this.V];
        ArrayList<Integer> stack = new ArrayList<>();

        for (int i = 0; i < this.V; i++) {
            for (int var : this.adjListArray[i]) {
                indegree[var]++;
            }
        }
        allTopologicalSortsUtil(visited, indegree, stack);
    }

    public static void main(String[] args) {

        Problem3 graph = new Problem3(8);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
		graph.addEdge(1, 4);
		graph.addEdge(1, 6);
        graph.addEdge(1, 7);
        graph.addEdge(2, 4);
        graph.addEdge(2, 6);
		graph.addEdge(4, 5);

        System.out.println("All Topological sorts");
		graph.allTopologicalSorts();
		System.out.println(graph.num);
    }

}
