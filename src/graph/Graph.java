package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Реализовать классы вершины и графа. Граф  неориентированный.
 * Связи представить в виде матрицы смежности.
 * Предусмотреть возможость добавления новых вершин и дуг, обхода графа в глубину.
 *
 * Добавить в класс графа метод топологической сортировки.
 */
class Graph {

    /**
     * список вершин графа
     */
    private List<Vertex> vertices = new ArrayList<>();

    /**
     * добавление новой вершины
     * @param name название вершины
     */
    void addVertex(String name) {
        if (!findVertex(name).isPresent()) {
            vertices.add(new Vertex(name));
        }
    }

    /**
     * демонстрация графа матрицей смежности
     */
    void showAdjacencyMatrix() {
        System.out.print("  ");
        vertices.forEach(vertex -> System.out.print(vertex.getName() + " ")); // первый ряд
        System.out.println();
        vertices.forEach(vertex -> { // последующие ряды
            System.out.print(vertex.getName() + ' ');
            vertices.forEach(v -> System.out.print(v.getEdgesSend().contains(vertex) ? "1 " : "0 "));
            System.out.println();
        });
    }

    /**
     * добавление ребра
     * @param a название верхины, откуда
     * @param b название вершины, куда
     */
    void addEdge(String a, String b) {
        findVertex(a).ifPresent(aVertex -> aVertex.addEdge(findVertex(b)));
    }

    /**
     * поиск вершины
     * @param name азвание искомой вершины
     * @return вершину или null, если вершины с таким названием не оказалось
     */
    private Optional<Vertex> findVertex(String name) {
        return vertices
                .stream()
                .filter(vertex -> vertex.getName().equals(name))
                .findFirst();
    }

    /**
     * метод поиска в глубину
     * @param name название вершины, с которой этой поиск начинается
     */
    void depthFirstSearch(String name) {
        Vertex current = findVertex(name).orElse(null);
        LinkedList<Vertex> stack = new LinkedList<>(); // очередь вершины, с которых ведеться обход
        Set<Vertex> foundedVertex = new HashSet<>(); // очередь пройденных вершин
        foundedVertex.add(current);


        while (current != null) {
            // добавляем выбранную вершину в список пройденных
            foundedVertex.add(current);
            // находим любую дочернюю вершину, которой еще нет в списке пройденных foundedVertex
            Vertex next = current.getEdgesSend().stream().filter(v -> !foundedVertex.contains(v)).findFirst().orElse(null);

            if (next == null) {
                // если валидных дочерних нет - берем другую вершину с очереди, либо заканчиваем, если таковых нету
                if (stack.isEmpty()) {
                    break;
                }
                current = stack.removeLast();
            } else {
                // добавляет выбранную вершину в очередь, выбираем её дочернюю вершину
                System.out.print(next.getName() + " ");
                stack.addLast(current);
                current = next;
            }
        }
        System.out.println("\n");
    }


    /**
     * частичная сортировка методом топологической сортировки
     */
    void topologicalSorting() {
        List<Vertex> foundedVertex = new ArrayList<>(); // отсортированные вершины

        while (vertices.size() > foundedVertex.size()) { // пока количество отсортированных < количества вершин
            Vertex ver = vertices // берем вершину,
                    .stream()
                    .filter(vertex -> !foundedVertex.contains(vertex))
                    // удаляем все, в которых входных вершин либо > 0, либо в которые идут ребра от вершин, еще не добавленных foundedVertex
                    .filter(vertex -> foundedVertex.containsAll(vertex.getEdgesGet()))
                    .findFirst()
                    .orElse(null);
            if (ver == null) { // если null, значит у графа есть цикличность (контур), и он не может быть отсортирован этим методом
                System.out.println("graph have a cycle");
                break;
            }
            foundedVertex.add(ver);
        }

        foundedVertex.forEach(e -> System.out.print(e.getName() + " "));
        System.out.println("\n");
    }

}
