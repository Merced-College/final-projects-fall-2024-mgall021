package src;

public class Room {
    public String name;       // Name of the room
    public String riddle;     // The riddle presented in the room
    public String[] options;  // Multiple-choice options
    public String answer;     // Correct answer
    public String hint;       // Hint for the riddle
    public boolean solved;    // Whether the riddle has been solved
    public Room nextRoom;     // Reference to the next room (linked list)

    // Constructor
    public Room(String name, String riddle, String[] options, String answer, String hint) {
        this.name = name;
        this.riddle = riddle;
        this.options = options;
        this.answer = answer;
        this.hint = hint;
        this.solved = false;  // Initially unsolved
        this.nextRoom = null; // No next room by default
    }

    // Display the multiple-choice options
    public void displayOptions() {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}
