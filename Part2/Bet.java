public class Bet {
    private String betterName;
    private HorseV2 horse;
    private double betStake;
    private boolean isWon = false;

    public Bet(String chosenName, HorseV2 chosenHorse, double chosenStake) {
        if(chosenName == null) {
            throw new IllegalArgumentException("ERROR: Better name must not be empty.");
        }

        if(chosenHorse == null) {
            throw new IllegalArgumentException("ERROR: Chosen horse must not be null");
        }

        if(chosenStake <= 0) {
            throw new IllegalArgumentException("ERROR: Stake amount must not be negative.");
        }

        this.betterName = chosenName;
        this.horse = chosenHorse;
        this.betStake = chosenStake;
    }

    public String getBetterName() {
        return this.betterName;
    }

    public HorseV2 getHorse() {
        return this.horse;
    }

    public double getStake() {
        return this.betStake;
    }

    public boolean isWon() {
        return this.isWon;
    }

    public void win() {
        this.isWon = true;
    }
}
