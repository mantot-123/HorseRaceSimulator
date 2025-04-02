public class TestHorse {
    public static void testHorse1(String[] args) {
        Horse horse1 = new Horse('H', "Pippi Longstocking", 0.6);
        System.out.println("Horse name: " + horse1.getName()); // Shows name of the horse
        System.out.println("Horse symbol: " + horse1.getSymbol()); // Shows symbol of the horse
        System.out.println("Horse confidence: " + horse1.getConfidence()); // Shows confidence of the horse
        System.out.println("Distance travelled: " + horse1.getDistanceTravelled()); // Shows distance of the horse
        System.out.println("Is the horse fallen: " + horse1.hasFallen()); // Shows the "fallen" flag of the horse
        // Shows distance of the horse
        System.out.println("Distance travelled before moving: " + horse1.getDistanceTravelled()); 

        // Updates the distance travelled by 2 steps
        horse1.moveForward();
        horse1.moveForward();
        System.out.println("Distance travelled after moving by 2 steps: " + horse1.getDistanceTravelled());
        
        // Updates the "fallen" flag of the horse
        horse1.fall();
        System.out.println("Is the horse fallen: " + horse1.hasFallen());

        // Resets horse distance 
        horse1.goBackToStart();
        System.out.println("DIstance travelled after going back to starting point: " + horse1.getDistanceTravelled());

        // Updates the confidence of the horse (success)
        horse1.setConfidence(0.9);
        System.out.println("Horse confidence after updating: " + horse1.getConfidence()); // Shows confidence of the horse
        // Updates the confidence of the horse (fail)
        horse1.setConfidence(1.1);
        System.out.println("Horse confidence after updating: " + horse1.getConfidence()); // Shows confidence of the horse

        // Set horse symbol
        horse1.setSymbol('X');
        System.out.println("Horse symbol after updating: " + horse1.getSymbol()); // Shows symbol of the horse
    }
}
