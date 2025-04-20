import java.util.ArrayList;
import java.io.*;
public class PastRacesFile {
    private ArrayList<PastRace> pastRaces = new ArrayList<PastRace>();
    private final String FILENAME = "PastRaces.csv";
    private final int NO_OF_COLUMNS = 7;

    public PastRacesFile() {
        loadPastRaces();
    }

    public void loadPastRaces() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            HorsesListFile horsesFile = new HorsesListFile();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                // TODO EXTRACT PAST RACE DATA HERE
                String[] data = line.split(",");

                if(data.length == NO_OF_COLUMNS) {
                    int raceLength = Integer.parseInt(data[0]);
                    String trackName = data[1];
                    double trackBaseFallProb = Double.parseDouble(data[2]);
                    double trackBaseFastMoveProb = Double.parseDouble(data[3]);
                    String horseId = data[4];

                    TrackType track = new TrackType(trackName, trackBaseFallProb, trackBaseFastMoveProb);

                    HorseV2 winningHorse;
                    if(horseId.equals(SpecialHorses.NO_WINNER.getId())) { // Checks if the winning horse's ID mathes the "NOWINNER" placeholder horse's ID
                        winningHorse = SpecialHorses.NO_WINNER;
                    } else {
                        // If the winning horse is not found in the saved horses file, load the race data, but leave the horse unknown
                        winningHorse = horsesFile.searchById(horseId);
                        if(winningHorse == null) {
                            winningHorse = SpecialHorses.UNKNOWN_HORSE; 
                        }
                    }

                    double elapsedTime = Double.parseDouble(data[5]);
                    String dateCompleted = data[6];

                    PastRace pastRace = new PastRace(raceLength, winningHorse, elapsedTime, dateCompleted);
                    pastRace.setTrackType(track);
                    pastRaces.add(pastRace);
                } else {
                    throw new IllegalArgumentException("ERROR: Invalid data format in line '" + line + "' of " + FILENAME + " file");
                }

                line = reader.readLine();
            }
        } catch(IllegalArgumentException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error occurred while loading from the file " + FILENAME + ". Possible that one of the columns in the file are holding the wrong values?");
            }
            e.printStackTrace();

        } catch(IOException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error occurred while loading from the file " + FILENAME + ". Possible that the file does not exist or is renamed?");
            }
            e.printStackTrace();
        }
    }

    public void savePastRace(PastRace pastRace) {
        // TODO SAVE PAST RACE DATA HERE
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME, true))) {
            int raceLength = pastRace.getRaceLength();
            String trackName = pastRace.getTrackType().toString();
            double trackBaseFallProb = pastRace.getTrackType().getBaseFallProb();
            double trackBaseFastMoveProb = pastRace.getTrackType().getBaseFastMoveProb();
            String winningHorseId = pastRace.getWinningHorse().getId();
            double elapsedTime = pastRace.getElapsedTime();
            String dateCompleted = pastRace.getDateCompleted();

            writer.println(raceLength + "," + trackName + "," + trackBaseFallProb + "," + trackBaseFastMoveProb + "," + winningHorseId + "," + elapsedTime + "," + dateCompleted);
            writer.close();
        } catch(IOException e) {
            System.out.println("ERROR: An error occurred while writing to the file " + FILENAME + ". Possible that the file does not exist for is renamed?");
            e.printStackTrace();
        }

    }

    public ArrayList<PastRace> getPastRaces() {
        return this.pastRaces;
    }
}
