import java.io.*;
import java.util.*;

public class HorsesListFile {
    private ArrayList<HorseV2> loadedHorses = new ArrayList<HorseV2>();
    private final String FILENAME = "Horses.csv";
    private final int NO_OF_COLUMNS = 7;

    public HorsesListFile() {
        loadHorses();
    }

    public void saveHorse(HorseV2 horse, boolean append) {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME, append))) {
            String id = horse.getId();
            String name = horse.getName();
            char symbol = horse.getSymbol();
            double confidence = horse.getConfidence();
            Equipment equipment = horse.getEquipment();
            int gamesPlayed = horse.getGamesPlayed();
            int gamesWon = horse.getGamesWon();

            writer.println(id + "," + name + "," + symbol + "," + confidence + "," + equipment.getId() + "," + gamesPlayed + "," + gamesWon);
            writer.close();
        } catch(IOException e) {
            System.out.println("ERROR: An error has occurred while writing to the file " + FILENAME);
            e.printStackTrace();
        }
    }

    public void saveMultipleHorses(ArrayList<HorseV2> horses, boolean append) {
        if(!append) {
            clearSavedHorses(); // Overwrites the contents of the save file if "append" is true
        }
        
        for(HorseV2 horse: horses) {
            saveHorse(horse, true);
        }
    }

    // Erases contents of the horse list file
    public void clearSavedHorses() {
        final String FILENAME = "Horses.csv";
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME))) {
            writer.print(""); 
            writer.close();
        } catch(IOException e) {
            System.out.println("ERROR: An error has occurred while clearing the file " + FILENAME);
            e.printStackTrace();
        }
    }

    // Updates the horses list with the new contents in the horses list file
    public void loadHorses() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            loadedHorses.clear();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                String[] horseData = line.split(",");
                if(horseData.length == NO_OF_COLUMNS) {
                    String id = horseData[0];
                    String name = horseData[1];

                    if(horseData[2].length() > 1) {
                        throw new IllegalArgumentException("Symbol must be a single character. Found a non single character: " + horseData[2]);
                    }

                    char symbol = horseData[2].charAt(0);
                    double confidence = Double.parseDouble(horseData[3]);
                    String eqId = horseData[4];

                    EquipmentFile eqFile = new EquipmentFile();
                    Equipment equipment = eqFile.getEquipmentList().get(eqId);

                    int gamesPlayed = Integer.parseInt(horseData[5]);
                    int gamesWon = Integer.parseInt(horseData[6]);

                    HorseV2 newHorse = new HorseV2(id, symbol, name, confidence, equipment, gamesPlayed, gamesWon);
                    this.loadedHorses.add(newHorse);
                } else {
                    throw new IllegalArgumentException("Invalid data format in line " + line + " of the CSV file");
                }
                line = reader.readLine();
            }
            reader.close();

        } catch(IllegalArgumentException  e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: An error has occurred while reading the file Horses.csv. The contents of the file might have been corrupted. Possible that the confidence might not be a valid decimal number?");
            } else {
                System.out.println("ERROR: " + e.getMessage());
            }
            e.printStackTrace();

        } catch(IOException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error has occurred while reading the file Horses.csv. Possible that the file does not exist or is empty.");
            }
            e.printStackTrace();
        }
    }

    public HorseV2 searchById(String id) {
        for(HorseV2 h: this.loadedHorses) {
            if(h.getId().equals(id)) {
                return h;
            }
        }
        return null;
    }

    public ArrayList<HorseV2> getHorsesList() {
        return this.loadedHorses;
    }

    public HorseV2[] getHorsesArray() {
        HorseV2[] horsesArr = new HorseV2[loadedHorses.size()];
        for(int i = 0; i < this.loadedHorses.size(); i++) {
            horsesArr[i] = this.loadedHorses.get(i);
        }
        return horsesArr;
    }
}
