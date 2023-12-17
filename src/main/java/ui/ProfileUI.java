package ui; 
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import mancala.Player;
import mancala.UserProfile;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * A component for displaying a certain players stats.
 */
public class ProfileUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private UserProfile playerProfile;
    private Player player;

    public ProfileUI(final Player newPlayer) {
        super();
        this.player = newPlayer;
        this.playerProfile = newPlayer.getProfile();
        buildUI();
        setMaximumSize(new Dimension(450, 85));
        setPreferredSize(new Dimension(350, 85));
    }

    /**
     * Sets a new player and updates the display based on the players stats.
     * 
     * @param newPlayer The new player to display.
     */
    public void updatePlayer(final Player newPlayer) {
        this.player = newPlayer;
        this.playerProfile = newPlayer.getProfile();
        removeAll();
        buildUI();
        revalidate();
        repaint();
    }

    /**
     * Gets the player currently being displayed
     * 
     * @return The player currently being displayed.
     */
    public Player getPlayer() {
        return this.player;
    }

    protected void buildUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Display stats vertically
        setBorder(BorderFactory.createLineBorder(Color.black));
        buildStats();
    }

    protected void buildStats() {
        final JLabel name = new JLabel("Name: " + this.playerProfile.getUserName());
        name.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        final JLabel kalahGames = new JLabel("Kalah Games: " + String.valueOf(this.playerProfile.getKalahGames()));
        kalahGames.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        final JLabel kalahWins = new JLabel("Kalah Wins: " + String.valueOf(this.playerProfile.getKalahWins()));
        kalahWins.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        final JLabel ayoGames = new JLabel("Ayo Games: " + String.valueOf(this.playerProfile.getAyoGames()));
        ayoGames.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        final JLabel ayoWins = new JLabel("Ayo Wins: " + String.valueOf(this.playerProfile.getAyoWins()));
        ayoWins.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(name);
        add(kalahGames);
        add(kalahWins);
        add(ayoGames);
        add(ayoWins);
    }
}