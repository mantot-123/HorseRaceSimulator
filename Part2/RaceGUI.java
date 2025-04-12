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

    public RaceGUI() {
        openRaceMenuWindow();
    }

    public void openRaceMenuWindow() {
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1000, 550);
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

        startRaceButton.addActionListener(e -> {
            // Check for any empty horse name and symbol fields
            for(JTextField nameField: this.horseNameFields) {
                String horseName = nameField.getText();
                if(horseName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ERROR: One of your horses' names are empty! Please enter a name for all horses.");
                    return;
                }
            }

            for(JTextField symbolField: this.horseSymbolFields) {
                String horseSymbol = symbolField.getText();
                if(horseSymbol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ERROR: One of your horses' symbols are empty! Please enter a symbol for all horses.");
                    return;
                } else if(horseSymbol.length() > 1) {
                    JOptionPane.showMessageDialog(null, "ERROR: You can only use 1 character as a symbol for all your horses. Check your horses and try again.");
                }
            }
            
            setRaceConfiguration();
        });

        panel2.add(startRaceButton);
        panel2.add(cancelButton);

        menuFrame.add(panel1, BorderLayout.CENTER);
        menuFrame.add(panel2, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
    }

    public void setRaceConfiguration() {
        // Add all of the horses in the fields to the participating horses list ("laneHorses")
        for(int i = 0; i < horseNameFields.size(); i++) {
            String horseName = this.horseNameFields.get(i).getText();
            char horseSymbol = this.horseSymbolFields.get(i).getText().charAt(0);
            double initialConfidence = 0.5; // Starting confidence rating
            HorseV2 newHorse = new HorseV2(horseSymbol, horseName, initialConfidence);
            this.laneHorses.add(newHorse);
        }

        // Temporary test code. This can be removed....
        for(HorseV2 horse: this.laneHorses) {
            System.out.println("Horse name: " + horse.getName() + ", symbol: " + horse.getSymbol() + ", confidence: " + horse.getConfidence());
        }
    }

    public void addHorse(HorseV2 horseToAdd) {
        if(!this.laneHorses.contains(horseToAdd)) {
            this.laneHorses.add(horseToAdd);
        } else{
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
