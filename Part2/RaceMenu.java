import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;

public class RaceMenu {
    RaceGUI race;

    private EquipmentFile equipmentFile = new EquipmentFile();
    private Vector<Equipment> equipmentList = new Vector<Equipment>(equipmentFile.getEquipmentList().values());

    private JFrame menuFrame = new JFrame("New race");

    JPanel panel1 = new JPanel(new GridLayout(0, 2));
    JPanel panel2 = new JPanel(new GridLayout(0, 1));
    JPanel panel3 = new JPanel(new GridLayout(0, 2));

    private JTextField raceLengthField = new JTextField(15);
    private JTextField horseNameField = new JTextField(15);
    private JTextField horseSymbolField = new JTextField(15);
    private JComboBox<Equipment> horseEquipmentField = new JComboBox<Equipment>(equipmentList); // Load horse equipment into the combo box
    
    private DefaultListModel<HorseV2> horsesListModel = new DefaultListModel<HorseV2>();
    private JList<HorseV2> horsesListDisplay = new JList<HorseV2>(horsesListModel);

    private int raceLengthInput = 0;

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

    public void loadTopPanel() {
        JButton addHorseBtn = new JButton("Add horse");
        JButton removeHorseBtn = new JButton("Remove selected horse");

        panel1.add(new JLabel("Race length: "));
        panel1.add(raceLengthField);
        panel1.add(new JLabel("Horse name: "));
        panel1.add(horseNameField);
        panel1.add(new JLabel("Horse symbol: "));
        panel1.add(horseSymbolField);
        panel1.add(new JLabel("Horse equipment: "));
        panel1.add(horseEquipmentField);

        addHorseBtn.addActionListener(e -> {
            try {
                if(horseNameField.getText().isEmpty())
                    throw new IllegalArgumentException("Please enter a name for your horse.");

                if(horseSymbolField.getText().isEmpty())
                    throw new IllegalArgumentException("Please enter a symbol for your horse.");

                if(horseSymbolField.getText().length() > 1)
                    throw new IllegalArgumentException("You can only use 1 character as a symbol for your horse. Please try again.");
                
                // Check if the name and symbol are already taken by another horse
                for(int i = 0; i < horsesListModel.getSize(); i++) {
                    HorseV2 horse = horsesListModel.getElementAt(i);
                    if(horse.getName().equalsIgnoreCase(horseNameField.getText()))
                        throw new IllegalArgumentException("That horse name is already taken. Please try another one.");
                    
                    if(horse.getSymbol() == horseSymbolField.getText().charAt(0))
                        throw new IllegalArgumentException("That horse symbol is already taken. Please try another one.");
                }
                

                String horseName = horseNameField.getText();
                char horseSymbol = horseSymbolField.getText().charAt(0);
                Equipment horseEquipment = (Equipment)horseEquipmentField.getSelectedItem();
                double initialConfidence = 0.5; // Starting confidence rating
                HorseV2 newHorse = new HorseV2(horseSymbol, horseName, initialConfidence, horseEquipment);
                horsesListModel.addElement(newHorse);

                horseNameField.setText("");
                horseSymbolField.setText("");
                horseEquipmentField.setSelectedIndex(0);

            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        removeHorseBtn.addActionListener(e -> {
            try {
                if(horsesListDisplay.getSelectedValue() == null)
                    throw new IllegalArgumentException("Please select a horse to remove.");

                horsesListModel.removeElement(horsesListDisplay.getSelectedValue());
            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        panel1.add(addHorseBtn);
        panel1.add(removeHorseBtn);
    }

    public void loadCenterPanel() {
        panel2.add(horsesListDisplay);
    }

    public void loadBottomPanel() {
        JButton startRaceButton = new JButton("Start race");
        JButton cancelButton = new JButton("Cancel");

        startRaceButton.addActionListener(e -> {
            try {
                raceLengthInput = Integer.parseInt(raceLengthField.getText());
                if(raceLengthInput < 10) {
                    throw new NumberFormatException();
                }

                if(horsesListModel.getSize() < 2) {
                    throw new IllegalArgumentException("You need to have at least 2 horses to start the race.");
                }

                setRaceConfiguration();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid racetrack length. It has to be a valid number and at least 10 blocks long.", "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } 
        });

        cancelButton.addActionListener(e -> {
            this.menuFrame.dispose();
        });

        panel3.add(startRaceButton);
        panel3.add(cancelButton);
    }

    public void openRaceMenuWindow() {
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(380, 350);
        menuFrame.setLayout(new BorderLayout());

        loadTopPanel();
        loadCenterPanel();
        loadBottomPanel();

        menuFrame.add(panel1, BorderLayout.NORTH);
        menuFrame.add(panel2, BorderLayout.CENTER);
        menuFrame.add(panel3, BorderLayout.SOUTH);
        menuFrame.setResizable(false);
        menuFrame.setVisible(true);
    }

    public void setRaceConfiguration() {
        HorsesListFile horsesFile = new HorsesListFile();

        // Save all the horses to the horses CSV file
        ArrayList<HorseV2> horses = new ArrayList<HorseV2>(horsesListModel.getSize());
        for(int i = 0; i < horsesListModel.getSize(); i++) {
            horses.add(horsesListModel.getElementAt(i));
        }

        horsesFile.saveMultipleHorses(horses, false);

        // Add all of the horses in the fields to the participating horses list ("laneHorses")
        this.race = new RaceGUI(this.raceLengthInput);

        for(int i = 0; i < horsesListModel.getSize(); i++) {
            this.race.addHorse(horsesListModel.getElementAt(i));
        }

        RaceInfoFile raceInfoFile = new RaceInfoFile();
        raceInfoFile.saveRaceInfo(this.race.getRaceInfo());

        this.confirmStartRace();
    }

    public void confirmStartRace() {
        this.race.startRaceGUI();
    }
}
