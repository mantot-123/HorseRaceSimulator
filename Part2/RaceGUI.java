import java.awt.*;
import java.util.*;
import javax.swing.*;

public class RaceGUI {
    private int raceLength;
    private ArrayList<HorseV2> laneHorses = new ArrayList<HorseV2>();
    private HorseV2 winningHorse;

    public RaceGUI(int raceLength) {
        this.raceLength = raceLength;
    }

    public ArrayList<HorseV2> getLaneHorses() {
        return this.laneHorses;
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

    public void startRace() {
        
    }

    // Opens the window showing the ongoing horse race
    public void openRaceFrame() {

    }
}
