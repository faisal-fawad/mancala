package mancala;
import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_TWO = 2;
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    /* Custom game logic to calculate a bonus turn, always false for Ayo */
    private boolean bonus;
    public boolean isBonus() {
        return this.bonus;
    }

    public void setBonus(final boolean newBonus) {
        this.bonus = newBonus;
    }
    /* End of custom game logic */

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        this.gameBoard.setUpPits();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */ 
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    public boolean isSideEmpty(final int pitNum)  {
        int lowerBound; 
        int upperBound;

        if (pitNum < 1 || pitNum > 12) {
            return false;
        } else if (pitNum >= 1 && pitNum <= 6) {
            lowerBound = 0;
            upperBound = 5;
        } else {
            lowerBound = 6;
            upperBound = 11;
        }

        // Checks if the respective side is empty
        for (int i = lowerBound; i <= upperBound; i++) {
            if (getNumStones(i + 1) != 0) {
                return false;
            }
        }
        return true; // If the code reaches here, the side is empty
    }

    /**
     * Captures all stones on one side of the board
     * 
     * @param pitNum The number of the pit which corresponds to the side of the board
     */
    public void captureSide(final int pitNum) {
        int lowerBound; 
        int upperBound;
        int playerNum;

        if (pitNum >= 1 && pitNum <= 6) {
            lowerBound = 0;
            upperBound = 5;
            playerNum = 1;
        } else if (pitNum >= 7 && pitNum <= 12) {
            lowerBound = 6;
            upperBound = 11;
            playerNum = 2;
        } else {
            lowerBound = -1;
            upperBound = -1;
            playerNum = -1;
        }

        if (playerNum != -1) {
            int sum = 0;
            for (int i = lowerBound; i <= upperBound; i++) {
                sum += this.gameBoard.removeStones(i + 1);
            }
            this.gameBoard.addToStore(playerNum, sum);
        }
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Get the current player.
     * 
     * @return The player number (1 or 2).
     */
    public int getPlayer() {
        return currentPlayer;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */ 
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */ 
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */ 
    abstract int captureStones(int stoppingPoint);

    /**
     * Check if a move is valid
     * 
     * @param startPit The starting pit for the move.
     * @param playerNum The player making the move.
     * @return True if the move is valid, false otherwise.
     */
    boolean isValidMove(final int startPit, final int playerNum) {
        // Error handling for invalid move
        if (playerNum == PLAYER_ONE) {
            // Valid pits are 1-6 for P1
            if (!(startPit >= 1 && startPit <= 6)) {
                return false;
            }
        } else if (playerNum == PLAYER_TWO) {
            // Valid pits are 7-12 for player two
            if (!(startPit >= 7 && startPit <= 12)) {
                return false;
            }
        } else {
            return false; // Player number is not valid
        }

        // If the pit is empty the move is invalid, other it's valid
        return getNumStones(startPit) != 0;
    }

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // Player one
        final Store storeOne = new Store();
        storeOne.setOwner(one);
        one.setStore(storeOne);
        this.gameBoard.setStore(storeOne, 1);

        // Player two
        final Store storeTwo = new Store();
        storeTwo.setOwner(two);
        two.setStore(storeTwo);
        this.gameBoard.setStore(storeTwo, 2);
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    @Override
    public String toString() {
        return "...";
    }
}
