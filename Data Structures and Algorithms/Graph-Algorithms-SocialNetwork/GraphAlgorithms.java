import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 3 - Part 2: Graph Algorithms
 *
 * In this part, you will implement several fundamental graph algorithms.
 * All methods are static and operate on a {@link Graph} object from Part 1.
 *
 * You may use any classes from java.util (ArrayList, LinkedList, HashMap,
 * HashSet, PriorityQueue, Stack, Queue, Collections, etc.).
 *
 * CONSTRAINTS:
 * - You may ONLY modify the sections marked with // TODO.
 * - Do NOT change method signatures, class name, or provided code.
 *
 * Total: 65 points
 */
public class GraphAlgorithms {

    // ========================== STUDENT INFO ==========================
    // Fill in your information below. This is MANDATORY and will be used
    // for grading identification. Do NOT leave these empty.
    public static final String STUDENT_NAME = "";       // TODO: Enter your full name (e.g., "Ahmet Yilmaz")
    public static final String STUDENT_ID = "";         // TODO: Enter your student ID number (e.g., "0012345")
    public static final String STUDENT_KU_EMAIL = "";   // TODO: Enter your KU email (e.g., "ayilmaz20@ku.edu.tr")
    // ==================================================================

    /**
     * Performs a Breadth-First Search (BFS) traversal starting from the given vertex.
     * [12 points]
     *
     * BFS explores vertices level by level. It uses a queue to keep track of which
     * vertex to visit next. Neighbors are visited in sorted (ascending) order to
     * ensure a deterministic traversal.
     *
     * Returns an ArrayList of vertices in the order they were visited.
     * If the start vertex is not in the graph, return an empty list.
     *
     * Pseudocode:
     *   1. If the start vertex is not in the graph, return an empty list.
     *   2. Create a visited set (HashSet<Integer>).
     *   3. Create a queue (LinkedList<Integer>) and add the start vertex.
     *   4. Mark the start vertex as visited.
     *   5. Create a result list (ArrayList<Integer>).
     *   6. While the queue is not empty:
     *      a. Dequeue a vertex v.
     *      b. Add v to the result list.
     *      c. Get the neighbors of v from the graph.
     *      d. Sort the neighbors in ascending order (use Collections.sort()).
     *      e. For each neighbor n in sorted order:
     *         - If n has not been visited:
     *             - Mark n as visited.
     *             - Enqueue n.
     *   7. Return the result list.
     *
     * @param g     the graph to traverse
     * @param start the starting vertex
     * @return list of vertices in BFS visit order
     */
    public static ArrayList<Integer> bfs(Graph g, int start) {
        // TODO
        return new ArrayList<>();
    }

    /**
     * Performs a Depth-First Search (DFS) traversal starting from the given vertex.
     * [12 points]
     *
     * DFS explores as far as possible along each branch before backtracking.
     * This implementation uses an explicit stack. Neighbors are pushed in reverse
     * sorted order so that they are popped in ascending order, ensuring a
     * deterministic traversal.
     *
     * Returns an ArrayList of vertices in the order they were visited.
     * If the start vertex is not in the graph, return an empty list.
     *
     * Pseudocode:
     *   1. If the start vertex is not in the graph, return an empty list.
     *   2. Create a visited set (HashSet<Integer>).
     *   3. Create a stack (Stack<Integer>) and push the start vertex.
     *   4. Create a result list (ArrayList<Integer>).
     *   5. While the stack is not empty:
     *      a. Pop a vertex v.
     *      b. If v has already been visited, skip it (continue).
     *      c. Mark v as visited.
     *      d. Add v to the result list.
     *      e. Get the neighbors of v from the graph.
     *      f. Sort the neighbors in ascending order.
     *      g. Push the neighbors onto the stack in REVERSE sorted order
     *         (so that the smallest neighbor is on top and gets popped first).
     *   6. Return the result list.
     *
     * @param g     the graph to traverse
     * @param start the starting vertex
     * @return list of vertices in DFS visit order
     */
    public static ArrayList<Integer> dfs(Graph g, int start) {
        // TODO
        return new ArrayList<>();
    }

    /**
     * Computes the shortest path distances from a start vertex to all reachable
     * vertices using Dijkstra's algorithm.
     * [18 points]
     *
     * Returns a HashMap where each key is a reachable vertex and each value is
     * the shortest distance from the start vertex.
     *
     * Uses a PriorityQueue of int arrays: {vertex, distance}, ordered by distance.
     *
     * IMPORTANT: Dijkstra's algorithm assumes non-negative edge weights.
     *
     * If the start vertex is not in the graph, return an empty map.
     *
     * Pseudocode:
     *   1. If the start vertex is not in the graph, return an empty map.
     *   2. Create a distances map (HashMap<Integer, Integer>).
     *   3. Create a priority queue (PriorityQueue<int[]>) that compares by
     *      the second element (distance): (a, b) -> a[1] - b[1].
     *   4. Add {start, 0} to the priority queue.
     *   5. While the priority queue is not empty:
     *      a. Poll the entry with the smallest distance: {u, distU}.
     *      b. If u is already in the distances map, skip it (continue).
     *         (u has already been finalized with a shorter or equal distance.)
     *      c. Put (u, distU) into the distances map.
     *      d. For each edge from u (use g.getEdges(u)):
     *         - Let neighbor = edge.dest, weight = edge.weight.
     *         - Let newDist = distU + weight.
     *         - If neighbor is NOT yet in the distances map:
     *             - Add {neighbor, newDist} to the priority queue.
     *   6. Return the distances map.
     *
     * @param g     the graph (must have non-negative weights)
     * @param start the source vertex
     * @return map of vertex -> shortest distance from start
     */
    public static HashMap<Integer, Integer> dijkstra(Graph g, int start) {
        // TODO
        return new HashMap<>();
    }

    /**
     * Determines whether the graph contains a cycle.
     * [10 points]
     *
     * Uses DFS-based cycle detection with three-color marking:
     *   - WHITE (0): vertex has not been visited
     *   - GRAY  (1): vertex is currently being explored (in the current DFS path)
     *   - BLACK (2): vertex and all its descendants have been fully explored
     *
     * For directed graphs, a cycle exists if DFS encounters a GRAY vertex
     * (a back edge to an ancestor in the current path).
     *
     * For undirected graphs, a cycle exists if DFS encounters a visited vertex
     * that is not the immediate parent of the current vertex.
     *
     * Pseudocode (directed):
     *   1. Create a color map (HashMap<Integer, Integer>) and initialize
     *      all vertices to WHITE (0).
     *   2. For each vertex v in the graph:
     *      - If v is WHITE, call the helper: dfsCycleDirected(g, v, color).
     *      - If the helper returns true, return true.
     *   3. Return false.
     *
     *   Helper dfsCycleDirected(g, v, color):
     *   1. Set color[v] = GRAY.
     *   2. For each neighbor n of v:
     *      - If color[n] == GRAY, return true (back edge = cycle).
     *      - If color[n] == WHITE:
     *          - Recursively call dfsCycleDirected(g, n, color).
     *          - If it returns true, return true.
     *   3. Set color[v] = BLACK.
     *   4. Return false.
     *
     * Pseudocode (undirected):
     *   1. Create a visited set (HashSet<Integer>).
     *   2. For each vertex v in the graph:
     *      - If v is not visited, call the helper: dfsCycleUndirected(g, v, -1, visited).
     *      - If the helper returns true, return true.
     *   3. Return false.
     *
     *   Helper dfsCycleUndirected(g, v, parent, visited):
     *   1. Mark v as visited.
     *   2. For each neighbor n of v:
     *      - If n is not visited:
     *          - Recursively call dfsCycleUndirected(g, n, v, visited).
     *          - If it returns true, return true.
     *      - Else if n != parent, return true (cycle found).
     *   3. Return false.
     *
     * @param g the graph to check
     * @return true if the graph contains a cycle, false otherwise
     */
    public static boolean hasCycle(Graph g) {
        // TODO
        return false;
    }

    /**
     * Computes a topological ordering of a directed acyclic graph (DAG)
     * using Kahn's algorithm.
     * [13 points]
     *
     * A topological ordering is a linear ordering of vertices such that for
     * every directed edge (u, v), vertex u comes before vertex v.
     *
     * If the graph contains a cycle, a valid topological ordering does not exist;
     * return null in this case.
     *
     * If the graph is undirected, return null (topological sort is only defined
     * for directed graphs).
     *
     * When multiple vertices have in-degree 0, pick the smallest vertex first
     * to ensure a deterministic result. Use a PriorityQueue for this.
     *
     * Pseudocode:
     *   1. If the graph is undirected, return null.
     *   2. Compute the in-degree of every vertex.
     *      - Create an inDegree map (HashMap<Integer, Integer>).
     *      - For each vertex v, set inDegree[v] = g.inDegree(v).
     *   3. Create a priority queue (PriorityQueue<Integer>) and add all vertices
     *      whose in-degree is 0.
     *   4. Create a result list (ArrayList<Integer>).
     *   5. While the priority queue is not empty:
     *      a. Poll the smallest vertex v.
     *      b. Add v to the result list.
     *      c. For each neighbor n of v:
     *         - Decrement inDegree[n] by 1.
     *         - If inDegree[n] becomes 0, add n to the priority queue.
     *   6. If the result list size != number of vertices in the graph,
     *      return null (cycle detected).
     *   7. Return the result list.
     *
     * @param g the directed graph to sort
     * @return topological ordering as an ArrayList, or null if not possible
     */
    public static ArrayList<Integer> topologicalSort(Graph g) {
        // TODO
        return null;
    }
}
