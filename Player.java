/**
 * Player class to represent each player in the Stack Battle Royale game
 * Each player has a name and can be compared to other players
 */
public class Player {
    private String name;
    
    /**
     * Constructor for Player
     */
    public Player(String name) {
        this.name = name;
    }
    
    /**
     * Get the player's name
     * @return The name of the player
     */
    public String getName() {
        return name;
    }
    
    /**
     * Check if two players are equal
     * @param obj The object to compare to
     * @return true if the players are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return name.equals(player.name);
    }
    
    /**
     * Generate hash code for the player
     * @return Hash code based on player name
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}