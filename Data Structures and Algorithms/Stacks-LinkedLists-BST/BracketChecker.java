package ds;

/**
 * Part 1: Unmatched Brackets
    * Implement a bracket checker that detects and corrects bracket errors in a given string using your own stack implementation.
    * Supported brackets: () [] {}
    * Must run in O(n) time.
 */
public class BracketChecker {

    private static class Node {
        char character;
        Node next;
        Node(char character) {
            this.character = character;
        }
    }

    // Stack implementation
    private static class Stack {
        private Node top;

        public void push(char c) {
            // TODO: Implement push
        }

        public char pop() {
            // TODO: Implement pop
            return c;
        }

        public char peek() {
            // TODO: Implement peek
            return c;
        }

        public boolean isEmpty() {
            // TODO: Implement isEmpty
            return bool;
        }
    }

    // Function to check and fix brackets
    public static String fixBrackets(String input) {
        // TODO: Implement bracket fixing logic using your Stack
        return code; 
    }

}
