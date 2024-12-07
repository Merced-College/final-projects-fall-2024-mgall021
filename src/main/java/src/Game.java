package src;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private Room startRoom;       // The first room in the linked list
    private UndoMove undoMove;    // Stack to manage player movement
    private Queue<Room> roomQueue; // Queue to manage room
    private int solvedCount;      // Number of riddles solved
    private Scanner scanner;

    public Game() {
        this.undoMove = new UndoMove();
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
                new String[]{"Tree", "Candle", "Person", "Pencil"},
                "candle",
                "It gives light but melts over time."
        );

        Room room3 = new Room(
                "Room 3",
                "The more you take, the more you leave behind. What am I?",
                new String[]{"Sand", "Time", "Footsteps", "Memories"},
                "footsteps",
                "Think about something physical that gets left behind as you move."
        );

        Room room4 = new Room(
                "Room 4",
                "What has hands but cannot clap?",
                new String[]{"Statue", "Doll", "Robot", "Clock"},
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


    public void start() {
        System.out.println("Welcome to the Post-Apocalyptic Scavenger!");
        System.out.println("Solve all 5 riddles to repair the car and escape!");

        Room currentRoom = startRoom;
        int attempts = 0; // Track incorrect attempts for hints and skip logic
        int firstTryIncorrectCount = 0; // Track first-try incorrect answers

        while ((!roomQueue.isEmpty() || currentRoom != null) && firstTryIncorrectCount < 3) {
            // Present the riddle if unsolved
            if (!currentRoom.solved) {
                System.out.println("\nYou have entered " + currentRoom.name);
                System.out.println("Riddle: " + currentRoom.riddle);

                // Display multiple-choice options
                currentRoom.displayOptions();

                System.out.print("Choose the correct answer (1-4): ");
                String playerChoice = scanner.nextLine().trim();

                // Validate input
                int choiceIndex;
                try {
                    choiceIndex = Integer.parseInt(playerChoice) - 1;
                    if (choiceIndex < 0 || choiceIndex >= currentRoom.options.length) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Please select a number between 1 and 4.");
                    continue;
                }

                // Check if the answer is correct
                if (currentRoom.options[choiceIndex].equalsIgnoreCase(currentRoom.answer)) {
                    System.out.println("Correct! This part of the car is repaired.");
                    currentRoom.solved = true;
                    solvedCount++;
                    attempts = 0; // Reset attempts
                } else {
                    System.out.println("Incorrect! Try again.");
                    attempts++;

                    // Increment first-try incorrect counter if this is the first attempt
                    if (attempts == 1) {
                        firstTryIncorrectCount++;
                    }

                    // Offer hint after 3 incorrect attempts
                    if (attempts == 3) {
                        System.out.println("Hint: " + currentRoom.hint);
                    }

                    // Ask if they want to skip only after 2 incorrect attempts
                    if (attempts >= 2) {
                        System.out.print("Do you want to skip this room? (yes/no): ");
                        String skipChoice = scanner.nextLine().trim().toLowerCase();

                        if (skipChoice.equals("yes")) {
                            // Add the room back to the queue for later
                            roomQueue.add(currentRoom);
                            if (!roomQueue.isEmpty()) {
                                currentRoom = roomQueue.poll(); // Move to the next room
                            } else {
                                System.out.println("No more rooms to visit.");
                                break;
                            }
                            attempts = 0; // Reset attempts for the next room
                            continue;
                        } else {
                            System.out.println("You stay in " + currentRoom.name + ".");
                            continue; // Stay in the current room
                        }
                    }
                }
            } else {
                System.out.println(currentRoom.name + " is already solved.");
            }

            // Check win condition
            if (solvedCount == 5) {
                System.out.println("\n🎉 Congrats! You have successfully repaired the car and can start adventuring the rest of this post-apocalyptic world! 🎉");
                break;
            }

            // Move to the next room only if it's solved
            if (!roomQueue.isEmpty() && currentRoom.solved) {
                undoMove.push(currentRoom); // Push current room onto the stack
                currentRoom = roomQueue.poll(); // Move to the next room
            } else if (!currentRoom.solved) {
                continue; // Stay in the current room if unsolved
            } else {
                System.out.println("No more rooms to visit.");
                break;
            }
        }

        // Lose condition
        if (firstTryIncorrectCount >= 3) {
            System.out.println("\n💀 You have been spotted by nearby scavengers and taken prisoner. 💀");
        } else {
            System.out.println("Game Over. Thanks for playing!");
        }

        scanner.close();
    }

}