/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 2 - Student Autograder
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

    private static void testPriorityQueue() {
        section("Part 1: Min-Heap Priority Queue [35 pts]");

        // Test insert and peek
        PriorityQueue pq = new PriorityQueue();
        pq.insert(5, "five");
        pq.insert(3, "three");
        pq.insert(7, "seven");
        pq.insert(1, "one");

        test("peek returns minimum key entry",
             pq.peek() != null && pq.peek().key == 1 && pq.peek().value.equals("one"));

        test("size is 4 after 4 inserts", pq.size() == 4);

        // Test extractMin order
        PriorityQueue.Entry e1 = pq.extractMin();
        test("extractMin #1: key=1 (\"one\")",
             e1 != null && e1.key == 1 && e1.value.equals("one"));

        PriorityQueue.Entry e2 = pq.extractMin();
        test("extractMin #2: key=3 (\"three\")",
             e2 != null && e2.key == 3 && e2.value.equals("three"));

        PriorityQueue.Entry e3 = pq.extractMin();
        test("extractMin #3: key=5 (\"five\")",
             e3 != null && e3.key == 5 && e3.value.equals("five"));

        PriorityQueue.Entry e4 = pq.extractMin();
        test("extractMin #4: key=7 (\"seven\")",
             e4 != null && e4.key == 7 && e4.value.equals("seven"));

        test("extractMin on empty returns null", pq.extractMin() == null);
        test("peek on empty returns null", pq.peek() == null);

        // Test FIFO tiebreaker
        PriorityQueue pq2 = new PriorityQueue();
        pq2.insert(2, "first");
        pq2.insert(2, "second");
        pq2.insert(2, "third");

        PriorityQueue.Entry t1 = pq2.extractMin();
        PriorityQueue.Entry t2 = pq2.extractMin();
        PriorityQueue.Entry t3 = pq2.extractMin();
        test("FIFO tiebreaker: same key extracted in insertion order",
             t1.value.equals("first") && t2.value.equals("second") && t3.value.equals("third"));

        // Test changePriority
        PriorityQueue pq3 = new PriorityQueue();
        pq3.insert(5, "A");
        pq3.insert(3, "B");
        pq3.insert(8, "C");
        pq3.insert(1, "D");
        // Heap should have D(1) at top. Change D's key from 1 to 10 (bubble down)
        pq3.changePriority(0, 10);
        test("changePriority: after increasing root key, new min is B(3)",
             pq3.peek() != null && pq3.peek().key == 3 && pq3.peek().value.equals("B"));

        // Test buildHeap
        PriorityQueue pq4 = new PriorityQueue();
        PriorityQueue.Entry[] entries = new PriorityQueue.Entry[5];
        entries[0] = new PriorityQueue.Entry(9, "nine", 0);
        entries[1] = new PriorityQueue.Entry(4, "four", 1);
        entries[2] = new PriorityQueue.Entry(7, "seven", 2);
        entries[3] = new PriorityQueue.Entry(2, "two", 3);
        entries[4] = new PriorityQueue.Entry(6, "six", 4);
        pq4.buildHeap(entries);

        test("buildHeap: size is 5", pq4.size() == 5);
        test("buildHeap: min is key=2",
             pq4.peek() != null && pq4.peek().key == 2);

        // Extract all and verify sorted order
        int[] expected = {2, 4, 6, 7, 9};
        boolean sorted = true;
        for (int i = 0; i < 5; i++) {
            PriorityQueue.Entry ex = pq4.extractMin();
            if (ex == null || ex.key != expected[i]) {
                sorted = false;
                break;
            }
        }
        test("buildHeap: extracting all gives sorted order [2,4,6,7,9]", sorted);
    }

    // ======================== PART 2 TESTS ========================

    private static void testHashTable() {
        section("Part 2: Hash Table with Separate Chaining [65 pts]");

        HashTable ht = new HashTable();

        // Test put and get
        ht.put("alpha", 10);
        ht.put("beta", 20);
        ht.put("gamma", 30);

        test("get(\"alpha\") returns 10", ht.get("alpha") == 10);
        test("get(\"beta\") returns 20", ht.get("beta") == 20);
        test("get(\"gamma\") returns 30", ht.get("gamma") == 30);
        test("get(\"delta\") returns -1 (not found)", ht.get("delta") == -1);
        test("size is 3", ht.size() == 3);

        // Test put update
        ht.put("alpha", 99);
        test("put update: get(\"alpha\") returns 99", ht.get("alpha") == 99);
        test("size still 3 after update", ht.size() == 3);

        // Test containsKey
        test("containsKey(\"beta\") is true", ht.containsKey("beta"));
        test("containsKey(\"delta\") is false", !ht.containsKey("delta"));

        // Test remove
        int removedVal = ht.remove("beta");
        test("remove(\"beta\") returns 20", removedVal == 20);
        test("get(\"beta\") returns -1 after removal", ht.get("beta") == -1);
        test("size is 2 after removal", ht.size() == 2);
        test("remove(\"nonexistent\") returns -1", ht.remove("nonexistent") == -1);

        // Test getLoadFactor
        HashTable ht2 = new HashTable();
        ht2.put("a", 1);
        ht2.put("b", 2);
        double lf = ht2.getLoadFactor();
        test("load factor with 2 entries, capacity 8 is 0.25",
             Math.abs(lf - 0.25) < 0.001);

        // Test resize
        HashTable ht3 = new HashTable();
        for (int i = 0; i < 7; i++) {
            ht3.put("key" + i, i * 10);
        }
        test("capacity doubled after 7 inserts (was 8, now 16)",
             ht3.capacity() == 16);
        // Verify all entries survive resize
        boolean allPresent = true;
        for (int i = 0; i < 7; i++) {
            if (ht3.get("key" + i) != i * 10) {
                allPresent = false;
                break;
            }
        }
        test("all entries accessible after resize", allPresent);

        // Test keys()
        HashTable ht4 = new HashTable();
        ht4.put("x", 1);
        ht4.put("y", 2);
        ht4.put("z", 3);
        String[] keys = ht4.keys();
        test("keys() returns array of length 3", keys.length == 3);
        boolean hasX = false, hasY = false, hasZ = false;
        for (String k : keys) {
            if ("x".equals(k)) hasX = true;
            if ("y".equals(k)) hasY = true;
            if ("z".equals(k)) hasZ = true;
        }
        test("keys() contains all inserted keys", hasX && hasY && hasZ);

        // Test values()
        int[] vals = ht4.values();
        test("values() returns array of length 3", vals.length == 3);

        // Test maxValueKey
        test("maxValueKey returns \"z\" (value 3)", "z".equals(ht4.maxValueKey()));

        // Test keysWithValueAbove
        HashTable ht5 = new HashTable();
        ht5.put("charlie", 50);
        ht5.put("alice", 80);
        ht5.put("bob", 70);
        ht5.put("dave", 30);
        String[] above60 = ht5.keysWithValueAbove(60);
        test("keysWithValueAbove(60): length is 2", above60.length == 2);
        test("keysWithValueAbove(60): sorted alphabetically [\"alice\", \"bob\"]",
             above60.length == 2 && "alice".equals(above60[0]) && "bob".equals(above60[1]));

        // Test merge
        HashTable htA = new HashTable();
        htA.put("a", 10);
        htA.put("b", 20);
        HashTable htB = new HashTable();
        htB.put("b", 5);
        htB.put("c", 30);
        htA.merge(htB);
        test("merge: get(\"a\") = 10 (unchanged)", htA.get("a") == 10);
        test("merge: get(\"b\") = 25 (summed)", htA.get("b") == 25);
        test("merge: get(\"c\") = 30 (new key)", htA.get("c") == 30);
        test("merge: size is 3", htA.size() == 3);
    }

    // ======================== BONUS TESTS ========================

    private static void testHashCrypto() {
        section("Bonus: Hash-Based Cryptography [20 pts]");

        // Test hashMessage
        int h1 = HashCrypto.hashMessage("hello", 10007);
        int h2 = HashCrypto.hashMessage("hello", 10007);
        test("hashMessage is deterministic", h1 == h2);
        test("hashMessage returns non-negative value", h1 >= 0);
        test("hashMessage returns value < modulus", h1 < 10007);

        int h3 = HashCrypto.hashMessage("world", 10007);
        test("hashMessage: different strings likely produce different hashes", h1 != h3);

        // Test generateHMAC and verifyHMAC
        int hmac = HashCrypto.generateHMAC("test message", "secretKey");
        test("generateHMAC returns non-negative value", hmac >= 0);
        test("verifyHMAC returns true for correct code",
             HashCrypto.verifyHMAC("test message", "secretKey", hmac));
        test("verifyHMAC returns false for wrong code",
             !HashCrypto.verifyHMAC("test message", "secretKey", hmac + 1));
        test("verifyHMAC returns false for wrong message",
             !HashCrypto.verifyHMAC("wrong message", "secretKey", hmac));

        // Test detectTampering
        String key = "myKey";
        String[] messages = {"Deploy Unit 5", "Evacuate Zone 3", "All Clear"};
        int[] codes = new int[3];
        codes[0] = HashCrypto.generateHMAC(messages[0], key);
        codes[1] = 9999; // tampered
        codes[2] = HashCrypto.generateHMAC(messages[2], key);

        String[] tampered = HashCrypto.detectTampering(messages, codes, key);
        test("detectTampering: finds 1 tampered message", tampered.length == 1);
        test("detectTampering: tampered message is \"Evacuate Zone 3\"",
             tampered.length == 1 && "Evacuate Zone 3".equals(tampered[0]));
    }

    // ======================== MAIN ========================

    private static boolean checkStudentInfo() {
        System.out.println("=== Student Information ===");

        boolean valid = true;

        // Check PriorityQueue info
        if (PriorityQueue.STUDENT_NAME.isEmpty() ||
            PriorityQueue.STUDENT_ID.isEmpty() ||
            PriorityQueue.STUDENT_KU_EMAIL.isEmpty()) {
            System.out.println("  [WARNING] Student info not filled in PriorityQueue.java!");
            valid = false;
        }

        // Check HashTable info
        if (HashTable.STUDENT_NAME.isEmpty() ||
            HashTable.STUDENT_ID.isEmpty() ||
            HashTable.STUDENT_KU_EMAIL.isEmpty()) {
            System.out.println("  [WARNING] Student info not filled in HashTable.java!");
            valid = false;
        }

        // Check HashCrypto info
        if (HashCrypto.STUDENT_NAME.isEmpty() ||
            HashCrypto.STUDENT_ID.isEmpty() ||
            HashCrypto.STUDENT_KU_EMAIL.isEmpty()) {
            System.out.println("  [WARNING] Student info not filled in HashCrypto.java!");
            valid = false;
        }

        if (valid) {
            System.out.println("  Name:  " + PriorityQueue.STUDENT_NAME);
            System.out.println("  ID:    " + PriorityQueue.STUDENT_ID);
            System.out.println("  Email: " + PriorityQueue.STUDENT_KU_EMAIL);

            // Check consistency across files
            if (!PriorityQueue.STUDENT_ID.equals(HashTable.STUDENT_ID) ||
                !PriorityQueue.STUDENT_ID.equals(HashCrypto.STUDENT_ID)) {
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
        System.out.println("COMP202 Assignment 2 - Student Autograder");
        System.out.println("=========================================");

        boolean infoValid = checkStudentInfo();

        testPriorityQueue();
        testHashTable();
        testHashCrypto();

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
