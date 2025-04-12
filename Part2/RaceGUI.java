import java.awt.*;
import java.util.*;
import javax.swing.*;

public class RaceGUI {
    private JFrame menuFrame = new JFrame("New race");
    private JFrame raceFrame = new JFrame("Race in progress...");
    private ArrayList<JTextField> horseNameFields = new ArrayList<JTextField>();
    private ArrayList<JTextField> horseSymbolFields = new ArrayList<JTextField>();
    int horseCount = 0;

    private int raceLength;
    private ArrayList<HorseV2> laneHorses = new ArrayList<HorseV2>();
    private HorseV2 winningHorse;

    public RaceGUI(int raceLength) {
        openRaceMenuWindow();
    }

    public void openRaceMenuWindow() {
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1000, 500);
        menuFrame.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField raceLengthField = new JTextField(15);
        JButton addHorseBtn = new JButton("Add horse ");

        addHorseBtn.addActionListener(e -> {
            if(horseCount >= 6) {
                JOptionPane.showMessageDialog(null, "ERROR: You can only have up to 6 horses in the race");
                return;
            }

            horseCount++;
            JTextField horseNameField = new JTextField(15);
            JTextField horseSymbolField = new JTextField(15);

            horseNameFields.add(horseNameField);
            horseSymbolFields.add(horseSymbolField);

            panel1.add(new JLabel("Horse #" + horseCount + " name: "));
            panel1.add(horseNameField);
            panel1.add(new JLabel("Horse #" + horseCount + " symbol: "));
            panel1.add(horseSymbolField);

            panel1.revalidate();
            panel1.repaint();
        });
        
        panel1.add(new JLabel("Race length: "));
        panel1.add(raceLengthField);
        panel1.add(new JLabel("Horses: "));
        panel1.add(addHorseBtn);

        JPanel panel2 = new JPanel(new GridLayout(2, 1));
        JButton startRaceButton = new JButton("Start race");
        JButton cancelButton = new JButton("Cancel");

        panel2.add(startRaceButton);
        panel2.add(cancelButton);

        menuFrame.add(panel1, BorderLayout.CENTER);
        menuFrame.add(panel2, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
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
