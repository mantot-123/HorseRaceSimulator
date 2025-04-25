import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BetHistoryFile {
    private ArrayList<Bet> bets = new ArrayList<Bet>();

    private final String FILENAME = "BetHistory.csv";
    private final int NO_OF_COLUMNS = 6;

    public BetHistoryFile() {
        loadBets();
    }

    public void loadBets() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            bets.clear(); // Clear all the existing bets in the ArrayList before loading new ones
            HorsesListFile horsesFile = new HorsesListFile();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                String[] data = line.split(",");

                if(data.length == NO_OF_COLUMNS) {
                    String betterName = data[0];
                    String horseId = data[1];
                    double betStake = Double.parseDouble(data[2]);
                    double betOdds = Double.parseDouble(data[3]);
                    double betWinnings = Double.parseDouble(data[4]);
                    int betStatus = Integer.parseInt(data[5]);

                    HorseV2 horse = horsesFile.searchById(horseId);

                    // If the horse can't be found, set it to an unknown horse
                    if(horse == null) {
                        System.out.println("WARNING: Unable to find horse with ID '" + horseId + "' in the file '" + FILENAME + "'. Setting it to an UNKNOWN_HORSE.");
                        horse = SpecialHorses.UNKNOWN_HORSE; 
                    }

                    Bet bet = new Bet(betterName, horse, betStake, betOdds, betWinnings, betStatus);
                    bets.add(bet);
                } else {
                    throw new IOException("ERROR: Invalid data format in line '" + line + "' of " + FILENAME + " file");
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
                System.out.println("ERROR: An error occurred while loading from the file " + FILENAME + ". Possible that the file does not exist, is renamed or the contents are corrupted?");
            }
            e.printStackTrace();
        }
    }

    public void saveBet(Bet bet, boolean append) {
        // TODO SAVE PAST RACE DATA HERE
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME, append))) {
            String name = bet.getBetterName();
            String horseId = bet.getHorse().getId();
            double stake = bet.getStake();
            double odds = bet.getBetOdds();
            double winnings = bet.getWinnings();
            int status = bet.getStatus();

            writer.println(name + "," + horseId + "," + stake + "," + odds + "," + winnings + "," + status);
            writer.close();
        } catch(NullPointerException e) {
            System.out.println("ERROR: The bet provided is null. Please give a valid bet.");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("ERROR: An error occurred while writing to the file " + FILENAME + ". Possible that the file does not exist for is renamed?");
            e.printStackTrace();
        }
    }

    public void saveMultipleBets(ArrayList<Bet> bets, boolean append) {
        if(!append) {
            clearBetsFile(); // Overwrite the contents of the save file if "append" is true
        }

        for(Bet bet: bets) {
            saveBet(bet, true);
        }
    }

    public void clearBetsFile() {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILENAME))) {
            writer.print("");
            writer.close();
        } catch(IOException e) {
            System.out.println("ERROR: An error occurred while writing to the file " + FILENAME + ". Possible that the file does not exist for is renamed?");
            e.printStackTrace(); 
        }
    }

    public ArrayList<Bet> getBetsList() {
        return this.bets;
    }

}
