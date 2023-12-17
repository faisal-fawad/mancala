package mancala;
import java.io.Serializable;

/**
 * A class which represents a store in a Mancala game
 */
public class Store implements Countable, Serializable {
    private static final long serialVersionUID = 1L;
    private Player owner;
    private int stones;

    /**
     * Constructor to initialize store.
     */
    public Store() {
        this.stones = 0;
    }

    @Override
    public void addStones(final int amount) {
        this.stones += amount;
    }

    @Override
    public void addStone() {
        this.stones += 1; 
    }

    @Override
    public int removeStones() {
        final int stoneCount = getStoneCount();
        this.stones = 0; // Removes all stones
        return stoneCount;
    }

    @Override
    public int getStoneCount() {
        return this.stones;
    }

    /**
     * Gets the owner of the store.
     * 
     * @return The player who owns the store.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner of the store.
     * 
     * @param player The new owner of the store.
     */
    public void setOwner(final Player player) {
        this.owner = player;
    }

    @Override 
    public String toString() {
        return String.valueOf(getStoneCount());
    }
}