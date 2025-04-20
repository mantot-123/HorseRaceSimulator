import java.time.LocalDate;

public class PastRace extends RaceInfo {
    private HorseV2 winningHorse;
    private double elapsedTime; // measured in seconds
    private String dateCompleted;

    public PastRace(int raceLength, HorseV2 winningHorse, double elapsedTime) {
        super(raceLength);

        // If no winning horse is specified (it's null), then add a dummy horse showing "No winner"
        if(winningHorse != null) {
            this.winningHorse = winningHorse;
        } else {
            this.winningHorse = SpecialHorses.NO_WINNER;
        }

        this.elapsedTime = elapsedTime;
        this.dateCompleted = LocalDate.now().toString(); // Get the date when the object was created
    }

    public PastRace(int raceLength, HorseV2 winningHorse, double elapsedTime, String dateCompleted) {
        super(raceLength);

        if(winningHorse != null) {
            this.winningHorse = winningHorse;
        } else {
            this.winningHorse = SpecialHorses.NO_WINNER;
        }

        this.elapsedTime = elapsedTime;
        this.dateCompleted = dateCompleted;
    }

    public HorseV2 getWinningHorse() {
        return this.winningHorse;
    }

    public double getElapsedTime() {
        return this.elapsedTime;
    }

    public String getDateCompleted() {
        return this.dateCompleted;
    }

    @Override
    public String toString() {
        return "Track: " + this.getTrackType() + ", Winning horse: " + this.winningHorse.getName() + ", Elapsed time: " + this.elapsedTime + "s, Date completed: " + this.dateCompleted;
    }
}
