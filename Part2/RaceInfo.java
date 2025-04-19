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
        if(newTrackType == null) {
            throw new IllegalArgumentException("ERROR: Track type must not be 'null'.");
        }
        this.trackType = newTrackType;
    }

    public int getRaceLength() {
        return this.raceLength;
    }

    public TrackType getTrackType() {
        return this.trackType;
    }
}