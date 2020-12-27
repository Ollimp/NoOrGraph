package graph;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;

/**
 * класс вершины графа
 */

@Getter
class Vertex {

    private String name;
    /**
     * сет вершин, куда ИЗ данной вершины идут ребра
     */
    private Set<Vertex> edgesSend = new HashSet<>();
    /**
     * сет вершин, откуда В данную вершины идут ребра
     */
    private Set<Vertex> edgesGet = new HashSet<>();

    Vertex(String name) {
        this.name = name;
    }

    void addEdge(Optional<Vertex> vertex) {
        vertex.ifPresent(v -> {
            edgesSend.add(v);
            v.edgesGet.add(this);
        });
    }

   private String getName(){
        return name;
    }
    @Override
    public String toString() {
        return name + ' ';
    }
}
