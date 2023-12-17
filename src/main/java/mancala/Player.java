package mancala;
import java.io.Serializable;

/**
 * A class which represents a Player in a Mancala game
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private Store playerStore;
    private String playerName;
    private UserProfile profile;

    /**
     * Constructor to initialize a player.
     * 
     * @param name The name of the player.
     */
    public Player(final String name) {
        this.playerName = name;
        this.profile = new UserProfile(name);
    }

    /**
     * Constructor to initialize a player with no name.
     */
    public Player() {
        this("");
    }

    /**
     * Gets the user profile of the player.
     * 
     * @return The user profile. 
     */
    public UserProfile getProfile() {
        return this.profile;
    }

    /**
     * Gets the name of the player.
     * 
     * @return The name of the player.
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Gets the store of the player.
     * 
     * @return The store of player.
     */
    public Store getStore() {
        return this.playerStore;
    }

    /**
     * Gets the number of stones in the player's store.
     * 
     * @return The number of stones in the player's store.
     */
    public int getStoreCount() {
        return this.playerStore.getStoneCount();
    }

    /**
     * Sets the name of the player.
     * 
     * @param name The new name of the player.
     */
    public void setName(final String name) {
        this.playerName = name;
    }

    /**
     * Sets the store of player.
     * 
     * @param store The new store of the player.
     */
    public void setStore(final Store store) {
        this.playerStore = store;
    }

    @Override
    public String toString() {
        return getName();
    }
}