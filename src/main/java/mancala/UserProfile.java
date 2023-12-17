package mancala;
import java.io.Serializable;

/**
 * A class to store the stats of a player
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private int kalahGames;
    private int kalahWins;
    private int ayoGames;
    private int ayoWins;

    /**
     * Constructor to initialize a user profile.
     * 
     * @param name The name of the user.
     */
    public UserProfile(final String name) {
        this.userName = name;
        this.kalahGames = 0;
        this.kalahWins = 0;
        this.ayoGames = 0;
        this.ayoWins = 0;
    }

    /**
     * Constructor to initialize a user profile without a name.
     */
    public UserProfile() {
        this("");
    }

    /**
     * Sets the name of the user.
     * 
     * @param newName The new name of the user.
     */
    public void setUserName(final String newName) {
        this.userName = newName;
    }

    /**
     * Gets the name of the user.
     * 
     * @return The name of the user.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Gets the number of Kalah games played by the user.
     * 
     * @return The number of Kalah games.
     */
    public int getKalahGames() {
        return this.kalahGames;
    }

    /**
     * Gets the number of Kalah wins played by the user.
     * 
     * @return The number of Kalah wins.
     */
    public int getKalahWins() {
        return this.kalahWins;
    }

    /**
     * Gets the number of Ayo games played by the user.
     * 
     * @return The number of Ayo games.
     */
    public int getAyoGames() {
        return this.ayoGames;
    }

    /**
     * Gets the number of Ayo wins played by the user.
     * 
     * @return The number of Ayo wins.
     */
    public int getAyoWins() {
        return this.ayoWins;
    }

    /**
     * Adds stats based on the game results.
     * 
     * @param isWin True if the game was won, false otherwise.
     * @param isKalah True if the game was Kalah, false otherwise.
     */
    public void addGame(final boolean isWin, final boolean isKalah) {
        if (isKalah) {
            this.kalahGames += 1;
            if (isWin) {
                this.kalahWins += 1;
            }
        } else {
            this.ayoGames += 1;
            if (isWin) {
                this.ayoWins += 1;
            }
        }
    }
}