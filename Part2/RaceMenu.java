import java.util.ArrayList;
import java.util.Vector;
import javax.management.openmbean.OpenDataException;
import javax.swing.*;
import java.awt.*;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

public class RaceMenu {
    RaceGUI race;
    private JFrame menuFrame = new JFrame("New race");
    private JFrame raceFrame = new JFrame("Race in progress...");
    private ArrayList<JTextField> horseNameFields = new ArrayList<JTextField>();
    private ArrayList<JTextField> horseSymbolFields = new ArrayList<JTextField>();
    private ArrayList<JComboBox<Equipment>> horseEquipmentFields = new ArrayList<JComboBox<Equipment>>();
    
    private int raceLengthInput = 0;
    private int horseCount = 0;

    public RaceMenu() {
        HorsesListFile horsesFile = new HorsesListFile();
        RaceInfoFile raceInfoFile = new RaceInfoFile();
        try {
            if(horsesFile.getHorsesList().isEmpty()&& raceInfoFile.getRace().getRaceLength() == 0) {
                openRaceMenuWindow();
            } else if(horsesFile.getHorsesList().isEmpty() ^ raceInfoFile.getRace().getRaceLength() == 0) { // Check if ONE of the files have been corrupted, then if yes, clear all the saved progress
                JOptionPane.showMessageDialog(null, "WARNING: One of your saved race and saved horses files has been corrupted.\nYour saved horses and race data have been cleared. You will have to create a new race again.", "Warning", JOptionPane.ERROR_MESSAGE);
                horsesFile.clearSavedHorses();
                raceInfoFile.clearRaceInfo();
                openRaceMenuWindow();
            } else {
                int loadProgressConfirm = JOptionPane.showConfirmDialog(null, "You currently have some saved horses. Would you like to load them?\nNOTE: Clicking 'no' will clear all your already saved horses. Choose wisely!", "Load progress", JOptionPane.YES_NO_CANCEL_OPTION);
                switch(loadProgressConfirm) {
                    case 0:
                        this.race = new RaceGUI(raceInfoFile.getRace().getRaceLength(), horsesFile.getHorsesList());
                        this.confirmStartRace();
                        break;
                    case 1:
                        horsesFile.clearSavedHorses();
                        raceInfoFile.clearRaceInfo();
                        openRaceMenuWindow();
                        break;
                    default:
                        break;
                }
            }
        } catch(IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "WARNING: One of your saved race and saved horses files has been corrupted.\nYour saved horses and race data have been cleared. You will have to create a new race again.", "Warning", JOptionPane.ERROR_MESSAGE);
            horsesFile.clearSavedHorses();
            raceInfoFile.clearRaceInfo();
            openRaceMenuWindow();
        }
    }

    public void openRaceMenuWindow() {
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1000, 700);
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

            EquipmentFile equipmentFile = new EquipmentFile();
            Vector<Equipment> equipmentChoices = new Vector<Equipment>(equipmentFile.getEquipmentList().values());

            JComboBox<Equipment> horseEquipmentField = new JComboBox<Equipment>(equipmentChoices);

            horseNameFields.add(horseNameField);
            horseSymbolFields.add(horseSymbolField);
            horseEquipmentFields.add(horseEquipmentField);

            panel1.add(new JLabel("Horse #" + horseCount + " name: "));
            panel1.add(horseNameField);
            panel1.add(new JLabel("Horse #" + horseCount + " symbol: "));
            panel1.add(horseSymbolField);
            panel1.add(new JLabel("Horse #" + horseCount + " equipment: "));
            panel1.add(horseEquipmentField);

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
            // Check if the racetrack length is a valid number + if it's at least 10 blocks long
            try {
                raceLengthInput = Integer.parseInt(raceLengthField.getText());
                if(raceLengthInput < 10) {
                    throw new NumberFormatException();
                }

                // Check if there are at least 2 horses to start the race
                if(horseCount < 2) {
                    throw new IllegalArgumentException("You must have at least 2 horses before starting the race.");
                }

                // Check for any empty horse name and symbol fields
                for(JTextField nameField: this.horseNameFields) {
                    String horseName = nameField.getText();
                    if(horseName.isEmpty()) {
                        throw new IllegalArgumentException("One of your horses' names are empty! Please enter a name for all horses.");
                    }
                }

                for(JTextField symbolField: this.horseSymbolFields) {
                    String horseSymbol = symbolField.getText();
                    if(horseSymbol.isEmpty()) {
                        throw new IllegalArgumentException("One of your horses' symbols are empty! Please enter a symbol for all horses.");
                    } else if(horseSymbol.length() > 1) {
                        throw new IllegalArgumentException("You can only use 1 character as a symbol for all your horses. Check your horses and try again.");
                    }
                }
    
                raceLengthInput = Integer.parseInt(raceLengthField.getText());
                setRaceConfiguration();

            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid race length. It must be a whole number, and it has to be at least 10 blocks long.", "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        panel2.add(startRaceButton);
        panel2.add(cancelButton);

        menuFrame.add(panel1, BorderLayout.CENTER);
        menuFrame.add(panel2, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
    }

    public void setRaceConfiguration() {
        // Add all of the horses in the fields to the participating horses list ("laneHorses")
        this.race = new RaceGUI(this.raceLengthInput);

        for(int i = 0; i < horseNameFields.size(); i++) {
            String horseName = this.horseNameFields.get(i).getText();
            char horseSymbol = this.horseSymbolFields.get(i).getText().charAt(0);
            Equipment horseEquipment = (Equipment)this.horseEquipmentFields.get(i).getSelectedItem();
            double initialConfidence = 0.5; // Starting confidence rating
            HorseV2 newHorse = new HorseV2(horseSymbol, horseName, initialConfidence, horseEquipment);
            this.race.addHorse(newHorse);
        }

        HorsesListFile horsesFile = new HorsesListFile();
        horsesFile.saveMultipleHorses(this.race.getLaneHorses(), false);

        RaceInfoFile raceInfoFile = new RaceInfoFile();
        raceInfoFile.saveRaceInfo(this.race.getRaceInfo());

        this.confirmStartRace();
    }

    public void confirmStartRace() {
        this.race.startRaceGUI();
    }
}
