import java.io.*;
import java.util.*;

public class HorsesListFile {
    ArrayList<HorseV2> loadedHorses = new ArrayList<HorseV2>();

    public HorsesListFile() {
        loadHorses();
    }

    public void saveHorse(HorseV2 horse) {
        final int ID_SIZE = 10;
        final String FILENAME = "Horses.csv";
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME, true))) {
            String id = Helpers.randomString(ID_SIZE);
            String name = horse.getName();
            char symbol = horse.getSymbol();
            double confidence = horse.getConfidence();
            writer.println(id + "," + name + "," + symbol + "," + confidence);
            writer.close();
        } catch(IOException e) {
            System.out.println("ERROR: An error has occurred while writing to the file " + FILENAME);
            e.printStackTrace();
        }
    }

    public void saveMultipleHorses(ArrayList<HorseV2> horses) {
        for(HorseV2 horse: horses) {
            saveHorse(horse);
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
        final int NO_OF_COLUMNS = 4;
        final String FILENAME = "Horses.csv";

        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            loadedHorses.clear();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                String[] horseData = line.split(",");
                if(horseData.length == NO_OF_COLUMNS) {
                    String id = horseData[0];
                    String name = horseData[1];
                    char symbol = horseData[2].charAt(0);
                    double confidence = Double.parseDouble(horseData[3]);

                    if(horseData[2].length() > 1) {
                        throw new IllegalArgumentException("Symbol must be a single character. Found a non single character: " + horseData[1]);
                    }

                    HorseV2 newHorse = new HorseV2(id, symbol, name, confidence);
                    this.loadedHorses.add(newHorse);
                } else {
                    throw new IOException("Invalid data format in line " + line + " of the CSV file");
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

    public ArrayList<HorseV2> getHorsesList() {
        return this.loadedHorses;
    }
}
