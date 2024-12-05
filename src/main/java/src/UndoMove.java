package src;
import java.util.Stack;

public class UndoMove {
    private Stack<Room> stack;

    // Constructor
    public UndoMove() {
        this.stack = new Stack<>();
    }

    // Push a room onto the stack
    public void push(Room room) {
        stack.push(room);
    }

    // Pop the last room from the stack
    public Room pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
