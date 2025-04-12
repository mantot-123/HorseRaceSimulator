import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class RaceGUI {
    private int raceLength;
    private ArrayList<HorseV2> laneHorses = new ArrayList<HorseV2>();
    private HorseV2 winningHorse;

    public RaceGUI(int raceLength) {
        this.raceLength = raceLength;
    }

    public void addHorse(HorseV2 horseToAdd) {
        if(!laneHorses.contains(horseToAdd)) {
            laneHorses.add(horseToAdd);
        } else{
            int laneNo = laneHorses.indexOf(horseToAdd) + 1;
            JOptionPane.showMessageDialog(null, "ERROR: The horse " + horseToAdd.getName() + " is already added to lane " + laneNo + ". Please add a different horse.");
            return;
        }
    }

    public void startRace() {
        
    }

    // Opens the window showing the ongoing horse race
    public void openRaceFrame() {

    }
}
