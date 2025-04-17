public class Equipment {
    private String id;
    private String name;
    private int movementAmp;
    private int stabilityAmp;

    public Equipment(String id, String name, int movementAmp, int stabilityAmp) {
        this.id = id;
        this.name = name;
        this.movementAmp = movementAmp;
        this.stabilityAmp = stabilityAmp;
    }

    public String getId() {
        return this.id;
    }

    public int getMovementAmp() {
        return this.movementAmp;
    }

    public int getStabilityAmp() {
        return this.stabilityAmp;
    }

    public double calcFastMoveProbIncrease() {
        // When a horse moves the probability that it moves twice as many blocks by default is 0.1
        // Movement amplification increases that chance by 1/8 of the default probability
        double fastMoveProbIncrease = (this.movementAmp * (0.125 * 0.1));
        return fastMoveProbIncrease;
    }

    public double calcFallProbDecrease() {
        double fallProbDecrease = (this.stabilityAmp) / 1000;
        return fallProbDecrease; 
    }

    @Override
    public String toString() {
        return this.name;
    }

}