public class TrackType {
    // Base probability values.
    // Fall and fast movement probabilities can change depending on the horse's properties like their equipment
    private String name;
    private double baseFallProb;
    private double baseFastMoveProb;

    public TrackType(String name, double baseFallProb, double baseFastMoveProb) {
        if(baseFallProb < 0 || baseFallProb > 1) {
            throw new IllegalArgumentException("ERROR: Invalid fall probability entered. The probability has to be between 0 and 1.");
        }

        if(baseFastMoveProb < 0 || baseFastMoveProb > 1) {
            throw new IllegalArgumentException("ERROR: Invalid fast movement probability entered. The probability has to be between 0 and 1.");
        }

        this.name = name;
        this.baseFallProb = baseFallProb;
        this.baseFastMoveProb = baseFastMoveProb;
    }

    public double getBaseFallProb() {
        return this.baseFallProb;
    }

    public double getBaseFastMoveProb() {
        return this.baseFastMoveProb;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
