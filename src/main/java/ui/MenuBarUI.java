package ui;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu; 
import mancala.MancalaGame;
import javax.swing.JFileChooser;
import mancala.Saver;
import java.io.IOException;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * A menu bar which is responsible for starting, saving and loading games.
 */
public class MenuBarUI extends JMenuBar {
    private static final long serialVersionUID = 1L;
    private static final String ERROR = "Error";
    private final GameWindow window;

    public MenuBarUI(final GameWindow newWindow) {
        super();
        this.window = newWindow;
        createMenu();
    }

    protected void createMenu() {
        final JMenu menu = new JMenu("Game");
        createGameItems(menu);
        createStateItems(menu);
        createMainMenu(menu);
        final JMenu rules = new JMenu("Rules");
        createRulesMenu(rules);
        add(menu);
        add(rules);
    }

    protected void createRulesMenu(final JMenu rules) {
        final JMenuItem kalahItem = new JMenuItem("Kalah Rules");
        kalahItem.addActionListener(e -> handleRulesListener(true));
        final JMenuItem ayoItem = new JMenuItem("Ayo Rules");
        ayoItem.addActionListener(e -> handleRulesListener(false));
        rules.add(kalahItem);
        rules.add(ayoItem);
    }

    protected void createMainMenu(final JMenu menu) {
        final JMenuItem mainMenu = new JMenuItem("Main Menu");
        mainMenu.addActionListener(e -> handleMenuListener());
        menu.add(mainMenu);
    }

    protected void createGameItems(final JMenu menu) {
        // Kalah
        final JMenuItem kalahItem = new JMenuItem("New Kalah");
        kalahItem.addActionListener(e -> handleGameListener(true));
        menu.add(kalahItem);

        // Ayo
        final JMenuItem ayoItem = new JMenuItem("New Ayo");  
        ayoItem.addActionListener(e -> handleGameListener(false));
        menu.add(ayoItem);
    }

    protected void createStateItems(final JMenu menu) { 
        // Load
        final JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> handleLoadListener());
        menu.add(loadItem);

        // Save
        final JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> handleSaveListener());
        menu.add(saveItem);
    }

    protected void handleLoadListener() {
        final JFileChooser chooser = new JFileChooser("assets");
        final int returnVal = chooser.showOpenDialog(window);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                final MancalaGame loadedGame = (MancalaGame) Saver.loadObject(chooser.getSelectedFile().getName());
                window.setPlayerOne(loadedGame.getPlayers().get(0));
                window.setPlayerTwo(loadedGame.getPlayers().get(1));
                window.setCurrentBoard(new BoardUI(window, loadedGame, false));
                window.add(window.getCurrentBoard(), window.BOARD_SIGNATURE);
                window.showWindow(window.BOARD_SIGNATURE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to load game!", ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(null, "Save file is not a game!", ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An unknown error has occured!", ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void handleSaveListener() {
        final String filename = JOptionPane.showInputDialog("Please enter a file name: ");
        if (filename != null && !filename.isEmpty()) {
            try {
                File dir = new File("assets");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Saver.saveObject(window.getCurrentBoard().getGame(), filename);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to save game!", ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "No game to save!", ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An unknown error has occured!", ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void handleGameListener(final boolean isKalah) {
        window.setCurrentBoard(new BoardUI(window, isKalah));
        window.add(window.getCurrentBoard(), window.BOARD_SIGNATURE);
        window.showWindow(window.BOARD_SIGNATURE);
    }

    protected void handleMenuListener() {
        window.setCurrentBoard(null);
        final MenuUI currentMenu = new MenuUI(window);
        window.add(currentMenu, window.MENU_SIGNATURE);
        window.showWindow(window.MENU_SIGNATURE);
    }

    protected void handleRulesListener(final boolean isKalah) {
        if (isKalah) {
            JOptionPane.showMessageDialog(null, 
            "(1) If you run into your own store, deposit one piece in it. If you run into your opponent's store, skip it. \n" +
            "(2) If the last piece you drop is in your own store, you get a free turn. \n" +
            "(3) If the last piece you drop is in an empty hole on your side, you capture that piece and any pieces in the \n" + 
            "hole directly opposite. \n" +
            "(4) Always place all captured pieces in your store. \n", 
            "Kalah Rules", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
            "(1) When sowing the seeds, the starting pit is excluded. If there are enough stones in a pit to travel around \n" +
            "the board without reaching the starting pit on the second pass, no stone is placed in the original starting pit. \n" + 
            "In other words, the starting pit always remains empty after a move. \n" +
            "(2) After distributing the stones, if the last stone lands in a pit on either side of the board that already \n" +
            "contains stones, all the stones in that pit are collected and redistributed. This multi-lap play continues until \n" +
            "the last seed falls into an empty pit, which concludes the turn unless a capture occurs. \n" +
            "(3) A capture takes place when the last stone lands in an empty pit on the player's side, and there are stones in \n" +
            "the pit directly opposite. In such a case, all of the opponent's stones from the opposite pit are captured and \n" +
            "placed in the player's store. The final stone from the player's own side is not captured; it remains in play. \n" +
            "If the opposite pit has no stones, the move ends, and no stones are captured. Additionally, if the final stone \n" +
            "lands on the opponent's side, no stones are captured. \n",
            "Ayo Rules", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}