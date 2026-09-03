/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 2 - Part 2: Hash Table with Separate Chaining
 *
 * In this part, you will implement a hash table that maps String keys to
 * int values. Collisions are resolved using separate chaining, where each
 * bucket is a singly linked list that you build yourself.
 *
 * CONSTRAINTS:
 * - You may NOT use any classes from java.util.
 * - You may use Java arrays.
 * - You may ONLY modify the sections marked with // TODO.
 * - Do NOT change method signatures, class name, or provided code.
 *
 * Total: 65 points
 */
public class HashTable {

    // ========================== STUDENT INFO ==========================
    // Fill in your information below. This is MANDATORY and will be used
    // for grading identification. Do NOT leave these empty.
    public static final String STUDENT_NAME = "";       // TODO: Enter your full name (e.g., "Ahmet Yilmaz")
    public static final String STUDENT_ID = "";         // TODO: Enter your student ID number (e.g., "0012345")
    public static final String STUDENT_KU_EMAIL = "";   // TODO: Enter your KU email (e.g., "ayilmaz20@ku.edu.tr")
    // ==================================================================

    /**
     * A node in the singly linked list used for separate chaining.
     * DO NOT MODIFY THIS CLASS.
     */
    public static class Node {
        public String key;
        public int value;
        public Node next;

        public Node(String key, int value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node[] buckets;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 8;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    /**
     * Constructs an empty hash table with default capacity.
     * DO NOT MODIFY.
     */
    public HashTable() {
        capacity = DEFAULT_CAPACITY;
        buckets = new Node[capacity];
        size = 0;
    }

    /**
     * Returns the number of key-value pairs in the hash table.
     * DO NOT MODIFY.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the current capacity (number of buckets) of the hash table.
     * DO NOT MODIFY.
     */
    public int capacity() {
        return capacity;
    }

    // ========================== CORE OPERATIONS ==========================

    /**
     * Computes the hash (bucket index) for the given key.
     * [5 points]
     *
     * Use the polynomial rolling hash function:
     *   h(s) = (s[0] * 31^(n-1) + s[1] * 31^(n-2) + ... + s[n-1]) % capacity
     *
     * where s[i] is the character value (cast to int) at position i, and n is the
     * length of the string.
     *
     * IMPORTANT: The result must be non-negative. Java's % operator can return
     * negative values for negative dividends. To fix this:
     *   index = ((hash % capacity) + capacity) % capacity
     *
     * Pseudocode:
     *   hash = 0
     *   for each character c in key:
     *       hash = hash * 31 + (int) c
     *   return ((hash % capacity) + capacity) % capacity
     *
     * Note: Use long arithmetic to avoid integer overflow during computation.
     *
     * @param key the key to hash
     * @return the bucket index (0 to capacity - 1)
     */
    public int hash(String key) {
        // TODO
        return 0;
    }

    /**
     * Inserts or updates a key-value pair in the hash table.
     * [8 points]
     *
     * If the key already exists, update its value. If it does not exist,
     * insert a new node at the beginning of the appropriate chain.
     * After inserting a NEW key, check the load factor and resize if needed.
     *
     * Pseudocode:
     *   1. Compute the bucket index using hash(key).
     *   2. Walk the chain at that bucket:
     *      - If a node with the matching key is found, update its value and return.
     *   3. If not found, create a new Node and insert it at the head of the chain.
     *   4. Increment size.
     *   5. If getLoadFactor() > LOAD_FACTOR_THRESHOLD, call resize().
     *
     * @param key the key
     * @param value the value to associate with the key
     */
    public void put(String key, int value) {
        // TODO
    }

    /**
     * Returns the value associated with the given key.
     * [5 points]
     *
     * Pseudocode:
     *   1. Compute the bucket index using hash(key).
     *   2. Walk the chain at that bucket.
     *   3. If a node with the matching key is found, return its value.
     *   4. If not found, return -1.
     *
     * @param key the key to search for
     * @return the associated value, or -1 if the key is not found
     */
    public int get(String key) {
        // TODO
        return -1;
    }

    /**
     * Removes the entry with the given key from the hash table.
     * [7 points]
     *
     * You must correctly re-link the chain after removing a node.
     * Consider the cases: removing the head of the chain vs. removing
     * a node in the middle or end of the chain.
     *
     * Pseudocode:
     *   1. Compute the bucket index using hash(key).
     *   2. If the chain is empty, return -1.
     *   3. If the head node matches the key:
     *      - Save its value, set buckets[index] = head.next, decrement size, return value.
     *   4. Otherwise, walk the chain with a prev pointer:
     *      - If current.key matches, set prev.next = current.next, decrement size, return value.
     *   5. If not found, return -1.
     *
     * @param key the key to remove
     * @return the value that was associated with the key, or -1 if not found
     */
    public int remove(String key) {
        // TODO
        return -1;
    }

    /**
     * Doubles the capacity and rehashes all entries.
     * [10 points]
     *
     * This method is called automatically when the load factor exceeds 0.75.
     *
     * Pseudocode:
     *   1. Save the old buckets array.
     *   2. Double the capacity.
     *   3. Create a new buckets array with the new capacity.
     *   4. Reset size to 0.
     *   5. For each bucket in the old array:
     *      - Walk the chain and call put(key, value) for each node.
     *      (put() will rehash using the new capacity and increment size.)
     *
     * Note: Calling put() inside resize() is the simplest approach. Since put()
     * checks the load factor, but the new table will be half-full at most, it
     * will NOT trigger another resize (no infinite recursion).
     */
    public void resize() {
        // TODO
    }

    /**
     * Returns the current load factor: size / capacity.
     * [2 points]
     *
     * @return the load factor as a double
     */
    public double getLoadFactor() {
        // TODO
        return 0.0;
    }

    /**
     * Returns true if the given key exists in the hash table.
     * [3 points]
     *
     * Hint: You can reuse logic from get().
     *
     * @param key the key to check
     * @return true if the key is in the table, false otherwise
     */
    public boolean containsKey(String key) {
        // TODO
        return false;
    }

    // ======================== ANALYTICAL OPERATIONS ========================

    /**
     * Returns an array of all keys in the hash table.
     * [5 points]
     *
     * The order of keys in the returned array does not matter.
     *
     * Pseudocode:
     *   1. Create a String array of length 'size'.
     *   2. Iterate over all buckets.
     *   3. For each node in each chain, add its key to the array.
     *   4. Return the array.
     *
     * @return a String array containing all keys
     */
    public String[] keys() {
        // TODO
        return new String[0];
    }

    /**
     * Returns an array of all values in the hash table.
     * [3 points]
     *
     * The order of values in the returned array does not matter,
     * but should correspond to the same traversal order as keys().
     *
     * @return an int array containing all values
     */
    public int[] values() {
        // TODO
        return new int[0];
    }

    /**
     * Returns the key that has the highest associated value.
     * [7 points]
     *
     * If multiple keys share the same maximum value, return the one that
     * comes first alphabetically (using String's compareTo method).
     *
     * Pseudocode:
     *   1. Initialize maxKey = null, maxVal = smallest possible integer.
     *   2. Iterate over all buckets and all nodes.
     *   3. For each node:
     *      - If node.value > maxVal, update maxKey and maxVal.
     *      - If node.value == maxVal and node.key comes before maxKey
     *        alphabetically (node.key.compareTo(maxKey) < 0), update maxKey.
     *   4. Return maxKey (or null if the table is empty).
     *
     * @return the key with the highest value, or null if empty
     */
    public String maxValueKey() {
        // TODO
        return null;
    }

    /**
     * Returns an alphabetically sorted array of all keys whose associated
     * value is strictly greater than the given threshold.
     * [5 points]
     *
     * Since you cannot use java.util.Arrays.sort(), you must sort the result
     * yourself. Insertion sort is a simple and acceptable choice.
     *
     * Pseudocode:
     *   1. First pass: count how many keys qualify (value > threshold).
     *   2. Create a String array of that count.
     *   3. Second pass: fill in the qualifying keys.
     *   4. Sort the array alphabetically using insertion sort:
     *      for i from 1 to length-1:
     *          temp = arr[i]
     *          j = i - 1
     *          while j >= 0 and arr[j].compareTo(temp) > 0:
     *              arr[j+1] = arr[j]
     *              j = j - 1
     *          arr[j+1] = temp
     *   5. Return the sorted array.
     *
     * @param threshold the value threshold
     * @return sorted array of keys with value > threshold
     */
    public String[] keysWithValueAbove(int threshold) {
        // TODO
        return new String[0];
    }

    /**
     * Merges all entries from another hash table into this one.
     * [5 points]
     *
     * For each key in the other table:
     * - If the key does NOT exist in this table, insert it with its value.
     * - If the key DOES exist in this table, add the other table's value
     *   to the existing value (sum them).
     *
     * Pseudocode:
     *   1. Get all keys from the other table (use other.keys()).
     *   2. For each key:
     *      - Get the value from the other table.
     *      - If this table contains the key:
     *            this.put(key, this.get(key) + otherValue)
     *      - Else:
     *            this.put(key, otherValue)
     *
     * @param other the hash table to merge into this one
     */
    public void merge(HashTable other) {
        // TODO
    }

    /**
     * Returns a String representation of the hash table for debugging.
     * DO NOT MODIFY.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            sb.append("Bucket ").append(i).append(": ");
            Node current = buckets[i];
            while (current != null) {
                sb.append("[").append(current.key).append("=").append(current.value).append("]");
                if (current.next != null) sb.append(" -> ");
                current = current.next;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
