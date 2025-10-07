/**
 * Disk class to represent each disk that gets pushed onto the stacks
 * Each disk belongs to a specific player
 */
public class Disk {
    private Player owner;
    
    /**
     * Constructor for Disk
     * @param owner The player who owns this disk
     */
    public Disk(Player owner) {
        this.owner = owner;
    }
    
    /**
     * Get the owner of this disk
     * @return The Player who owns this disk
     */
    public Player getOwner() {
        return owner;
    }
}