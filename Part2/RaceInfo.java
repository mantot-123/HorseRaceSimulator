public class RaceInfo {
    private int raceLength = 0;
    private TrackType trackType;

    // Constructor
    public RaceInfo(int chosenRaceLength) {
        this.raceLength = chosenRaceLength;
    }

    public void setRaceLength(int newRaceLength) {
        this.raceLength = newRaceLength;
    }

    public void setTrackType(TrackType newTrackType) {
        this.trackType = newTrackType;
    }

    public int getRaceLength() {
        return this.raceLength;
    }

    public TrackType getTrackType() {
        return this.trackType;
    }
}