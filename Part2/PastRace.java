
public class PastRace {
    private int raceLength;
    private HorseV2 winningHorse;
    private long timeCompleted; // measured in seconds
    private String date;

    private PastRace(int raceLength, HorseV2 winningHorse, long timeCompleted) {
        this.raceLength = raceLength;
        this.winningHorse = winningHorse;
        this.timeCompleted = timeCompleted;
    }
}
