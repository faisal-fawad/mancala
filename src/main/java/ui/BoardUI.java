package ui;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.Box;
import mancala.MancalaGame;
import java.util.ArrayList;

/**
 * A window representative of all components during a game.
 */
public class BoardUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int STORE_ONE = 7;
    private GameWindow window;
    private MancalaGame game; 
    protected ArrayList<BoardButton> boardButtons;
    protected ProfileUI playerOneProfile;
    protected ProfileUI playerTwoProfile;

    public BoardUI(final GameWindow newWindow, final MancalaGame newGame, final boolean isNew) {
        super();
        this.window = newWindow;
        this.game = newGame;
        if (isNew) {
            game.setPlayers(this.window.getPlayerOne(), this.window.getPlayerTwo());
        }
        createUI();
    }

    public BoardUI(final GameWindow newWindow, final boolean newIsKalah) {
        this(newWindow, new MancalaGame(newIsKalah), true);
    }

    /**
     * Gets the current game being played.
     * 
     * @return The current game.
     */
    public MancalaGame getGame() {
        return this.game;
    }

    /**
     * Checks if the game is Kalah.
     * 
     * @return True if the game is Kalah, false otherwise.
     */
    public boolean isKalah() {
        return this.game.isKalah();
    }

    /**
     * Updates the number of stones in stores and pits on the UI side.
     */
    public void updateBoard() {
        for (int i = 0; i < 14; i++) {
            if (this.boardButtons.get(i).isPit()) {
                this.boardButtons.get(i).setText(String.valueOf(game.getNumStones(this.boardButtons.get(i).getPosition()))); // Pit
            } else {
                this.boardButtons.get(i).setText(String.valueOf(game.getStoreCount(this.boardButtons.get(i).getPosition()))); // Store
            }
        }
    }

    /**
     * Disables buttons which are invalid moves.
     * 
     * @param playerOne True if the move is for player one, false otherwise.
     */
    public void disableButtons(final boolean playerOne) {
        // If playerOne is true, we disable P1 buttons, otherwise disable P2
        for (int i = 1; i < 13; i++) {
            if (playerOne) {
                if (i < this.STORE_ONE) {
                    this.boardButtons.get(i).setEnabled(true);
                } else {
                    this.boardButtons.get(i).setEnabled(false);
                }
            } else {
                if (i < this.STORE_ONE) {
                    this.boardButtons.get(i).setEnabled(false);
                } else {
                    this.boardButtons.get(i).setEnabled(true);
                }
            }
            if (game.getNumStones(this.boardButtons.get(i).getPosition()) == 0) {
                this.boardButtons.get(i).setEnabled(false);
            }
        }
    }

    protected void createUI() {
        setPreferredSize(new Dimension(600, 370));
        setAlignmentY(JComponent.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createButtons();
        this.playerTwoProfile = new ProfileUI(this.window.getPlayerTwo());
        add(this.playerTwoProfile);
        add(Box.createRigidArea(new Dimension(0, 32)));
        createBoard();
        add(Box.createRigidArea(new Dimension(0, 32)));
        this.playerOneProfile = new ProfileUI(this.window.getPlayerOne());
        add(this.playerOneProfile);
        if (this.game.getCurrentPlayer().equals(window.getPlayerOne())) {
            disableButtons(false);
        } else {
            disableButtons(true);
        }
    }

    protected void createButtons() {
        this.boardButtons = new ArrayList<>();

        final BoardButton twoStoreButton = new BoardButton(String.valueOf(this.game.getStoreCount(2)), 2, false, this, this.window);
        twoStoreButton.setPreferredSize(new Dimension(75, 150));
        twoStoreButton.setEnabled(false);
        this.boardButtons.add(twoStoreButton);

        for (int i = 12; i > 6; i--) {
            final BoardButton tempButton = new BoardButton(String.valueOf(this.game.getNumStones(i)), i, true, this, this.window);
            tempButton.setPreferredSize(new Dimension(75, 75));
            this.boardButtons.add(tempButton);
        }
        for (int j = 1; j <= 6; j++) {
            final BoardButton tempButton = new BoardButton(String.valueOf(this.game.getNumStones(j)), j, true, this, this.window);
            tempButton.setPreferredSize(new Dimension(75, 75));
            this.boardButtons.add(tempButton);
        }
        
        final BoardButton oneStoreButton = new BoardButton(String.valueOf(this.game.getStoreCount(1)), 1, false, this, this.window);
        oneStoreButton.setPreferredSize(new Dimension(75, 150));
        oneStoreButton.setEnabled(false);
        this.boardButtons.add(oneStoreButton);
    }

    protected void createBoard() {
        final JPanel pits = new JPanel(); 
        final JPanel container = new JPanel();
        container.setMaximumSize(new Dimension(600, 150));
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pits.setLayout(new GridLayout(2, 6)); // 2 x 6 grid for pits

        container.add(this.boardButtons.get(0));
        for (int i = 1; i < 13; i++) {
            pits.add(this.boardButtons.get(i));
        }
        container.add(pits);
        container.add(this.boardButtons.get(13));
        add(container);
    }
}