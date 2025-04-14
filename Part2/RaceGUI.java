import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class RaceGUI {
    JFrame frame = new JFrame("Ongoing race...");
    JPanel horseLanePanel = new JPanel(new GridLayout(0, 1, 10, 10));
    JPanel optionsPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    ArrayList<JLabel> horseLanes = new ArrayList<JLabel>();

    private int raceLength;
    private ArrayList<HorseV2> laneHorses = new ArrayList<HorseV2>();
    private HorseV2 winningHorse;

    public RaceGUI(int raceLength) {
        this.raceLength = raceLength;
    }

    public ArrayList<HorseV2> getLaneHorses() {
        return this.laneHorses;
    }

    public boolean allHorsesFallen() {
        for (HorseV2 horse : this.laneHorses) {
            if (!horse.hasFallen()) {
                return false;
            }
        }
        return true; 
    }

    public void addHorse(HorseV2 horseToAdd) {
        if(!this.laneHorses.contains(horseToAdd)) {
            this.laneHorses.add(horseToAdd);
        } else {
            int laneNo = this.laneHorses.indexOf(horseToAdd) + 1;
            JOptionPane.showMessageDialog(null, "ERROR: The horse " + horseToAdd.getName() + " is already added to lane " + laneNo + ". Please add a different horse.");
            return;
        }
    }

    // Opens the window showing the ongoing horse race
    public void loadRaceFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        // Adjusts the width of the frame based on the race track length
        this.frame.setSize(50 * this.raceLength, 500);
        // Locks the size of the frame so it can't be resized
        this.frame.setResizable(false);

        this.frame.add(this.horseLanePanel, BorderLayout.CENTER);
        this.frame.add(this.optionsPanel, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }

    // Loads the options panel for the race
    public void loadOptionsPanel() {
        JButton openBettingsBtn = new JButton("Open bettings");
        JButton openResultsBtn = new JButton("Open results");
        optionsPanel.add(openBettingsBtn);
        optionsPanel.add(openResultsBtn);
    }

    // Creates a JLabel object to show a lane horse
    public void addHorseToLanePanel(HorseV2 horse) {
        JLabel newLaneHorse = new JLabel(Character.toString(horse.getSymbol()));
        horseLanePanel.add(newLaneHorse);
        horseLanes.add(newLaneHorse);
    }

    public void startRaceGUI() {
        for(HorseV2 horse: this.laneHorses) {
            addHorseToLanePanel(horse);
        }
        loadOptionsPanel();
        loadRaceFrame();
    }

}
