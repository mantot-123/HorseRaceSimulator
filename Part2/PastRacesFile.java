import java.util.ArrayList;
import java.io.*;
public class PastRacesFile {
    private ArrayList<PastRace> pastRaces = new ArrayList<PastRace>();
    private final String FILENAME = "PastRaces.csv";

    public PastRacesFile() {
        loadPastRaces();
    }

    public void loadPastRaces() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                // TODO EXTRACT PAST RACE DATA HERE
                line = reader.readLine();
            }
        } catch(IOException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error occurred while loading from the file " + FILENAME + ". Possible that the file does not exist or is renamed?");
            }
        }
    }

    public void savePastRace() {
        // TODO SAVE PAST RACE DATA HERE
    }

    public ArrayList<PastRace> getPastRaces() {
        return this.pastRaces;
    }
}
