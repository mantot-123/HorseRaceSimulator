import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;

public class RaceGUI {
    private JFrame frame = new JFrame("Ongoing race...");

    private JPanel topPanel = new JPanel(new GridLayout(0, 2));
    private JPanel subPanel1 = new JPanel(new GridLayout(0, 1));
    private JPanel subPanel2 = new JPanel(new  GridLayout(1, 2));

    private JPanel horseLanePanel = new JPanel(new GridLayout(0, 1, 10, 10));
    private JPanel optionsPanel = new JPanel(new GridLayout(1, 0));
    
    private TrackTypesFile trackTypesFile = new TrackTypesFile();
    private Vector<TrackType> trackTypes = new Vector<TrackType>(trackTypesFile.getTrackTypes().values());

    private JComboBox<TrackType> trackComboBox  = new JComboBox<TrackType>(trackTypes);
    private ArrayList<JTextField> horseLaneLabels = new ArrayList<JTextField>();
    private ArrayList<JLabel> topPanelHorseLabels = new ArrayList<JLabel>();

    private JButton startRaceBtn = new JButton("Start race");
    private JButton openBettingsBtn = new JButton("Open bettings");
    private JButton openPastRacesBtn = new JButton("Open past races");
    private JButton openHorsesListBtn = new JButton("List of horses");

    private RaceInfo race;
    private ArrayList<HorseV2> laneHorses = new ArrayList<HorseV2>();
    private HorseV2 winningHorse;

    private BetHistoryDisplay betHistoryDisplay = new BetHistoryDisplay();

    public RaceGUI(int raceLength) {
        if(raceLength < 10) {
            throw new IllegalArgumentException("ERROR: Please enter a valid race length. It must be a whole number, and it has to be at least 10 blocks long.");
        }

        this.race = new RaceInfo(raceLength);
    }

    // Override
    public RaceGUI(int raceLength, ArrayList<HorseV2> horses) {
        this.race = new RaceInfo(raceLength);
        this.laneHorses = horses;
    }

    public RaceInfo getRaceInfo() {
        return this.race;
    }

    public ArrayList<HorseV2> getLaneHorses() {
        return this.laneHorses;
    }

    public void resetWinner() {
        this.winningHorse = null;
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
        try {
            if(horseToAdd == null) {
                throw new NullPointerException("Horse to add cannot be null.");
            }

            if(this.laneHorses.contains(horseToAdd)) {
                int laneNo = this.laneHorses.indexOf(horseToAdd) + 1;
                throw new IllegalArgumentException("ERROR: The horse " + horseToAdd.getName() + " is already added to lane " + laneNo + ". Please add a different horse.");
            }

            this.laneHorses.add(horseToAdd);

        } catch(NullPointerException | IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Opens the window showing the ongoing horse race
    public void loadRaceFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        // Adjusts the width of the frame based on the race track length
        this.frame.setSize(58 * this.race.getRaceLength(), 350);
        // Locks the size of the frame so it can't be resized
        this.frame.setResizable(false);

        this.frame.add(this.topPanel, BorderLayout.NORTH);
        this.frame.add(this.horseLanePanel, BorderLayout.CENTER);
        this.frame.add(this.optionsPanel, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }

    // Adds a horse's name and confidence to the top panel
    public void addHorseToTopPanel(HorseV2 horse) {
        JLabel horseLabel = new JLabel(horse.getName() + " (Current confidence: " + String.format("%.2f", (horse.getConfidence()*100)) + "%)");
        horseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subPanel1.add(horseLabel);
        topPanelHorseLabels.add(horseLabel);
        frame.revalidate();
        frame.repaint();
    }

    // Reloads all of the horse name and confidence labels in the top panel
    public void reloadHorseLabelsTopPanel() {
        for(int i = 0; i < topPanelHorseLabels.size(); i++) {
            HorseV2 horse = this.laneHorses.get(i);
            JLabel horseLabel = topPanelHorseLabels.get(i);
            horseLabel.setText(horse.getName() + " (Current confidence: " + String.format("%.2f", (horse.getConfidence()*100)) + "%)");
        }
        frame.revalidate();
        frame.repaint();
    }

    // Loads the track options panel
    public void loadTrackSettingsPanel() {
        JLabel label1 = new JLabel("Track type:");
        subPanel2.add(label1);
        subPanel2.add(trackComboBox);
        frame.revalidate();
        frame.repaint();
    }

    public void loadTopPanel() {
        topPanel.add(subPanel1);
        topPanel.add(subPanel2);
    }

    // Creates a JLabel object to show a lane horse + adds it to the lane panel
    public void addHorseToLanePanel(HorseV2 horse) {
        JTextField newLaneHorse = new JTextField(15);
        newLaneHorse.setFont(new Font("Arial", Font.BOLD, 14));
        newLaneHorse.setText(Character.toString(horse.getSymbol()));
        newLaneHorse.setEditable(false);
        horseLanePanel.add(newLaneHorse);
        horseLaneLabels.add(newLaneHorse);
    }

    // Loads the options panel for the race
    public void loadOptionsPanel() {
        startRaceBtn.addActionListener(e -> {
            this.race.setTrackType((TrackType)trackComboBox.getSelectedItem());
            resetWinner();
            simulateRace();
        });

        openBettingsBtn.addActionListener(e -> {
            betHistoryDisplay.showFrame();
        });

        openPastRacesBtn.addActionListener(e -> {
            PastRacesListDisplay pastRacesDisplay = new PastRacesListDisplay();
        });

        openHorsesListBtn.addActionListener(e -> {
            HorsesListDisplay horsesListDisplay  = new HorsesListDisplay();
        });

        optionsPanel.add(startRaceBtn);
        optionsPanel.add(openBettingsBtn);
        optionsPanel.add(openPastRacesBtn);
        optionsPanel.add(openHorsesListBtn);
    }

    public void startRaceGUI() {
        try {
            if(laneHorses.size() < 2) {
                JOptionPane.showMessageDialog(null, "Cannot start race because there are not enough horses to join the race. At least 2 horses are required to start the race", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Cannot start race because there are not enough horses to join the race. At least 2 horses are required to start the race");
            }

            // Horses' details added to the top panel
            for(HorseV2 horse: this.laneHorses) {
                addHorseToTopPanel(horse);
            }

            loadTrackSettingsPanel();

            // Lane horses added
            for(HorseV2 horse: this.laneHorses) {
                addHorseToLanePanel(horse);
            }

            loadTopPanel();
            loadOptionsPanel();
            loadRaceFrame();
        } catch(IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Simulates the horse race when called.
    private void simulateRace() {
        startRaceBtn.setEnabled(false);
        
        // Temporary test code. This can be removed....
        System.out.println("Track type: " + this.race.getTrackType().toString());
        for(HorseV2 horse: this.getLaneHorses()) {
            System.out.println("Horse ID: " + horse.getId() + ", name: " + horse.getName() + ", symbol: " + horse.getSymbol() + ", confidence: " + horse.getConfidence() + ", equipment: " + horse.getEquipment().toString());
        }

        // Puts all the horses back to their starting position + resets their fallen status + adds the number of games played by 1
        for(HorseV2 horse : this.laneHorses) {
            horse.goBackToStart();
            horse.resetFallen();
            horse.addGamePlayed();
        }

        ElapsedTime elapsedTime = new ElapsedTime();

        elapsedTime.start();
        Timer timer = new Timer(100, e -> {
            // Loop until a horse wins or all horses fall
            for(HorseV2 horse: this.laneHorses) {
                moveHorse(horse);
            }

            for(HorseV2 horse: this.laneHorses) {
                returnLaneText(horse);
            }

            for(HorseV2 horse: this.laneHorses) {
                if(raceWonBy(horse)) {
                    elapsedTime.finish();
                    double elapsedTimeSeconds = elapsedTime.getElapsedTime();
                    JOptionPane.showMessageDialog(null, "The winner is: " + horse.getName() + "\nElapsed time: " + String.format("%.2f", elapsedTimeSeconds) + " seconds.");
                    HorsesListFile horsesFile = new HorsesListFile();
                    horsesFile.saveMultipleHorses(laneHorses, false);

                    PastRacesFile pastRacesFile = new PastRacesFile();
                    PastRace pastRace = new PastRace(this.race.getRaceLength(), this.winningHorse, elapsedTimeSeconds);
                    pastRace.setTrackType(this.race.getTrackType());
                    pastRacesFile.savePastRace(pastRace);

                    markBets(); 
                    reloadBetHistoryWindow();
                    reloadHorseLabelsTopPanel(); // Reload all of the horse labels in the top panel

                    startRaceBtn.setEnabled(true);
                    ((Timer)e.getSource()).stop(); // Stops the timer
                }
            }

            if(this.allHorsesFallen()) {
                elapsedTime.finish();
                double elapsedTimeSeconds = elapsedTime.getElapsedTime();
                JOptionPane.showMessageDialog(null, "All horses have fallen. There is NO WINNER.\nElapsed time: " + String.format("%.2f", elapsedTimeSeconds) + " seconds.");
                HorsesListFile horsesFile = new HorsesListFile();
                horsesFile.saveMultipleHorses(laneHorses, false);

                PastRacesFile pastRacesFile = new PastRacesFile();
                PastRace pastRace = new PastRace(this.race.getRaceLength(), null, elapsedTimeSeconds);
                pastRace.setTrackType(this.race.getTrackType());
                pastRacesFile.savePastRace(pastRace);

                markBets(); // Mark the bets as won or lost
                reloadBetHistoryWindow();
                reloadHorseLabelsTopPanel(); // Reload all of the horse labels in the top panel

                startRaceBtn.setEnabled(true);
                ((Timer)e.getSource()).stop();
            }

        });

        timer.start();
        frame.revalidate();
        frame.repaint();
    }

    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(HorseV2 theHorse)
    {
        // checks if the horse has raced the full distance + is not down + there is not already a winner determined yet
        if (theHorse.getDistanceTravelled() >= this.race.getRaceLength() && !theHorse.hasFallen() && this.winningHorse == null)
        {
            this.winningHorse = theHorse;
            theHorse.win(); // Increase the horse's confidence rating + win count
            return true;
        }
        else
        {
            return false;
        }
    }

    private void returnLaneText(HorseV2 theHorse) {
        int noOfSpacesBefore = theHorse.getDistanceTravelled() * 14;

        String spacesBefore = returnCharSequence(' ', noOfSpacesBefore);
        String lane = spacesBefore;

        // Get the position of the horse in the lane
        int horsePosition = this.laneHorses.indexOf(theHorse);
        JTextField hLabel = horseLaneLabels.get(horsePosition);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            hLabel.setText(lane + '\u2322');
        }
        else
        {
            hLabel.setText(lane + theHorse.getSymbol());
        }

        frame.revalidate();
        frame.repaint();
    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private String returnCharSequence(char aChar, int times)
    {
        int i = 0;
        String seq = "";
        while (i < times)
        {
            seq += aChar;
            i = i + 1;
        }
        return seq;
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(HorseV2 theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                // When a horse moves, there is a chance that the horse will move twice as fast
                // The chance is based on the track conditions and modifier values for the horse
                if(Math.random() < (this.race.getTrackType().getBaseFastMoveProb() + theHorse.getEquipment().getMovementAmp())) {
                    for(int i = 1; i <= 2; i++)
                        theHorse.moveForward();
                } else {
                    theHorse.moveForward();
                }
            }
            
            //the probability that the horse will fall is very small (max is base probability of the track)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2

            // Probability is based on the track and the horse's modifier values
            if (Math.random() < ((this.race.getTrackType().getBaseFallProb() - theHorse.getEquipment().getStabilityAmp())*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }

    // Marks bets as won or lost after each race
    private void markBets() {
        // TODO = MARK BETS AS WON OR LOST AND SAVE THEM HERE
        ArrayList<Bet> bets = this.betHistoryDisplay.getBetsArrayList();
 
        // Check if there is no winning horse, then lose all the pending bets
        if(winningHorse == null) {
            for(Bet bet: bets) {
                if(bet.getStatus() == 0) {
                    bet.lose();
                }
            }
        } else {
            for(Bet bet: bets) {
                if(bet.getStatus() == 0) {
                    if(bet.getHorse().getId().equals(winningHorse.getId())) {
                        bet.win();
                    } else {
                        bet.lose();
                    }
                }
            }
        }

        this.betHistoryDisplay.getBetHistoryFile().saveMultipleBets(bets, false);
    }

    // Reload the bet history window with the new bets and new horse data
    private void reloadBetHistoryWindow() {
        this.betHistoryDisplay.reloadPlaceBetForm(); 
        this.betHistoryDisplay.reloadBetHistoryList();
    }
}
