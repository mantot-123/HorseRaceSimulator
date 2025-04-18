import java.io.*;
public class RaceInfoFile {
    private RaceInfo race = new RaceInfo(0);

    public RaceInfoFile() {
        loadRaceInfo();
    }

    public RaceInfo getRace() {
        return this.race;
    }

    public void loadRaceInfo() {
        final String FILENAME = "Race.txt";
        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine();
            if(line != null && !line.isEmpty()) {
                int readRaceLength = Integer.parseInt(line);
                this.race.setRaceLength(readRaceLength);
            }
        } catch(IllegalArgumentException e) {
            System.out.println("ERROR: An error occurred while reading from the file " + FILENAME + ". The contents of the file might have been corrupted.");
            e.printStackTrace();

        } catch (IOException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR:  " + e.getMessage());
            } else {
                System.out.println("ERROR: An error has occurred while reading from the file " + FILENAME + ". Possible that the file does not exist or is renamed?");
            }
            e.printStackTrace();
        }
    }

    public void saveRaceInfo(RaceInfo raceToSave) {
        final String FILENAME = "Race.txt";
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME))) {
            writer.println(raceToSave.getRaceLength());
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR: An error has occurred while writing to the file " + FILENAME + ". Possible that the file does not exist or is renamed?");
            e.printStackTrace();
        }
    }


    public void clearRaceInfo() {
        final String FILENAME = "Race.txt";
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME))) {
            writer.println("");
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR An error has occured while writing to the file " + FILENAME + ". Possible that the file does not exist or is renamed?");
            e.printStackTrace();
        }
    }
}
