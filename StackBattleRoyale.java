import java.util.*;

/**
 * Main game class for Stack Battle Royale
 * Manages the game simulation, player turns, and determines the winner
 */
public class StackBattleRoyale {
    private Player player1;
    private Player player2;
    private Stack<Disk> stackA;
    private Stack<Disk> stackB;
    private Stack<Disk> stackC;
    private int totalRounds;
    private Random random;
    
    // Pop intervals - made configurable as requested
    private final int STACK_A_POP_INTERVAL = 3;
    private final int STACK_B_POP_INTERVAL = 7;
    private final int STACK_C_POP_INTERVAL = 8;
    
    /**
     * Constructor - initializes the game components
     */
    public StackBattleRoyale() {
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.stackA = new Stack<>();
        this.stackB = new Stack<>();
        this.stackC = new Stack<>();
        this.random = new Random();
    }
    
    /**
     * Main method to start the game
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        StackBattleRoyale game = new StackBattleRoyale();
        game.startGame();
    }
    
    /**
     * Start the game - get input and run simulation
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        
        // Get number of rounds from user
        do {
            System.out.print("Enter number of rounds to play (n >= 30): ");
            totalRounds = scanner.nextInt();
            if (totalRounds < 30) {
                System.out.println("Number of rounds must be at least 30!");
            }
        } while (totalRounds < 30);
        
        // Run the simulation
        runSimulation();
        
        // Calculate and display results
        displayResults();
        
        scanner.close();
    }
    
    /**
     * Run the main game simulation for all rounds
     */
    private void runSimulation() {
        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("\nRound " + round + ":");
            
            //Fun little round narration implement 
            String[] intros = {
                "Both players eye the stacks cautiously...",
                "Tension rises as the round begins...",
                "Let's see who takes control this time!",
            };
            System.out.println(intros[random.nextInt(intros.length)]);
            // Player 1's turn
            takeTurn(player1);
            
            // Player 2's turn
            takeTurn(player2);
            
            // Check pop timers at end of round
            checkPopTimers(round);
        }
    }
    
    /**
     * Handle a single player's turn
     * @param player The player taking their turn
     */
    private void takeTurn(Player player) {
        // Generate random stack choice (0=A, 1=B, 2=C)
        int stackChoice = random.nextInt(3);
        Stack<Disk> chosenStack;
        String stackName;
        
        // Determine which stack was chosen
        switch (stackChoice) {
            case 0:
                chosenStack = stackA;
                stackName = "Stack A";
                break;
            case 1:
                chosenStack = stackB;
                stackName = "Stack B";
                break;
            default:
                chosenStack = stackC;
                stackName = "Stack C";
                break;
        }
        
        // Check if we need to pop opponent's disk first
        if (!chosenStack.isEmpty()) {
            Disk topDisk = chosenStack.peek();
            if (!topDisk.getOwner().equals(player)) {
                // Pop opponent's disk
                chosenStack.pop();
                System.out.println(player.getName() + " pushes a disk onto " + 
                    stackName + ", popping " + topDisk.getOwner().getName() + "'s disk.");
            } else {
                // Just push normally
                System.out.println(player.getName() + " pushes a disk onto " + stackName + ".");
            }
        } else {
            // Stack is empty, just push
            System.out.println(player.getName() + " pushes a disk onto " + stackName + ".");
        }
        
        // Push the new disk
        chosenStack.push(new Disk(player));
    }
    
    /**
     * Check if any stacks should pop based on round number
     * @param round The current round number
     */
    private void checkPopTimers(int round) {
        // Check Stack A (every 3rd round)
        if (round % STACK_A_POP_INTERVAL == 0) {
            popFromStack(stackA, "Stack A", "pop schedule");
        }
        
        // Check Stack B (every 7th round)
        if (round % STACK_B_POP_INTERVAL == 0) {
            popFromStack(stackB, "Stack B", "pop schedule");
        }
        
        // Check Stack C (every 8th round)
        if (round % STACK_C_POP_INTERVAL == 0) {
            popFromStack(stackC, "Stack C", "pop schedule");
        }
    }
    
    /**
     * Helper method to pop from a stack with proper exception handling
     * @param stack The stack to pop from
     * @param stackName The name of the stack (for output)
     * @param reason The reason for popping (for output)
     */
    private void popFromStack(Stack<Disk> stack, String stackName, String reason) {
        try {
            if (!stack.isEmpty()) {
                Disk poppedDisk = stack.pop();
                System.out.println("A disk for " + poppedDisk.getOwner().getName() + 
                    " was popped from " + stackName + " due to " + reason + ".");
            }
        } catch (EmptyStackException e) {
            // Stack is empty, nothing to pop - handle gracefully
            System.out.println("Attempted to pop from " + stackName + 
                " but it was empty.");
        }
    }
    
    /**
     * Calculate final scores and display winner
     * Pops all remaining disks from stacks and counts them
     */
    private void displayResults() {
        System.out.println("\nGame over!");
        
        int player1Count = 0;
        int player2Count = 0;
        
        // Count disks from Stack A
        while (!stackA.isEmpty()) {
            Disk disk = stackA.pop();
            if (disk.getOwner().equals(player1)) {
                player1Count++;
            } else {
                player2Count++;
            }
        }
        
        // Count disks from Stack B
        while (!stackB.isEmpty()) {
            Disk disk = stackB.pop();
            if (disk.getOwner().equals(player1)) {
                player1Count++;
            } else {
                player2Count++;
            }
        }
        
        // Count disks from Stack C
        while (!stackC.isEmpty()) {
            Disk disk = stackC.pop();
            if (disk.getOwner().equals(player1)) {
                player1Count++;
            } else {
                player2Count++;
            }
        }
        
        // Display final scores
        System.out.println(player1.getName() + " has " + player1Count + " disks remaining.");
        System.out.println(player2.getName() + " has " + player2Count + " disks remaining.");
        
        // Announce winner
        if (player1Count > player2Count) {
            System.out.println(player1.getName() + " wins!");
        } else if (player2Count > player1Count) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
        //Little extra Fun Ending Credit MSGS
        System.out.println("\nThanks for playing my Stack BR - Haaziq Out");
        System.out.println("GG to both parties involved!");
    }
}