import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph traversal algorithms.
 */
public class GraphAlgorithms {

    //constructor
    public GraphAlgorithms(){

    }

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you should use is the adjacency
     * list from graph. DO NOT create new instances of Map for BFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the bfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    //bfs using queue
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        Queue<Vertex<T>> toVisit = new LinkedList<>(); 

        Set<Vertex<T>> visited = new HashSet<>(); // to check whether need to visit 

        List<Vertex<T>> visitedList = new ArrayList<>(); // to return the record

        toVisit.add(start);
        visited.add(start);

        while (toVisit.size() != 0){
            Vertex current = toVisit.poll();
            visitedList.add(current);

            List<VertexDistance<T>> adjVertices = graph.getAdjList().get(current);

            for(VertexDistance<T> vd : adjVertices){
                if (! visited.contains(vd.getVertex())){
                    toVisit.add(vd.getVertex());
                    visited.add(vd.getVertex());

                }
            }
        }

        return visitedList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: This method should be implemented recursively. You may need to
     * create a helper method.
     *
     * You may import/use java.util.Set, java.util.List, and any classes that
     * implement the aforementioned interfaces, as long as they are efficient.
     *
     * The only instance of java.util.Map that you may use is the adjacency list
     * from graph. DO NOT create new instances of Map for DFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the dfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    //recursive dfs
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        Set<Vertex<T>> visited = new HashSet<>(); // to check whether need to visit 
        List<Vertex<T>> visitedList = new ArrayList<>(); // to return the record

        dfsHelper(start, graph, visited, visitedList);

        return visitedList;
        
    }

    private static <T> void dfsHelper (Vertex<T> v, Graph<T> g, Set<Vertex<T>> visited, List<Vertex<T>> visitedList){

        visited.add(v);
        visitedList.add(v);

        List<VertexDistance<T>> adjVertices = g.getAdjList().get(v);

        for(VertexDistance<T> vd : adjVertices){
            if (! visited.contains(vd.getVertex())){
                    dfsHelper(vd.getVertex(), g, visited, visitedList);
                
                }
        }
    }



    public static void main(String[] args) {
        Vertex<String> A = new Vertex<String>("A");
        Vertex<String> B = new Vertex<String>("B");
        Vertex<String> C = new Vertex<String>("C");
        Vertex<String> D = new Vertex<String>("D");
        Vertex<String> E = new Vertex<String>("E");
        Set<Vertex<String>> vertices = new HashSet<>();
        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);


        Edge<String> a = new Edge<>(A, B, 1);
        Edge<String> b = new Edge<>(B, C, 1);
        Edge<String> c = new Edge<>(A, C, 1);
        Edge<String> d = new Edge<>(A, D, 1);
        Edge<String> e = new Edge<>(C, E, 1);
        Set<Edge<String>> edges = new HashSet<>();
        edges.add(a);
        edges.add(b);
        edges.add(c);
        edges.add(d);
        edges.add(e);

        Graph g = new Graph<>(vertices, edges);

        System.out.println(bfs(A, g));
        System.out.println(dfs(A, g));




        
    }
}