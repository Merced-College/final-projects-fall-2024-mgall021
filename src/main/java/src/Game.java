package src;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private Room startRoom;       // The first room in the linked list
    private UndoMove undoMove;    // Stack to manage player movement
    private Queue<Room> roomQueue; // Queue to manage room traversal
    private int solvedCount;      // Number of riddles solved
    private Scanner scanner;      // Scanner for user input

    // Constructor
    public Game() {
        this.undoMove = new UndoMove(); // Initialize UndoMove
        this.roomQueue = new LinkedList<>();
        this.solvedCount = 0;
        this.scanner = new Scanner(System.in);
        initializeRooms(); // Create and link rooms
    }

    // Initialize and link rooms
    private void initializeRooms() {
        Room room1 = new Room(
                "Room 1",
                "What has to be broken before you can use it?",
                new String[]{"Egg", "Window", "Code", "Chain"},
                "egg",
                "It’s something fragile that contains something valuable."
        );

        Room room2 = new Room(
                "Room 2",
                "I’m tall when I’m young, and short when I’m old. What am I?",
                new String[]{"Candle", "Tree", "Person", "Pencil"},
                "candle",
                "It gives light but melts over time."
        );

        Room room3 = new Room(
                "Room 3",
                "The more you take, the more you leave behind. What am I?",
                new String[]{"Footsteps", "Time", "Sand", "Memories"},
                "footsteps",
                "Think about something physical that gets left behind as you move."
        );

        Room room4 = new Room(
                "Room 4",
                "What has hands but cannot clap?",
                new String[]{"Clock", "Doll", "Robot", "Statue"},
                "clock",
                "It’s something that tells time."
        );

        Room room5 = new Room(
                "Room 5",
                "I speak without a mouth and hear without ears. What am I?",
                new String[]{"Echo", "Shadow", "Dream", "Light"},
                "echo",
                "It’s a phenomenon you experience in a canyon or large space."
        );

        // Link rooms
        room1.nextRoom = room2;
        room2.nextRoom = room3;
        room3.nextRoom = room4;
        room4.nextRoom = room5;

        // Add rooms to the queue
        this.startRoom = room1;
        Room currentRoom = startRoom;
        while (currentRoom != null) {
            roomQueue.add(currentRoom);
            currentRoom = currentRoom.nextRoom;
        }
    }


    // Start the game
    public void start() {
        System.out.println("Welcome to the Post-Apocalyptic Scavenger!");
        System.out.println("Solve all 5 riddles to repair the car and escape!");

        Room currentRoom = startRoom;

        while (!roomQueue.isEmpty()) {
            // Present the riddle if unsolved
            if (!currentRoom.solved) {
                System.out.println("\nYou have entered " + currentRoom.name);
                System.out.println("Riddle: " + currentRoom.riddle);
                System.out.print("Your answer: ");
                String playerAnswer = scanner.nextLine().trim().toLowerCase();

                // Check the answer
                if (playerAnswer.equals(currentRoom.answer.toLowerCase())) {
                    System.out.println("Correct! This part of the car is repaired.");
                    currentRoom.solved = true;
                    solvedCount++;
                } else {
                    System.out.println("Incorrect! Try again later.");

                    // Ask if they want to skip the room
                    System.out.print("Do you want to skip this room? (yes/no): ");
                    String skipChoice = scanner.nextLine().trim().toLowerCase();

                    if (skipChoice.equals("yes")) {
                        // Move to the next room in the queue
                        undoMove.push(currentRoom); // Push current room onto the stack
                        if (!roomQueue.isEmpty()) {
                            currentRoom = roomQueue.poll(); // Get the next room
                        } else {
                            System.out.println("No more rooms to visit.");
                            break;
                        }
                        continue;
                    } else {
                        // Check if they want to go to the previous room, only if there's a previous room
                        if (!undoMove.isEmpty()) {
                            System.out.print("Do you want to move to the previous room? (yes/no): ");
                            String moveBackChoice = scanner.nextLine().trim().toLowerCase();

                            if (moveBackChoice.equals("yes")) {
                                Room lastRoom = undoMove.pop(); // Move back to the previous room
                                if (lastRoom != null) {
                                    currentRoom = lastRoom;
                                    System.out.println("You returned to " + currentRoom.name + ".");
                                    continue;
                                }
                            } else {
                                System.out.println("You stay in " + currentRoom.name + ".");
                            }
                        } else {
                            System.out.println("You are in the first room, so you cannot move to a previous room.");
                        }
                    }
                }
            } else {
                System.out.println(currentRoom.name + " is already solved.");
            }

            // Check win condition
            if (solvedCount == 5) {
                System.out.println("\nCongratulations! You've solved all the riddles and repaired the car!");
                break;
            }

            // Move to the next room
            if (!roomQueue.isEmpty()) {
                undoMove.push(currentRoom); // Push current room onto the stack
                currentRoom = roomQueue.poll(); // Move to the next room
            } else {
                System.out.println("No more rooms to visit.");
                break;
            }
        }

        System.out.println("Game Over. Thanks for playing!");
        scanner.close();
    }
}
