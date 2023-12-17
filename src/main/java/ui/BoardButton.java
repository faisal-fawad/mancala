package ui;
import javax.swing.JButton;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
import javax.swing.JOptionPane;
import mancala.Player;
import mancala.UserProfile;
import mancala.GameRules;
import mancala.MancalaGame;

/**
 * A singular button rendered on the board.
 * This class is also responsible for handling all game logic!
 */
public class BoardButton extends JButton {
    private static final long serialVersionUID = 1L;
    private final int position; 
    private final boolean isPitBtn;
    private final BoardUI display;
    private final GameWindow window;
    private final MancalaGame game;

    public BoardButton(final String val, final int newPosition, final boolean isNewPit, 
    final BoardUI newDisplay, final GameWindow newWindow) {
        super(val);
        this.position = newPosition;
        this.isPitBtn = isNewPit;
        this.display = newDisplay;
        this.window = newWindow;
        this.game = newDisplay.getGame();
        addActionListener(e -> handleFullMove());
    }

    /**
     * Gets the position of a button.
     * 
     * @return A value from 1-14 where 7 & 14 are stores.
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Checks if the button is a pit.
     * 
     * @return True if the button is a pit, false otherwise.
     */
    public boolean isPit() {
        return this.isPitBtn;
    }

    /**
     * A singular method for handling the entire move for a player. 
     * This method does not throw any errors because invalid moves are unavailable to the player by default.
     */
    public void handleMove() {
        try {
            this.game.move(position);
            handleBonus(this.game.getBoard());
        } catch (InvalidMoveException ex) {
            // Code should never reach here since we disable the possible invalid moves
            JOptionPane.showMessageDialog(null, "An unknown error has occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (this.game.isGameOver()) {
            handlePostGame(this.game.getBoard(), this.window.getPlayerOne(), this.window.getPlayerTwo());
        }

        display.updateBoard();
        if (this.game.getCurrentPlayer().equals(window.getPlayerOne())) {
            display.disableButtons(false);
        } else {
            display.disableButtons(true);
        }

        if (this.game.isGameOver()) {
            try {
                displayWinner(this.game.getWinner());
            } catch (GameNotOverException e) {
                // Code should never reach here since we know the game is over
                JOptionPane.showMessageDialog(null, "An unknown error has occured!", "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }

    /* Helper method for handleMove */
    protected void handleBonus(final GameRules board) {
        if (!board.isBonus() /* Bonus turn check! */) {
            if (this.game.getCurrentPlayer().equals(this.window.getPlayerOne())) {
                this.game.setCurrentPlayer(this.window.getPlayerTwo());
            } else {
                this.game.setCurrentPlayer(this.window.getPlayerOne());
            }
        } 
    }

    /* Helper method for handleMove */
    protected void handlePostGame(final GameRules board, final Player one, final Player two) {
        if (board.isSideEmpty(1)) {
            board.captureSide(12);
        } else if (board.isSideEmpty(12)) {
            board.captureSide(1);
        }

        try {
        if (this.game.getWinner().equals(window.getPlayerOne())) {
            updateProfiles(1, one.getProfile(), two.getProfile());
        } else if (this.game.getWinner().equals(window.getPlayerTwo())) {
            updateProfiles(2, one.getProfile(), two.getProfile());
        } else {
            updateProfiles(3, one.getProfile(), two.getProfile());
        }
        } catch (GameNotOverException e) {
            JOptionPane.showMessageDialog(null, "An unknown error has occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* Helper method for handleMove */
    protected void updateProfiles(final int winner, final UserProfile oneProfile, final UserProfile twoProfile) {
        if (winner == this.window.PLAYER_ONE /* Player one won */) {
            oneProfile.addGame(true, display.isKalah());
            twoProfile.addGame(false, display.isKalah());
        } else if (winner == this.window.PLAYER_TWO /* Player two won */) {
            oneProfile.addGame(false, display.isKalah());
            twoProfile.addGame(true, display.isKalah());
        } else /* Tie */ {
            oneProfile.addGame(false, display.isKalah());
            twoProfile.addGame(false, display.isKalah());
        }
        display.playerOneProfile.updatePlayer(window.getPlayerOne());
        display.playerTwoProfile.updatePlayer(window.getPlayerTwo());
    }

    /* Helper method for handleMove */
    protected void displayWinner(final Player winner) {
        final String[] options = {"Play again", "Main menu"};
        final int res = JOptionPane.showOptionDialog(null, winner + " has won!", "Game over", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == 0) {
            window.setCurrentBoard(new BoardUI(this.window, display.isKalah()));
            this.window.add(window.getCurrentBoard(), this.window.BOARD_SIGNATURE);
            this.window.showWindow(this.window.BOARD_SIGNATURE);
        } else {
            this.window.add(new MenuUI(this.window), this.window.MENU_SIGNATURE);
            this.window.showWindow(this.window.MENU_SIGNATURE);
        }
    }

    protected void handleFullMove() {
        if (isPit()) {
            handleMove();
        }
    }
}