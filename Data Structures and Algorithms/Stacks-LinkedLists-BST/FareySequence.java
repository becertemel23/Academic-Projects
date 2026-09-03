package ds;

/**
 * Part 2:
 */

public class FareySequence {

    private static class FractionNode {
        int numerator;
        int denominator;
        FractionNode next;

        FractionNode(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    private FractionNode head;
    private int currentLevel = 1;

    public FareySequence() {
        // Initialize level 1 sequence: 0/1, 1/1
        head = new FractionNode(0, 1);
        head.next = new FractionNode(1, 1);
    }

    // Extend list to next level
    public void extend() {
        // TODO: Implement extend
    }

    // Extend list to a specific level
    public void level(int n) {
        // TODO: Implement level extension
    }

    // Shrink list by one level
    public void shrink() {
        // TODO: Implement shrink
    }

    // print
    public void printSequence() {
        //TODO: Print the farey sequence
    }
    
}
