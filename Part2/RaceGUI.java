import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class RaceGUI {
    JFrame frame = new JFrame("Ongoing race...");

    JPanel topPanel = new JPanel(new GridLayout(0, 1));
    JPanel horseLanePanel = new JPanel(new GridLayout(0, 1, 10, 10));
    JPanel optionsPanel = new JPanel(new GridLayout(1, 0, 10, 10));

    ArrayList<JTextField> horseLaneLabels = new ArrayList<JTextField>();
    ArrayList<JLabel> topPanelLabels = new ArrayList<JLabel>();

    private int raceLength;
    private ArrayList<HorseV2> laneHorses = new ArrayList<HorseV2>();
    private HorseV2 winningHorse;

    public RaceGUI(int raceLength) {
        this.raceLength = raceLength;
    }

    // Override
    public RaceGUI(int raceLength, ArrayList<HorseV2> horses) {
        this.raceLength = raceLength;
        this.laneHorses = horses;
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
        this.frame.setSize(58 * this.raceLength, 350);
        // Locks the size of the frame so it can't be resized
        this.frame.setResizable(false);

        this.frame.add(this.topPanel, BorderLayout.NORTH);
        this.frame.add(this.horseLanePanel, BorderLayout.CENTER);
        this.frame.add(this.optionsPanel, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }

    // Adds a horse's name and confidence to the top panel
    public void addHorseToTopPanel(HorseV2 horse) {
        JLabel horseLabel = new JLabel(horse.getName() + " (Current confidence: " + (horse.getConfidence()*100) + "%)");
        horseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(horseLabel);
        topPanelLabels.add(horseLabel);
        frame.revalidate();
        frame.repaint();
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
        JButton startRaceBtn = new JButton("Start race");
        JButton openBettingsBtn = new JButton("Open bettings");
        JButton openResultsBtn = new JButton("Open results");
        startRaceBtn.addActionListener(e -> {
            resetWinner();
            showRace();
        });

        openBettingsBtn.addActionListener(e -> {
            ViewBets viewBets = new ViewBets();
        });

        optionsPanel.add(startRaceBtn);
        optionsPanel.add(openBettingsBtn);
        optionsPanel.add(openResultsBtn);
    }

    public void startRaceGUI() {
        // Horses' details added to the top panel
        for(HorseV2 horse: this.laneHorses) {
            addHorseToTopPanel(horse);
        }

        // Lane horses added
        for(HorseV2 horse: this.laneHorses) {
            addHorseToLanePanel(horse);
        }

        loadOptionsPanel();
        loadRaceFrame();
    }

    // Simulates the horse race when called.
    private void showRace() {
        // Temporary test code. This can be removed....
        for(HorseV2 horse: this.getLaneHorses()) {
            System.out.println("Horse ID: " + horse.getId() + ", name: " + horse.getName() + ", symbol: " + horse.getSymbol() + ", confidence: " + horse.getConfidence());
        }

        boolean finished = false;

        // Puts all the horses back to their starting position + resets their fallen status
        for(HorseV2 horse : this.laneHorses) {
            horse.goBackToStart();
            horse.resetFallen();
        }

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
                    JOptionPane.showMessageDialog(null, "The winner is: " + horse.getName());
                    ((Timer)e.getSource()).stop(); // Stops the timer
                }
            }

            if(this.allHorsesFallen()) {
                JOptionPane.showMessageDialog(null, "All horses have fallen. There is NO WINNER.");
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
        if (theHorse.getDistanceTravelled() >= raceLength && !theHorse.hasFallen() && this.winningHorse == null)
        {
            this.winningHorse = theHorse;
            theHorse.win(); // Increase the horse's confidence rating
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
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
}
