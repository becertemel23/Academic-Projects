import ds.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("===== Part 1: Bracket Checker =====");
        BracketChecker.fixBrackets("Foo(bar[i];");

        // ---------------------------------------------------------------------------------------------
        System.out.println("\n===== Part 2: Farey Sequence =====");
        FareySequence fs = new FareySequence();
        fs.printSequence();
        
        fs.level(3);
        fs.printSequence();

        fs.extend();
        fs.printSequence();                  
        
        fs.shrink();
        fs.printSequence(); 
        
        // ---------------------------------------------------------------------------------------------
        System.out.println("\n===== Part 3: Task Manager =====");
        TaskManager tm = new TaskManager();
        tm.addTask(3, "Write report");
        tm.addTask(1, "Send email");
        tm.addTask(2, "Fix bug");

        tm.processNext();  // Task 3
        tm.processNext();  // Task 1
        tm.processNext();  // Task 2

        System.out.println("Completed tasks (sorted by ID):");
        tm.traverse();     // Should print IDs 1, 2, 3

        tm.undoTask(2);
        System.out.println("After undo of task 2:");
        tm.traverse();     // Should print IDs 1, 3
    }
}
