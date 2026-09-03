import java.util.ArrayList;
import java.util.HashMap;

/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 3 - Student Autograder
 *
 * This file contains basic test cases for you to verify your implementations.
 * Run this file to check your work before submitting.
 *
 * IMPORTANT: Passing all tests here does NOT guarantee a perfect score.
 * The final grading will use an extended autograder with additional test cases.
 *
 * DO NOT MODIFY THIS FILE.
 * DO NOT SUBMIT THIS FILE.
 */
public class Main {

    private static int passed = 0;
    private static int failed = 0;

    private static void test(String name, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + name);
            passed++;
        } else {
            System.out.println("  [FAIL] " + name);
            failed++;
        }
    }

    private static void section(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    // ======================== PART 1 TESTS ========================

    private static void testGraph() {
        section("Part 1: Graph Data Structure [35 pts]");

        // --- Directed Graph Tests ---
        Graph dg = new Graph(true);
        test("isDirected() returns true for directed graph", dg.isDirected());

        dg.addVertex(0);
        dg.addVertex(1);
        dg.addVertex(2);
        test("vertexCount is 3 after adding 3 vertices", dg.vertexCount() == 3);

        dg.addVertex(0); // duplicate
        test("vertexCount still 3 after duplicate addVertex", dg.vertexCount() == 3);

        test("hasVertex(0) returns true", dg.hasVertex(0));
        test("hasVertex(5) returns false", !dg.hasVertex(5));

        dg.addEdge(0, 1, 10);
        dg.addEdge(0, 2, 20);
        dg.addEdge(1, 2, 30);
        test("edgeCount is 3 after adding 3 edges", dg.edgeCount() == 3);

        test("hasEdge(0, 1) returns true", dg.hasEdge(0, 1));
        test("hasEdge(1, 0) returns false (directed)", !dg.hasEdge(1, 0));

        test("getWeight(0, 1) returns 10", dg.getWeight(0, 1) == 10);
        test("getWeight(1, 0) returns -1 (no reverse edge)", dg.getWeight(1, 0) == -1);
        test("getWeight(0, 5) returns -1 (no such vertex)", dg.getWeight(0, 5) == -1);

        // Test addEdge auto-adds vertices
        dg.addEdge(3, 4);
        test("addEdge auto-adds vertices: hasVertex(3)", dg.hasVertex(3));
        test("addEdge auto-adds vertices: hasVertex(4)", dg.hasVertex(4));
        test("addEdge(3,4) with default weight: getWeight is 1", dg.getWeight(3, 4) == 1);

        // Test update weight
        dg.addEdge(0, 1, 99);
        test("addEdge updates weight: getWeight(0, 1) is now 99", dg.getWeight(0, 1) == 99);
        test("edgeCount unchanged after weight update", dg.edgeCount() == 4);

        // Test getNeighbors
        ArrayList<Integer> neighbors = dg.getNeighbors(0);
        test("getNeighbors(0) contains 1", neighbors != null && neighbors.contains(1));
        test("getNeighbors(0) contains 2", neighbors != null && neighbors.contains(2));
        test("getNeighbors(0) size is 2", neighbors != null && neighbors.size() == 2);
        test("getNeighbors(99) returns null", dg.getNeighbors(99) == null);

        // Test degrees
        test("outDegree(0) is 2", dg.outDegree(0) == 2);
        test("inDegree(2) is 2", dg.inDegree(2) == 2);
        test("inDegree(0) is 0", dg.inDegree(0) == 0);
        test("outDegree(99) is -1 (not in graph)", dg.outDegree(99) == -1);

        // Test removeEdge
        test("removeEdge(0, 1) returns true", dg.removeEdge(0, 1));
        test("hasEdge(0, 1) is false after removal", !dg.hasEdge(0, 1));
        test("edgeCount is 3 after removal", dg.edgeCount() == 3);
        test("removeEdge(0, 1) returns false (already removed)", !dg.removeEdge(0, 1));

        // Test removeVertex
        dg.removeVertex(2);
        test("vertexCount is 4 after removing vertex 2", dg.vertexCount() == 4);
        test("hasVertex(2) is false after removal", !dg.hasVertex(2));
        test("hasEdge(0, 2) is false after removing vertex 2", !dg.hasEdge(0, 2));
        test("hasEdge(1, 2) is false after removing vertex 2", !dg.hasEdge(1, 2));

        // --- Undirected Graph Tests ---
        Graph ug = new Graph(false);
        test("isDirected() returns false for undirected graph", !ug.isDirected());

        ug.addEdge(0, 1, 5);
        ug.addEdge(1, 2, 8);
        test("undirected: edgeCount is 2", ug.edgeCount() == 2);
        test("undirected: hasEdge(0, 1) is true", ug.hasEdge(0, 1));
        test("undirected: hasEdge(1, 0) is true", ug.hasEdge(1, 0));
        test("undirected: getWeight(1, 0) is 5", ug.getWeight(1, 0) == 5);

        // Update weight on undirected edge
        ug.addEdge(0, 1, 42);
        test("undirected: updated weight in both directions", ug.getWeight(0, 1) == 42 && ug.getWeight(1, 0) == 42);
        test("undirected: edgeCount unchanged after update", ug.edgeCount() == 2);

        test("undirected: outDegree(1) is 2", ug.outDegree(1) == 2);
        test("undirected: inDegree(1) == outDegree(1)", ug.inDegree(1) == ug.outDegree(1));

        ug.removeEdge(0, 1);
        test("undirected: removeEdge removes both directions", !ug.hasEdge(0, 1) && !ug.hasEdge(1, 0));
        test("undirected: edgeCount is 1 after removal", ug.edgeCount() == 1);

        // Test removeVertex on undirected graph
        ug.addEdge(0, 1);
        ug.addEdge(0, 2);
        ug.removeVertex(0);
        test("undirected: removeVertex removes incident edges", !ug.hasEdge(1, 0) && !ug.hasEdge(2, 0));
    }

    // ======================== PART 2 TESTS ========================

    private static void testGraphAlgorithms() {
        section("Part 2: Graph Algorithms [65 pts]");

        // --- BFS Test ---
        Graph g1 = new Graph(false);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);

        ArrayList<Integer> bfsResult = GraphAlgorithms.bfs(g1, 0);
        test("BFS: starts with vertex 0", bfsResult.size() > 0 && bfsResult.get(0) == 0);
        test("BFS: visits all 5 vertices", bfsResult.size() == 5);
        // In an undirected graph with sorted neighbors: 0 -> [1,2] -> [3] -> [4]
        ArrayList<Integer> expectedBfs = new ArrayList<>();
        expectedBfs.add(0); expectedBfs.add(1); expectedBfs.add(2); expectedBfs.add(3); expectedBfs.add(4);
        test("BFS: correct order [0, 1, 2, 3, 4]", bfsResult.equals(expectedBfs));

        ArrayList<Integer> bfsEmpty = GraphAlgorithms.bfs(g1, 99);
        test("BFS: returns empty list for non-existent start vertex", bfsEmpty.isEmpty());

        // --- DFS Test ---
        ArrayList<Integer> dfsResult = GraphAlgorithms.dfs(g1, 0);
        test("DFS: starts with vertex 0", dfsResult.size() > 0 && dfsResult.get(0) == 0);
        test("DFS: visits all 5 vertices", dfsResult.size() == 5);
        // With sorted neighbors pushed in reverse: 0->1->3->2->4 or 0->1->3->4->2
        // Stack-based: push [2,1] -> pop 1 -> push [3,0(visited)] -> pop 3 -> push [4,2,1(visited),0(visited)] -> pop 2(visited? no)...
        // Actually: 0 -> push(2,1) -> pop 1 -> push(3) -> pop 3 -> push(4,2) -> pop 2 (already visited? no) -> pop 4
        // 0, 1, 3, 2, 4
        ArrayList<Integer> expectedDfs = new ArrayList<>();
        expectedDfs.add(0); expectedDfs.add(1); expectedDfs.add(3); expectedDfs.add(2); expectedDfs.add(4);
        test("DFS: correct order [0, 1, 3, 2, 4]", dfsResult.equals(expectedDfs));

        ArrayList<Integer> dfsEmpty = GraphAlgorithms.dfs(g1, 99);
        test("DFS: returns empty list for non-existent start vertex", dfsEmpty.isEmpty());

        // --- Dijkstra Test ---
        Graph g2 = new Graph(true);
        g2.addEdge(0, 1, 4);
        g2.addEdge(0, 2, 1);
        g2.addEdge(2, 1, 2);
        g2.addEdge(1, 3, 1);
        g2.addEdge(2, 3, 5);

        HashMap<Integer, Integer> dist = GraphAlgorithms.dijkstra(g2, 0);
        test("Dijkstra: distance to 0 is 0", dist.containsKey(0) && dist.get(0) == 0);
        test("Dijkstra: distance to 1 is 3 (0->2->1)", dist.containsKey(1) && dist.get(1) == 3);
        test("Dijkstra: distance to 2 is 1", dist.containsKey(2) && dist.get(2) == 1);
        test("Dijkstra: distance to 3 is 4 (0->2->1->3)", dist.containsKey(3) && dist.get(3) == 4);

        // Unreachable vertex
        g2.addVertex(5);
        HashMap<Integer, Integer> dist2 = GraphAlgorithms.dijkstra(g2, 0);
        test("Dijkstra: unreachable vertex 5 not in result", !dist2.containsKey(5));

        HashMap<Integer, Integer> distEmpty = GraphAlgorithms.dijkstra(g2, 99);
        test("Dijkstra: returns empty map for non-existent start", distEmpty.isEmpty());

        // --- hasCycle Tests ---
        // Directed acyclic graph
        Graph dag = new Graph(true);
        dag.addEdge(0, 1);
        dag.addEdge(0, 2);
        dag.addEdge(1, 3);
        dag.addEdge(2, 3);
        test("hasCycle: DAG returns false", !GraphAlgorithms.hasCycle(dag));

        // Directed cyclic graph
        Graph dcg = new Graph(true);
        dcg.addEdge(0, 1);
        dcg.addEdge(1, 2);
        dcg.addEdge(2, 0);
        test("hasCycle: directed cycle returns true", GraphAlgorithms.hasCycle(dcg));

        // Undirected acyclic (tree)
        Graph tree = new Graph(false);
        tree.addEdge(0, 1);
        tree.addEdge(0, 2);
        tree.addEdge(1, 3);
        test("hasCycle: undirected tree returns false", !GraphAlgorithms.hasCycle(tree));

        // Undirected cyclic
        Graph ucg = new Graph(false);
        ucg.addEdge(0, 1);
        ucg.addEdge(1, 2);
        ucg.addEdge(2, 0);
        test("hasCycle: undirected cycle returns true", GraphAlgorithms.hasCycle(ucg));

        // Empty graph
        Graph emptyG = new Graph(true);
        test("hasCycle: empty graph returns false", !GraphAlgorithms.hasCycle(emptyG));

        // --- Topological Sort Tests ---
        ArrayList<Integer> topoResult = GraphAlgorithms.topologicalSort(dag);
        test("topologicalSort: returns non-null for DAG", topoResult != null);
        test("topologicalSort: contains all 4 vertices", topoResult != null && topoResult.size() == 4);
        // Verify ordering: for every edge (u,v), u appears before v
        if (topoResult != null) {
            boolean validOrder = true;
            // Check 0 before 1, 0 before 2, 1 before 3, 2 before 3
            validOrder &= topoResult.indexOf(0) < topoResult.indexOf(1);
            validOrder &= topoResult.indexOf(0) < topoResult.indexOf(2);
            validOrder &= topoResult.indexOf(1) < topoResult.indexOf(3);
            validOrder &= topoResult.indexOf(2) < topoResult.indexOf(3);
            test("topologicalSort: valid ordering (all edges respected)", validOrder);
        }
        // Expected with smallest-first: [0, 1, 2, 3]
        ArrayList<Integer> expectedTopo = new ArrayList<>();
        expectedTopo.add(0); expectedTopo.add(1); expectedTopo.add(2); expectedTopo.add(3);
        test("topologicalSort: deterministic order [0, 1, 2, 3]",
             topoResult != null && topoResult.equals(expectedTopo));

        ArrayList<Integer> topoCyclic = GraphAlgorithms.topologicalSort(dcg);
        test("topologicalSort: returns null for cyclic graph", topoCyclic == null);

        ArrayList<Integer> topoUndirected = GraphAlgorithms.topologicalSort(tree);
        test("topologicalSort: returns null for undirected graph", topoUndirected == null);
    }

    // ======================== BONUS TESTS ========================

    private static void testSocialNetwork() {
        section("Bonus: Social Network Analysis [20 pts]");

        // --- countComponents Tests ---
        Graph g = new Graph(false);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addVertex(5); // isolated vertex
        test("countComponents: 3 components (0-1-2, 3-4, 5)", SocialNetwork.countComponents(g) == 3);

        Graph single = new Graph(false);
        single.addVertex(0);
        test("countComponents: single isolated vertex = 1 component", SocialNetwork.countComponents(single) == 1);

        Graph empty = new Graph(false);
        test("countComponents: empty graph = 0 components", SocialNetwork.countComponents(empty) == 0);

        // Directed graph treated as undirected for components
        Graph dg = new Graph(true);
        dg.addEdge(0, 1);
        dg.addEdge(2, 1);
        test("countComponents: directed edges treated as undirected (1 component)", SocialNetwork.countComponents(dg) == 1);

        // --- mostConnected Tests ---
        Graph g2 = new Graph(false);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(0, 3);
        g2.addEdge(1, 2);
        test("mostConnected: vertex 0 has degree 3 (highest)", SocialNetwork.mostConnected(g2) == 0);

        // Tie-breaking: smallest vertex ID
        Graph g3 = new Graph(false);
        g3.addEdge(1, 2);
        g3.addEdge(3, 4);
        test("mostConnected: tie between 1,2,3,4 (degree 1 each) returns 1", SocialNetwork.mostConnected(g3) == 1);

        Graph emptyG = new Graph(false);
        test("mostConnected: empty graph returns -1", SocialNetwork.mostConnected(emptyG) == -1);

        // --- degreesOfSeparation Tests ---
        Graph g4 = new Graph(false);
        g4.addEdge(0, 1);
        g4.addEdge(1, 2);
        g4.addEdge(2, 3);
        g4.addEdge(3, 4);
        test("degreesOfSeparation(0, 4) is 4", SocialNetwork.degreesOfSeparation(g4, 0, 4) == 4);
        test("degreesOfSeparation(0, 2) is 2", SocialNetwork.degreesOfSeparation(g4, 0, 2) == 2);
        test("degreesOfSeparation(0, 0) is 0 (same person)", SocialNetwork.degreesOfSeparation(g4, 0, 0) == 0);

        g4.addVertex(5); // isolated
        test("degreesOfSeparation(0, 5) is -1 (unreachable)", SocialNetwork.degreesOfSeparation(g4, 0, 5) == -1);
        test("degreesOfSeparation(0, 99) is -1 (not in graph)", SocialNetwork.degreesOfSeparation(g4, 0, 99) == -1);

        // Directed graph: treat as undirected
        Graph dg2 = new Graph(true);
        dg2.addEdge(0, 1);
        dg2.addEdge(2, 1);
        test("degreesOfSeparation: directed edges treated as undirected",
             SocialNetwork.degreesOfSeparation(dg2, 0, 2) == 2);
    }

    // ======================== MAIN ========================

    private static boolean checkStudentInfo() {
        System.out.println("=== Student Information ===");

        boolean valid = true;

        // Check Graph info
        if (Graph.STUDENT_NAME.isEmpty() ||
            Graph.STUDENT_ID.isEmpty() ||
            Graph.STUDENT_KU_EMAIL.isEmpty()) {
            System.out.println("  [WARNING] Student info not filled in Graph.java!");
            valid = false;
        }

        // Check GraphAlgorithms info
        if (GraphAlgorithms.STUDENT_NAME.isEmpty() ||
            GraphAlgorithms.STUDENT_ID.isEmpty() ||
            GraphAlgorithms.STUDENT_KU_EMAIL.isEmpty()) {
            System.out.println("  [WARNING] Student info not filled in GraphAlgorithms.java!");
            valid = false;
        }

        // Check SocialNetwork info
        if (SocialNetwork.STUDENT_NAME.isEmpty() ||
            SocialNetwork.STUDENT_ID.isEmpty() ||
            SocialNetwork.STUDENT_KU_EMAIL.isEmpty()) {
            System.out.println("  [WARNING] Student info not filled in SocialNetwork.java!");
            valid = false;
        }

        if (valid) {
            System.out.println("  Name:  " + Graph.STUDENT_NAME);
            System.out.println("  ID:    " + Graph.STUDENT_ID);
            System.out.println("  Email: " + Graph.STUDENT_KU_EMAIL);

            // Check consistency across files
            if (!Graph.STUDENT_ID.equals(GraphAlgorithms.STUDENT_ID) ||
                !Graph.STUDENT_ID.equals(SocialNetwork.STUDENT_ID)) {
                System.out.println("  [WARNING] Student ID mismatch across files!");
            }
        } else {
            System.out.println();
            System.out.println("  *** YOU MUST FILL IN YOUR STUDENT INFO IN ALL FILES ***");
            System.out.println("  *** BEFORE SUBMITTING. Submissions with missing info ***");
            System.out.println("  *** may not be graded.                                ***");
        }

        return valid;
    }

    public static void main(String[] args) {
        System.out.println("COMP202 Assignment 3 - Student Autograder");
        System.out.println("=========================================");

        boolean infoValid = checkStudentInfo();

        testGraph();
        testGraphAlgorithms();
        testSocialNetwork();

        section("SUMMARY");
        System.out.println("  Passed: " + passed);
        System.out.println("  Failed: " + failed);
        System.out.println("  Total:  " + (passed + failed));
        System.out.println();
        if (!infoValid) {
            System.out.println("  *** REMINDER: Fill in your student info in ALL files! ***");
            System.out.println();
        }
        if (failed == 0) {
            System.out.println("  All tests passed! (But remember: the extended");
            System.out.println("  autograder has additional tests.)");
        } else {
            System.out.println("  Some tests failed. Review your implementation.");
        }
    }
}
