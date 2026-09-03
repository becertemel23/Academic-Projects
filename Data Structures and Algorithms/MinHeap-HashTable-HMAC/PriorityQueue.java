/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 2 - Part 1: Min-Heap Priority Queue
 *
 * In this part, you will implement an array-based min-heap priority queue.
 * The heap stores Entry objects, each containing an integer key (priority)
 * and a String value. Lower key values indicate higher priority.
 *
 * When two entries have the same key, the one that was inserted first
 * (lower insertionOrder) should come first (FIFO tiebreaker).
 *
 * CONSTRAINTS:
 * - You may NOT use any classes from java.util.
 * - You may use Java arrays.
 * - You may ONLY modify the sections marked with // TODO.
 * - Do NOT change method signatures, class name, or provided code.
 *
 * Total: 35 points
 */
public class PriorityQueue {

    // ========================== STUDENT INFO ==========================
    // Fill in your information below. This is MANDATORY and will be used
    // for grading identification. Do NOT leave these empty.
    public static final String STUDENT_NAME = "";       // TODO: Enter your full name (e.g., "Ahmet Yilmaz")
    public static final String STUDENT_ID = "";         // TODO: Enter your student ID number (e.g., "0012345")
    public static final String STUDENT_KU_EMAIL = "";   // TODO: Enter your KU email (e.g., "ayilmaz20@ku.edu.tr")
    // ==================================================================

    /**
     * Represents an entry in the priority queue.
     * Each entry has an integer key (priority), a String value,
     * and an insertionOrder field used for FIFO tiebreaking.
     * DO NOT MODIFY THIS CLASS.
     */
    public static class Entry {
        public int key;
        public String value;
        public long insertionOrder;

        public Entry(int key, String value, long insertionOrder) {
            this.key = key;
            this.value = value;
            this.insertionOrder = insertionOrder;
        }

        @Override
        public String toString() {
            return "(" + key + ", \"" + value + "\")";
        }
    }

    private Entry[] heap;
    private int size;
    private long insertionCounter;
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Constructs an empty priority queue with default capacity.
     * DO NOT MODIFY.
     */
    public PriorityQueue() {
        heap = new Entry[DEFAULT_CAPACITY];
        size = 0;
        insertionCounter = 0;
    }

    /**
     * Returns the number of entries in the priority queue.
     * DO NOT MODIFY.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the priority queue is empty.
     * DO NOT MODIFY.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Doubles the capacity of the backing array when it is full.
     * DO NOT MODIFY.
     */
    private void resizeIfNeeded() {
        if (size == heap.length) {
            Entry[] newHeap = new Entry[heap.length * 2];
            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
    }

    // ========================== HELPER METHODS ==========================
    // Implement the body of each helper method below.
    // These are essential for your heap operations.

    /**
     * Returns the index of the parent of the element at index i.
     *
     * Hint: parent of index i is at (i - 1) / 2
     *
     * @param i the index of the element
     * @return the index of the parent
     */
    private int parent(int i) {
        // TODO (not graded, but required for your heap to work)
        return 0;
    }

    /**
     * Returns the index of the left child of the element at index i.
     *
     * Hint: left child of index i is at 2 * i + 1
     *
     * @param i the index of the element
     * @return the index of the left child
     */
    private int leftChild(int i) {
        // TODO (not graded, but required for your heap to work)
        return 0;
    }

    /**
     * Returns the index of the right child of the element at index i.
     *
     * Hint: right child of index i is at 2 * i + 2
     *
     * @param i the index of the element
     * @return the index of the right child
     */
    private int rightChild(int i) {
        // TODO (not graded, but required for your heap to work)
        return 0;
    }

    /**
     * Swaps the entries at indices i and j in the heap array.
     *
     * @param i the first index
     * @param j the second index
     */
    private void swap(int i, int j) {
        // TODO (not graded, but required for your heap to work)
    }

    /**
     * Compares two entries for ordering.
     *
     * Returns a negative value if a should come before b,
     * a positive value if b should come before a,
     * and 0 if they are equal in ordering.
     *
     * Comparison rules:
     * 1. Compare by key first (lower key = higher priority).
     * 2. If keys are equal, compare by insertionOrder (lower = earlier = higher priority).
     *
     * Pseudocode:
     *   if a.key != b.key:
     *       return a.key - b.key
     *   else:
     *       return compare a.insertionOrder with b.insertionOrder
     *
     * Note: Be careful with long subtraction overflow. Consider using Long.compare()
     * or explicit comparisons for the insertionOrder part.
     *
     * @param a the first entry
     * @param b the second entry
     * @return negative if a < b, positive if a > b, 0 if equal
     */
    private int compare(Entry a, Entry b) {
        // TODO (not graded, but required for your heap to work)
        return 0;
    }

    // ========================== MAIN METHODS ==========================

    /**
     * Inserts a new entry with the given key and value into the priority queue.
     * [8 points]
     *
     * Pseudocode:
     *   1. Resize the array if needed (call resizeIfNeeded()).
     *   2. Create a new Entry with the given key, value, and current insertionCounter.
     *   3. Increment the insertionCounter.
     *   4. Place the new entry at index 'size' (the end of the heap).
     *   5. Increment size.
     *   6. Bubble up: while the new entry is not at the root and is smaller than
     *      its parent (use compare()), swap it with its parent and move up.
     *
     * Time complexity: O(log n)
     *
     * @param key the priority of the new entry (lower = higher priority)
     * @param value the value associated with this entry
     */
    public void insert(int key, String value) {
        // TODO
    }

    /**
     * Removes and returns the entry with the minimum key (highest priority).
     * [8 points]
     *
     * Pseudocode:
     *   1. If the heap is empty, return null.
     *   2. Save the entry at index 0 (the minimum).
     *   3. Move the last entry (at index size-1) to index 0.
     *   4. Decrement size.
     *   5. Set heap[size] = null (clean up the old last position).
     *   6. If the heap is not empty, bubble down from index 0:
     *      a. Find the smallest child (compare left and right children).
     *      b. If the current entry is larger than the smallest child, swap and repeat.
     *      c. Stop when the heap property is restored or a leaf is reached.
     *   7. Return the saved minimum entry.
     *
     * Time complexity: O(log n)
     *
     * @return the entry with the smallest key, or null if the queue is empty
     */
    public Entry extractMin() {
        // TODO
        return null;
    }

    /**
     * Returns (without removing) the entry with the minimum key.
     * [2 points]
     *
     * @return the entry with the smallest key, or null if the queue is empty
     */
    public Entry peek() {
        // TODO
        return null;
    }

    /**
     * Changes the priority key of the entry at the given index.
     * [7 points]
     *
     * After changing the key, the entry must be repositioned to maintain the
     * heap property. It may need to bubble up (if the new key is smaller than
     * the parent) or bubble down (if the new key is larger than a child).
     *
     * Pseudocode:
     *   1. If the index is out of bounds (< 0 or >= size), do nothing and return.
     *   2. Save the old key.
     *   3. Set the entry's key to the new key.
     *   4. If the new key is smaller than the old key:
     *        Bubble up from the given index (same logic as in insert).
     *   5. If the new key is larger than the old key:
     *        Bubble down from the given index (same logic as in extractMin).
     *
     * Time complexity: O(log n)
     *
     * @param index the index of the entry to modify
     * @param newKey the new priority key
     */
    public void changePriority(int index, int newKey) {
        // TODO
    }

    /**
     * Builds a min-heap from the given array of entries using Floyd's algorithm
     * (bottom-up heap construction).
     * [10 points]
     *
     * This method replaces the current contents of the priority queue with
     * the entries from the given array, then arranges them into a valid min-heap.
     *
     * Pseudocode:
     *   1. Create a new backing array large enough to hold all entries.
     *   2. Copy all entries from the input array into the backing array.
     *   3. Set size to the number of entries.
     *   4. Starting from the last non-leaf node (index size/2 - 1) down to 0:
     *        Bubble down from that index.
     *      (This is Floyd's algorithm and runs in O(n) time.)
     *
     * Note: Update insertionCounter to be greater than the maximum
     * insertionOrder in the input array, so future inserts get unique orders.
     *
     * Time complexity: O(n) where n is the length of the entries array
     *
     * @param entries the array of entries to heapify
     */
    public void buildHeap(Entry[] entries) {
        // TODO
    }

    /**
     * Returns a String representation of the heap for debugging purposes.
     * DO NOT MODIFY.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns the entry at the given index. For testing purposes only.
     * DO NOT MODIFY.
     */
    public Entry getEntry(int index) {
        if (index < 0 || index >= size) return null;
        return heap[index];
    }
}
