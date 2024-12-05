package src;

public class Room {
    String name;       // The name of the room
    String riddle;     // The riddle presented in this room
    String answer;     // The answer to the riddle
    boolean solved;    // Whether the riddle has been solved
    Room nextRoom;     // Reference to the next room (linked list)

    // Constructor
    public Room(String name, String riddle, String answer) {
        this.name = name;
        this.riddle = riddle;
        this.answer = answer;
        this.solved = false;  // Initially unsolved
        this.nextRoom = null; // Initially, no next room
    }
}
