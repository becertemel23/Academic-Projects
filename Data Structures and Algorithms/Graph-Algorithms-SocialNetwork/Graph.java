import java.util.ArrayList;
import java.util.HashMap;

/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 3 - Part 1: Graph Data Structure
 *
 * In this part, you will implement a weighted graph using an adjacency list
 * representation. The graph supports both directed and undirected modes,
 * determined by the {@code directed} flag set at construction time.
 *
 * Internally, the graph stores its adjacency list as a
 * {@code HashMap<Integer, ArrayList<Edge>>}, where each key is a vertex
 * and the associated list contains the outgoing edges from that vertex.
 *
 * CONSTRAINTS:
 * - You may use any classes from java.util.
 * - You may ONLY modify the sections marked with // TODO.
 * - Do NOT change method signatures, class name, or provided code.
 *
 * Total: 35 points
 */
public class Graph {

    // ========================== STUDENT INFO ==========================
    // Fill in your information below. This is MANDATORY and will be used
    // for grading identification. Do NOT leave these empty.
    public static final String STUDENT_NAME = "";       // TODO: Enter your full name (e.g., "Ahmet Yilmaz")
    public static final String STUDENT_ID = "";         // TODO: Enter your student ID number (e.g., "0012345")
    public static final String STUDENT_KU_EMAIL = "";   // TODO: Enter your KU email (e.g., "ayilmaz20@ku.edu.tr")
    // ==================================================================

    /**
     * Represents a directed edge to a destination vertex with a given weight.
     * DO NOT MODIFY THIS CLASS.
     */
    public static class Edge {
        public int dest;
        public int weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + dest + ", w=" + weight + ")";
        }
    }

    private HashMap<Integer, ArrayList<Edge>> adjList;
    private int edgeCount;
    private boolean directed;

    /**
     * Constructs an empty graph.
     * DO NOT MODIFY.
     *
     * @param directed true for a directed graph, false for undirected
     */
    public Graph(boolean directed) {
        this.adjList = new HashMap<>();
        this.edgeCount = 0;
        this.directed = directed;
    }

    /**
     * Returns true if this graph is directed.
     * DO NOT MODIFY.
     */
    public boolean isDirected() {
        return directed;
    }

    /**
     * Returns the number of vertices in the graph.
     * DO NOT MODIFY.
     */
    public int vertexCount() {
        return adjList.size();
    }

    /**
     * Returns the number of edges in the graph.
     * For undirected graphs, each undirected edge is counted once
     * (even though it is stored in both directions internally).
     * DO NOT MODIFY.
     */
    public int edgeCount() {
        return edgeCount;
    }

    /**
     * Returns the set of all vertices in the graph.
     * DO NOT MODIFY.
     */
    public ArrayList<Integer> getVertices() {
        return new ArrayList<>(adjList.keySet());
    }

    /**
     * Returns the raw adjacency list (list of edges) for a vertex.
     * Returns null if the vertex does not exist.
     * DO NOT MODIFY.
     */
    public ArrayList<Edge> getEdges(int v) {
        return adjList.get(v);
    }

    // ========================== CORE OPERATIONS ==========================

    /**
     * Adds a vertex to the graph.
     * [3 points]
     *
     * If the vertex already exists, do nothing.
     * Otherwise, add it with an empty edge list.
     *
     * Pseudocode:
     *   if the adjacency list does not contain vertex v:
     *       put v into the adjacency list with a new empty ArrayList
     *
     * @param v the vertex label to add
     */
    public void addVertex(int v) {
        // TODO
    }

    /**
     * Returns true if the graph contains the given vertex.
     * [2 points]
     *
     * @param v the vertex to check
     * @return true if v is in the graph, false otherwise
     */
    public boolean hasVertex(int v) {
        // TODO
        return false;
    }

    /**
     * Adds a weighted edge from src to dest.
     * [6 points]
     *
     * If src or dest do not exist, add them as vertices first.
     * If the edge already exists, update its weight.
     * For undirected graphs, also add (or update) the reverse edge from dest to src.
     *
     * Pseudocode:
     *   1. Call addVertex(src) and addVertex(dest) to ensure both exist.
     *   2. Walk through the edge list of src:
     *      - If an edge to dest is found, update its weight and:
     *          - If undirected, also find and update the reverse edge's weight.
     *          - Return (do not increment edgeCount).
     *   3. If no existing edge was found:
     *      - Add a new Edge(dest, weight) to the edge list of src.
     *      - If undirected, also add a new Edge(src, weight) to the edge list of dest.
     *      - Increment edgeCount by 1.
     *
     * @param src    the source vertex
     * @param dest   the destination vertex
     * @param weight the weight of the edge
     */
    public void addEdge(int src, int dest, int weight) {
        // TODO
    }

    /**
     * Adds an unweighted edge from src to dest (default weight = 1).
     * [2 points]
     *
     * Hint: Simply call addEdge(src, dest, 1).
     *
     * @param src  the source vertex
     * @param dest the destination vertex
     */
    public void addEdge(int src, int dest) {
        // TODO
    }

    /**
     * Returns true if there is an edge from src to dest.
     * [3 points]
     *
     * Pseudocode:
     *   1. If src is not in the graph, return false.
     *   2. Walk through the edge list of src:
     *      - If an edge with dest is found, return true.
     *   3. Return false.
     *
     * @param src  the source vertex
     * @param dest the destination vertex
     * @return true if the edge exists, false otherwise
     */
    public boolean hasEdge(int src, int dest) {
        // TODO
        return false;
    }

    /**
     * Returns the weight of the edge from src to dest.
     * [3 points]
     *
     * Pseudocode:
     *   1. If src is not in the graph, return -1.
     *   2. Walk through the edge list of src:
     *      - If an edge with dest is found, return its weight.
     *   3. Return -1.
     *
     * @param src  the source vertex
     * @param dest the destination vertex
     * @return the edge weight, or -1 if the edge does not exist
     */
    public int getWeight(int src, int dest) {
        // TODO
        return -1;
    }

    /**
     * Removes the edge from src to dest.
     * [4 points]
     *
     * For undirected graphs, also remove the reverse edge from dest to src.
     *
     * Pseudocode:
     *   1. If src is not in the graph, return false.
     *   2. Walk through the edge list of src:
     *      - If an edge with dest is found, remove it from the list.
     *      - If undirected, also find and remove the edge from dest to src.
     *      - Decrement edgeCount by 1.
     *      - Return true.
     *   3. Return false (no such edge found).
     *
     * @param src  the source vertex
     * @param dest the destination vertex
     * @return true if the edge was removed, false if it did not exist
     */
    public boolean removeEdge(int src, int dest) {
        // TODO
        return false;
    }

    /**
     * Returns a list of all neighbor vertex IDs reachable from v via outgoing edges.
     * [4 points]
     *
     * If v is not in the graph, return null.
     *
     * Pseudocode:
     *   1. If v is not in the graph, return null.
     *   2. Create a new ArrayList<Integer>.
     *   3. For each edge in the edge list of v:
     *      - Add edge.dest to the result list.
     *   4. Return the result list.
     *
     * @param v the vertex
     * @return list of neighbor vertex IDs, or null if v does not exist
     */
    public ArrayList<Integer> getNeighbors(int v) {
        // TODO
        return null;
    }

    /**
     * Returns the number of outgoing edges from vertex v.
     * [2 points]
     *
     * Returns -1 if v is not in the graph.
     *
     * @param v the vertex
     * @return the out-degree, or -1 if v does not exist
     */
    public int outDegree(int v) {
        // TODO
        return -1;
    }

    /**
     * Returns the number of incoming edges to vertex v.
     * [2 points]
     *
     * For undirected graphs, this is the same as outDegree(v).
     * For directed graphs, you must count how many vertices have an edge to v.
     *
     * Pseudocode (directed case):
     *   1. If v is not in the graph, return -1.
     *   2. Initialize count = 0.
     *   3. For each vertex u in the graph:
     *      - For each edge in the edge list of u:
     *          - If edge.dest == v, increment count.
     *   4. Return count.
     *
     * @param v the vertex
     * @return the in-degree, or -1 if v does not exist
     */
    public int inDegree(int v) {
        // TODO
        return -1;
    }

    /**
     * Removes a vertex and all edges incident to it.
     * [4 points]
     *
     * Pseudocode:
     *   1. If v is not in the graph, return.
     *   2. For directed graphs:
     *      a. Subtract the number of outgoing edges of v from edgeCount
     *         (these are the edges leaving v).
     *      b. For each other vertex u in the graph:
     *         - Walk through the edge list of u and remove any edge to v.
     *         - For each such edge removed, decrement edgeCount.
     *   3. For undirected graphs:
     *      a. For each edge (v -> dest) in v's edge list:
     *         - Remove the corresponding reverse edge (dest -> v) from dest's edge list.
     *         - Decrement edgeCount by 1.
     *   4. Remove v from the adjacency list.
     *
     * @param v the vertex to remove
     */
    public void removeVertex(int v) {
        // TODO
    }

    /**
     * Returns a String representation of the graph for debugging.
     * DO NOT MODIFY.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(directed ? "Directed" : "Undirected").append(" Graph:\n");
        for (int v : adjList.keySet()) {
            sb.append("  ").append(v).append(" -> ").append(adjList.get(v)).append("\n");
        }
        sb.append("Vertices: ").append(vertexCount());
        sb.append(", Edges: ").append(edgeCount);
        return sb.toString();
    }
}
