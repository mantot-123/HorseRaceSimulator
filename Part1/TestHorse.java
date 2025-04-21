public class TestHorse {
    public static void testHorse1() {
        Horse horse = new Horse('H', "Pippi Longstocking", 0.6);
        System.out.println("Horse name: " + horse.getName()); // Shows name of the horse
        System.out.println("Horse symbol: " + horse.getSymbol()); // Shows symbol of the horse
        System.out.println("Horse confidence: " + horse.getConfidence()); // Shows confidence of the horse
        System.out.println("Distance travelled: " + horse.getDistanceTravelled()); // Shows distance of the horse
        System.out.println("Is the horse fallen: " + horse.hasFallen()); // Shows the "fallen" flag of the horse
        // Shows distance of the horse
        System.out.println("Distance travelled before moving: " + horse.getDistanceTravelled()); 
    }

    public static void testHorse2() {
        Horse horse = new Horse('H', "Pippi Longstocking", 0.6);
        // Updates the distance travelled by 2 steps
        horse.moveForward();
        horse.moveForward();
        System.out.println("Distance travelled after moving by 2 steps: " + horse.getDistanceTravelled());   
    }

    public static void testHorse3() {
        Horse horse = new Horse('H', "Pippi Longstocking", 0.6);
        // Updates the "fallen" flag of the horse
        horse.fall();
        System.out.println("Is the horse fallen: " + horse.hasFallen());
        System.out.println("Confidence level after falling: " + horse.getConfidence());
    }

    public static void testHorse4() {
        Horse horse = new Horse('H', "Pippi Longstocking", 0.6);
        horse.moveForward();
        horse.moveForward();
        // Resets horse distance 
        horse.goBackToStart();
        System.out.println("DIstance travelled after going back to starting point: " + horse.getDistanceTravelled());
    }

    public static void testHorse5() {
        Horse horse = new Horse('H', "Pippi Longstocking", 0.6);
        // Updates the confidence of the horse (success)
        horse.setConfidence(0.9);
        System.out.println("Horse confidence after updating: " + horse.getConfidence()); // Shows confidence of the horse
        // Updates the confidence of the horse (fail)
        horse.setConfidence(1.1);
        System.out.println("Horse confidence after updating: " + horse.getConfidence()); // Shows confidence of the horse
    }


    public static void testHorse6() {
        Horse horse = new Horse('H', "Pippi Longstocking", 0.6);
        // Set horse symbol
        horse.setSymbol('X');
        System.out.println("Horse symbol after updating: " + horse.getSymbol()); // Shows symbol of the horse
    }
}
