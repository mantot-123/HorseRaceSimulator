import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
public class TrackTypesFile {
    private HashMap<String, TrackType> tracksHashMap = new HashMap<String, TrackType>();
    private final String FILENAME = "TrackTypesList.csv";

    public TrackTypesFile() {
        loadTrackTypes();
    }

    // Updates the equipment with the new contents in the horses list file
    public void loadTrackTypes() {
        final int NO_OF_COLUMNS = 4;

        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            tracksHashMap.clear();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                String[] data = line.split(",");
                if(data.length == NO_OF_COLUMNS) {
                    String id = data[0];
                    String name = data[1];
                    double baseFallProb = Double.parseDouble(data[2]);
                    double baseFastMoveProb = Double.parseDouble(data[3]);

                    TrackType tr = new TrackType(id, name, baseFallProb, baseFastMoveProb);
                    this.tracksHashMap.put(id, tr);
                } else {
                    throw new IllegalArgumentException("Invalid data format in line '" + line + "' of the CSV file");
                }
                line = reader.readLine();
            }
            reader.close();

        } catch(IllegalArgumentException  e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error has occurred while reading the file " + FILENAME + ". The contents of the file might have been corrupted.");
            }
            e.printStackTrace();

        } catch(IOException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error has occurred while reading the file " + FILENAME + ". Possible that the file does not exist or is empty.");
            }
            e.printStackTrace();
        }
    }

    public TrackType getTrackTypeById(String id) {
        return tracksHashMap.get(id);
    }

    public HashMap<String, TrackType> getTrackTypes() {
        return this.tracksHashMap;
    }
}
