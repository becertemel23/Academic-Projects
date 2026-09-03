import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 3 - Part 3: Social Network Analysis
 *
 * In this section, you will apply graph algorithms to analyze a social
 * network. Each vertex represents a person (identified by an integer ID), and
 * each edge represents a connection (friendship) between two people.
 *
 * You will implement methods to find connected communities, identify the most
 * connected person, and compute the degrees of separation between two people.
 *
 * All methods are static and operate on a {@link Graph} object from Part 1.
 * You may use any classes from java.util.
 *
 * CONSTRAINTS:
 * - You may ONLY modify the sections marked with // TODO.
 * - Do NOT change method signatures, class name, or provided code.
 *
 * Total: 20 points
 */
public class SocialNetwork {

  // ========================== STUDENT INFO ==========================
  // Fill in your information below. This is MANDATORY and will be used
  // for grading identification. Do NOT leave these empty.
  public static final String STUDENT_NAME =
      ""; // TODO: Enter your full name (e.g., "Ahmet Yilmaz")
  public static final String STUDENT_ID =
      ""; // TODO: Enter your student ID number (e.g., "0012345")
  public static final String STUDENT_KU_EMAIL =
      ""; // TODO: Enter your KU email (e.g., "ayilmaz20@ku.edu.tr")
  // ==================================================================

  /**
   * Counts the number of connected components in the graph.
   * [7 points]
   *
   * A connected component is a maximal set of vertices such that there is a
   * path between every pair of vertices in the set. For directed graphs,
   * treat all edges as undirected (i.e., if there is an edge from A to B,
   * consider A and B as connected regardless of direction).
   *
   * Pseudocode:
   *   1. Create a visited set (HashSet<Integer>).
   *   2. Initialize componentCount = 0.
   *   3. For each vertex v in the graph:
   *      - If v has not been visited:
   *          a. Increment componentCount.
   *          b. Perform a BFS (or DFS) from v, marking all reachable
   *             vertices as visited.
   *          c. IMPORTANT: When exploring neighbors, consider both
   *             outgoing AND incoming neighbors (for directed graphs,
   *             this means treating edges as undirected).
   *             - Outgoing neighbors: use g.getNeighbors(v).
   *             - Incoming neighbors: for each vertex u, check if u
   *               has an edge to the current vertex.
   *             Hint: For simplicity, you can build an undirected version
   *             of the neighbor list by iterating over all vertices.
   *             Alternatively, if the graph is undirected, getNeighbors
   *             already returns all connected vertices.
   *   4. Return componentCount.
   *
   * @param g the graph representing the social network
   * @return the number of connected components
   */
  public static int countComponents(Graph g) {
    // TODO
    return 0;
  }

  /**
   * Finds the most connected person in the network.
   * [5 points]
   *
   * Returns the vertex with the highest total degree (outDegree + inDegree).
   * For undirected graphs, outDegree and inDegree are the same, so total
   * degree is simply outDegree (do not double-count).
   *
   * If multiple vertices share the same highest degree, return the one with
   * the smallest vertex ID.
   *
   * If the graph has no vertices, return -1.
   *
   * Pseudocode:
   *   1. If the graph has no vertices, return -1.
   *   2. Initialize bestVertex = -1, bestDegree = -1.
   *   3. For each vertex v in the graph:
   *      - Compute the total degree of v:
   *          - If undirected: totalDegree = g.outDegree(v)
   *          - If directed:   totalDegree = g.outDegree(v) + g.inDegree(v)
   *      - If totalDegree > bestDegree, or
   *        (totalDegree == bestDegree and v < bestVertex):
   *          - Update bestVertex = v, bestDegree = totalDegree.
   *   4. Return bestVertex.
   *
   * @param g the graph representing the social network
   * @return the vertex ID of the most connected person, or -1 if empty
   */
  public static int mostConnected(Graph g) {
    // TODO
    return -1;
  }

  /**
   * Computes the degrees of separation between two people.
   * [8 points]
   *
   * The degrees of separation is the length of the shortest path (number of
   * edges) between two vertices. Uses BFS to find the shortest unweighted path.
   *
   * For directed graphs, treat edges as undirected (same as countComponents).
   *
   * Returns -1 if:
   * - Either person1 or person2 is not in the graph, or
   * - There is no path between them.
   *
   * Returns 0 if person1 == person2.
   *
   * Pseudocode:
   *   1. If person1 or person2 is not in the graph, return -1.
   *   2. If person1 == person2, return 0.
   *   3. Create a visited set and a queue for BFS.
   *   4. Create a distance map (HashMap<Integer, Integer>) to track the
   *      distance from person1 to each visited vertex.
   *   5. Add person1 to the queue, mark it as visited, set its distance to 0.
   *   6. While the queue is not empty:
   *      a. Dequeue a vertex v.
   *      b. For each neighbor n of v (treat edges as undirected):
   *         - If n has not been visited:
   *             - Mark n as visited.
   *             - Set distance[n] = distance[v] + 1.
   *             - If n == person2, return distance[n].
   *             - Enqueue n.
   *   7. Return -1 (no path found).
   *
   * @param g       the graph representing the social network
   * @param person1 the first person's vertex ID
   * @param person2 the second person's vertex ID
   * @return the degrees of separation, or -1 if unreachable
   */
  public static int degreesOfSeparation(Graph g, int person1, int person2) {
    // TODO
    return -1;
  }
}
