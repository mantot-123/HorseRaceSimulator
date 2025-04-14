public class Bet {
    private String betterName;
    private HorseV2 horse;
    private double betStake;
    public Bet(HorseV2 horse, double stake) {
        this.horse = horse;
        this.betStake = stake;
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
}
