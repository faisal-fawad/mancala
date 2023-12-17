package mancala;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * A class which represents the entire Mancala game. 
 */
public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private GameRules gameBoard;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private boolean kalah;

    /**
     * Constructor to initialize a Mancala game.
     * 
     * @param isKalah True if using the Kalah ruleset, false otherwise.
     */
    public MancalaGame(final boolean isKalah) {
        this.kalah = isKalah;
        if (isKalah) {
            this.gameBoard = new KalahRules();
        } else {
            this.gameBoard = new AyoRules();
        }
    }

    /**
     * Constructor to initialize a Mancala game with a Kalah ruleset.
     */
    public MancalaGame() {
        this(true);
    }

    /**
     * Checks if the game is Kalah.
     * 
     * @return True if the game is Kalah, false otherwise.
     */
    public boolean isKalah() {
        return this.kalah;
    }

    /**
     * Gets the board of the game.
     * 
     * @return The board of the game.
     */
    public GameRules getBoard() {
        return this.gameBoard;
    }

    /**
     * Gets the current player of the game.
     * 
     * @return The current player of the game.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Gets the number of stones in a pit.
     * 
     * @param pitNum The pit number.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return this.gameBoard.getNumStones(pitNum);
    }

    /**
     * Gets the players of the game.
     * 
     * @return The players of the game.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Gets the number of stones in a store.
     * 
     * @param player The player corresponding to the store.
     * @return The number of stones in the store.
     */
    public int getStoreCount(final Player player) {
        return player.getStoreCount();
    }

    /**
     * Gets the number of stones in a store.
     * 
     * @param playerNum The player number corresponding to the store.
     * @return The number of stones in the store.
     */
    public int getStoreCount(final int playerNum) {
        return this.gameBoard.getDataStructure().getStoreCount(playerNum);
    }

    /**
     * Gets the winner of the game.
     * 
     * @return The winner of the game.
     * @throws GameNotOverException If the game is not over.
     */
    public Player getWinner() throws GameNotOverException {
        // Error handling
        if (!isGameOver()) {
            throw new GameNotOverException();
        }
        
        final int oneCount = getStoreCount(this.players.get(0));
        final int twoCount = getStoreCount(this.players.get(1));
        if (oneCount > twoCount) {
            return this.players.get(0);
        } else if (twoCount > oneCount) {
            return this.players.get(1);
        }
        return null;
    }

    /**
     * Checks if the game is over.
     * 
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return this.gameBoard.isSideEmpty(1) || this.gameBoard.isSideEmpty(12);
    }

    /**
     * Makes a move for the current player
     * 
     * @param startPit The starting pit number of the move.
     * @return The remaining number of stones on the player's side of the board after the move.
     * @throws InvalidMoveException If the move is invalid. 
     */
    public int move(final int startPit) throws InvalidMoveException {
        final int curPlayer = getPlayers().indexOf(getCurrentPlayer()) % 2 + 1;
        if (!this.gameBoard.isValidMove(startPit, curPlayer)) {
            throw new InvalidMoveException();
        }

        // Attempts the move (throws InvalidMoveException)
        this.gameBoard.moveStones(startPit, curPlayer);

        // Code reaching here ensures that our start pit is a valid integer
        int sum = 0;
        int lowerBound; 
        int upperBound;

        if (startPit >= 1 && startPit <= 6) {
            lowerBound = 0;
            upperBound = 5;
        } else {
            lowerBound = 6;
            upperBound = 11;
        }

        // Get the total on one side of the board after moving
        for (int i = lowerBound; i <= upperBound; i++) {
            sum += getNumStones(i + 1);
        }

        return sum;
    }

    /**
     * Sets the board the game.
     * 
     * @param theBoard The new board of the game.
     */
    public void setBoard(final GameRules theBoard) {
        this.gameBoard = theBoard;
    }

    /**
     * Sets the current player of the game.
     * 
     * @param player The new current player of the game.
     */
    public void setCurrentPlayer(final Player player) {
        this.currentPlayer = player;
    }

    /**
     * Sets the players of the game, ensuring a two way connection.
     * 
     * @param onePlayer Player one of the game.
     * @param twoPlayer Player two of the game.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        this.players = new ArrayList<>();
        this.players.add(onePlayer);
        this.players.add(twoPlayer);
        setCurrentPlayer(onePlayer);
        this.gameBoard.registerPlayers(onePlayer, twoPlayer);
    }

    /**
     * Starts a new game 
     */
    public void startNewGame() {
        this.gameBoard.resetBoard();
    }

    @Override
    public String toString() {
        return "...";
    }
}