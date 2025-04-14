import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class RaceGUI {
    JFrame frame = new JFrame("Ongoing race...");

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
        this.frame.setVisible(true);
    }

    public void startRaceGUI() {
        loadRaceFrame();
    }

}
