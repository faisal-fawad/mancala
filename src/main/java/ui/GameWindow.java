package ui;
import java.awt.CardLayout;
import javax.swing.JFrame;
import mancala.Player;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import mancala.Saver;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.File;
import java.awt.Dimension;

/**
 * A game window which initializes the window and speaks to the UI components.
 */
public class GameWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int GAME_WIDTH = 700;
    private static final int GAME_HEIGHT = 450;
    private static final int STAY = 2;
    protected static final int PLAYER_ONE = 1;
    protected static final int PLAYER_TWO = 2;
    protected static final String MENU_SIGNATURE = "MENU";
    protected static final String BOARD_SIGNATURE = "BOARD";
    private final CardLayout cards = new CardLayout();
    private Player playerOne;
    private Player playerTwo;
    private BoardUI currentBoard;

    /**
     * A constructor which initializes the game window
     */
    public GameWindow() {
        super();
        setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(cards);
        setJMenuBar(new MenuBarUI(this));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent event) {
                exitProgram();
            }
        });
        this.playerOne = new Player("P1");
        this.playerTwo = new Player("P2");
        setVisible(true);
    }

    /**
     * Displays a window which matches a pre-defined signature.
     * 
     * @param signature A pre-defined signature in this class.
     */
    public void showWindow(final String signature) {
        this.cards.show(this.getContentPane(), signature);
    }

    /**
     * Gets player one of the game.
     * 
     * @return Player one of the game.
     */
    public Player getPlayerOne() {
        return this.playerOne;
    }

    /**
     * Sets a new player one for the game.
     * 
     * @param player The new player one for the game.
     */
    public void setPlayerOne(final Player player) {
        this.playerOne = player;
    }

    /**
     * Gets player two of the game.
     * 
     * @return Player two of the game.
     */
    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    /**
     * Sets a new player two for the game.
     * 
     * @param player The new player two for the game.
     */
    public void setPlayerTwo(final Player player) {
        this.playerTwo = player;
    }

    /**
     * Sets the current game UI.
     * 
     * @param newBoard The new game UI.
     */
    public void setCurrentBoard(final BoardUI newBoard) {
        this.currentBoard = newBoard;
    }

    /**
     * Gets the current game UI.
     * 
     * @return The current game UI.
     */
    public BoardUI getCurrentBoard() {
        return this.currentBoard; // Can be null 
    }

    /**
     * Changes one of the player's names.
     * 
     * @param playerOne True for player one, false for player two.
     * @param newName The new name for the player.
     */
    public void changePlayerName(final boolean playerOne, final String newName) {
        if (playerOne) {
            this.playerOne.setName(newName);
            this.playerOne.getProfile().setUserName(newName);
        } else {
            this.playerTwo.setName(newName);
            this.playerTwo.getProfile().setUserName(newName);
        }
    }

    protected void exitProgram() {
        final int performSave = JOptionPane.showConfirmDialog(null, "Save current players?");
        if (performSave == 0) {
            try {
                File dir = new File("assets");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Saver.saveObject(this.playerOne, "1-" + this.playerOne.getName());
                Saver.saveObject(this.playerTwo, "2-" + this.playerTwo.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to save players!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An unknown error has occured!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 

        if (performSave != this.STAY) {
            System.exit(0);
        }
    }

    // Reponsible for setting up the program
    public static void main(final String[] args) {
        final GameWindow game = new GameWindow();
        game.add(new MenuUI(game), MENU_SIGNATURE);
        game.showWindow(MENU_SIGNATURE);
        game.pack();
    }

    /* Inherits methods from JFrame */
}