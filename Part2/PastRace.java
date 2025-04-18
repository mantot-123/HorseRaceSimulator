import java.time.LocalDate;

public class PastRace extends RaceInfo {
    private HorseV2 winningHorse;
    private long elapsedTime; // measured in seconds
    private String dateCompleted;

    public PastRace(int raceLength, HorseV2 winningHorse, long elapsedTime) {
        super(raceLength);
        this.winningHorse = winningHorse;
        this.elapsedTime = elapsedTime;
        this.dateCompleted = LocalDate.now().toString(); // Get the date when the object was created
    }

    public PastRace(int raceLength, HorseV2 winningHorse, long elapsedTime, String dateCompleted) {
        super(raceLength);
        this.winningHorse = winningHorse;
        this.elapsedTime = elapsedTime;
        this.dateCompleted = dateCompleted;
    }

    public HorseV2 getWinningHorse() {
        return this.winningHorse;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }

    public String getDateCompleted() {
        return this.dateCompleted;
    }
}
