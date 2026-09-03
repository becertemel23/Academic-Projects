package ds;

/**
 * Part 3:
 */

public class TaskManager {

    // Task node
    private static class Task {
        int taskID;
        String taskName;
        Task next;

        Task(int taskID, String taskName) {
            this.taskID = taskID;
            this.taskName = taskName;
        }

        @Override
        public String toString() {
            return "Task " + taskID + " : " + taskName;
        }
    }

    // Queue implementation
    private static class Queue {
        private Task front;
        private Task rear;

        public void enqueue(Task task) {
            // TODO
        }

        public Task dequeue() {
            // TODO
            return null;
        }

        public boolean isEmpty() {
            // TODO
            return true;
        }
    }

    // BST node for completed tasks
    private static class BSTNode {
        Task task;
        BSTNode left;
        BSTNode right;

        BSTNode(Task task) {
            this.task = task;
        }
    }

    private Queue taskQueue = new Queue();
    private BSTNode tree;

    // Insert completed task into BST
    public void insert(Task task) {
        // TODO
    }

    // Remove task from BST (for undo)
    public void remove(int taskID) {
        // TODO
    }

    // Traverse BST
    public void traverse() {
        inorder(tree);
    }

    private void inorder(BSTNode current){
        // TODO: Apply inorder traversal
    }

    public void addTask(int taskID, String taskName) {
        // TODO: Create a task with the given name and ID and pass it to the queue
    }

    public void processNext() {
        // TODO: Take the next task from the queue, and pass it to processTask()
    }

    public void undoTask(int taskId) {
        // TODO: Undo a completed task
    }

    private Task searchBST(BSTNode current, int taskID) {
        // TODO: Search for a specific task
    }

    public static void processTask(Task task) {
        System.out.println("Processing: " + task);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Completed:  " + task);
    }

}
