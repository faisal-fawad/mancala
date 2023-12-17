package mancala;
import java.io.Serializable;

/**
 * A class which represents a pit in a Mancala game.
 */
public class Pit implements Countable, Serializable {
    private static final long serialVersionUID = 1L;
    private int stones;

    /**
     * Constructor to initialize pit
     */
    public Pit() {
        this.stones = 0;
    }

    @Override
    public void addStone() {
        this.stones += 1; 
    }

    @Override
    public int getStoneCount() {
        return this.stones;
    }

    @Override
    public int removeStones() {
        final int stoneCount = getStoneCount();
        this.stones = 0; // Removes all stones
        return stoneCount;
    }

    @Override
    public void addStones(final int amount) {
        this.stones += amount;
    }

    @Override
    public String toString() {
        return String.valueOf(getStoneCount());
    }
}