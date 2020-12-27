package graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();

        System.out.println(" enter 'exit' for exit \n enter update for new graph \n enter search vertex_name for depth-first search"
                + " \n enter sort for topological sorting \n enter vertex_name to create a new vertex \n enter vertex_name-vertex_name to create new edge (start_vertex-end_vertex)");
        while (true) {
            String line = reader.readLine();

            switch (line.split(" ")[0]) {
                case "exit":
                    return;
                case "update":
                    graph = new Graph();
                    break;
                case "search":
                    graph.depthFirstSearch(line.split(" ")[1]);
                    break;
                case "sort":
                    graph.topologicalSorting();
                    break;
                default:
                    if (line.contains("-")) {
                        String[] strings = line.split("-");
                        graph.addEdge(strings[0], strings[1]);
                    } else if(line.length() == 1) {
                        graph.addVertex(line);
                    } else {
                        System.out.println("Vertex name is too long");
                    }
            }

            graph.showAdjacencyMatrix();
        }
    }
}

