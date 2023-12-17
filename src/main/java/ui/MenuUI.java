package ui; 
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import mancala.Player;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import mancala.Saver;
import java.io.IOException;

/**
 * A window representative of all components in the main menu.
 */
public class MenuUI extends JPanel {
    private static final long serialVersionUID = 1L;
    final private GameWindow window;
    protected ProfileUI containerOne;
    protected ProfileUI containerTwo;

    public MenuUI(final GameWindow newWindow) {
        super();
        this.window = newWindow;
        this.window.setCurrentBoard(null);
        createUI();
    }

    protected void createUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Two different game rulesets & two different players 
        buildPlayerButton(this.window.getPlayerOne(), true); 
        buildPlayerButton(this.window.getPlayerTwo(), false); 
        buildContinueButton();
    }

    protected void buildContinueButton() {
        final JPanel continueContainer = new JPanel();
        final JLabel continueLabel = new JLabel("To start a game, click the \"Game\" menu on the menu bar!");
        continueContainer.add(continueLabel);
        add(continueContainer);
    }

    protected void buildPlayerButton(final Player player, final boolean playerOne) {
        final JPanel playerContainer = new JPanel();
        playerContainer.setMaximumSize(new Dimension(650, 85));
        playerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        final ProfileUI statsContainer = new ProfileUI(player);

        final JButton loadPlayer = new JButton("Load player");
        loadPlayer.setPreferredSize(new Dimension(150, 85));
        loadPlayer.setMaximumSize(new Dimension(150, 85));
        final JButton changeName = new JButton("Change name");
        changeName.setPreferredSize(new Dimension(150, 85));
        changeName.setMaximumSize(new Dimension(150, 85));

        loadPlayer.addActionListener(e -> handleLoadListener(playerOne, statsContainer));
        changeName.addActionListener(e -> handleNameListener(playerOne, statsContainer));

        playerContainer.add(loadPlayer);
        playerContainer.add(statsContainer);
        playerContainer.add(changeName);
        add(playerContainer);
    }

    protected void handleLoadListener(final boolean playerOne, final ProfileUI container) {
        final JFileChooser chooser = new JFileChooser("assets");
        final int returnVal = chooser.showOpenDialog(window);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                final Player loadedPlayer = (Player) Saver.loadObject(chooser.getSelectedFile().getName());
                if (playerOne) {
                    window.setPlayerOne(loadedPlayer);
                    container.updatePlayer(window.getPlayerOne());
                } else {
                    window.setPlayerTwo(loadedPlayer);
                    container.updatePlayer(window.getPlayerTwo());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to load player!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(null, "Save file is not a player!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An unknown error has occured!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void handleNameListener(final boolean playerOne, final ProfileUI container) {
        final String newName = JOptionPane.showInputDialog("Please enter a name: ");
        if (newName != null && !newName.isEmpty()) {
            window.changePlayerName(playerOne, newName);
            if (playerOne) {
                container.updatePlayer(window.getPlayerOne());
            } else {
                container.updatePlayer(window.getPlayerTwo());
            }
        }
    }
}