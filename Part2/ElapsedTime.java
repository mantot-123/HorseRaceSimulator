
public class ElapsedTime {
    private double startTime;
    private double finishTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void finish() {
        finishTime = System.currentTimeMillis();
    }

    public double getElapsedTime() {
        return (finishTime - startTime) / 1000; // Returned in seconds format
    }
}
