public class Bet {
    private String betterName;
    private HorseV2 horse;
    private double betStake;
    private double betOdds;
    private double winnings;

    // 0 = Pending, 1 = Won, 2 = Lost
    private int status = 0;

    public Bet(String chosenName, HorseV2 chosenHorse, double chosenStake, double odds) {
        if(chosenName == null || chosenName.isEmpty()) {
            throw new IllegalArgumentException("Better name must not be empty!");
        }

        if(chosenHorse == null) {
            throw new IllegalArgumentException("Chosen horse must not be null");
        }

        if(chosenStake <= 0) {
            throw new IllegalArgumentException("Stake amount must not be zero or negative.");
        }

        if(odds < 0) {
            throw new IllegalArgumentException("Betting odds must not be negative.");
        }

        this.betterName = chosenName;
        this.horse = chosenHorse;
        this.betStake = chosenStake;
        this.betOdds = odds;
    }

    // Overloading = 
    public Bet(String chosenName, HorseV2 chosenHorse, double chosenStake, double odds, double winnings, int status) {
        if(chosenName == null || chosenName.isEmpty()) {
            throw new IllegalArgumentException("Better name must not be empty.");
        }

        if(chosenHorse == null) {
            throw new IllegalArgumentException("Chosen horse must not be null");
        }

        if(chosenStake <= 0) {
            throw new IllegalArgumentException("Stake amount must not be zero or negative.");
        }

        if(odds < 0) {
            throw new IllegalArgumentException("Betting odds must not be negative.");
        }

        if(status < 0 || status > 2) {
            throw new IllegalArgumentException("Bet status must be 0, 1 or 2 only");
        }

        this.betterName = chosenName;
        this.horse = chosenHorse;
        this.betStake = chosenStake;
        this.betOdds = odds;
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
    
    public double getBetOdds() {
        // Betting odds calculated by taking the reciprocal of the chosen horse's win wate
        // return 1 / this.horse.getWinRating();
        return this.betOdds;
    }

    public double getWinnings() {
        return this.winnings;
    }

    public int getStatus() {
        return this.status;
    }

    public void win() {
        this.status = 1;
        this.winnings = this.betOdds * this.betStake;
    }

    public void lose() {
        this.status = 2;
        this.winnings = (-1) * this.betStake; // Negative winnings means money is lost from the bet
    }

    @Override
    public String toString() {
        String statusAsString = "";
        if(this.status == 0)
            statusAsString = "Pending...";
        else if(this.status == 1)
            statusAsString = "Won";
        else if(this.status == 2)
            statusAsString = "Lost";

        return "Name: " + this.betterName + " | Horse: " + this.horse.getName() + " | Stake: $" + this.betStake + " | Odds:" + String.format("%.2f", this.betOdds) +  " | Winnings: $" + this.winnings + " | Status: " + statusAsString;
    }

}
