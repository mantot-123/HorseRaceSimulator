public class TestRace {
    // Test for starting the race with 3 horses
    public static void testRace1() {
        Race race = new Race(15);
        Horse horse1 = new Horse('P', "Pippi Longstocking", 0.5);
        Horse horse2 = new Horse('S', "Sharpie Sniffer", 0.5);
        Horse horse3 = new Horse('E', "El Jefe", 0.5);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
    }

    // Test for missing lane horses
    public static void testRace2() {
        Race race = new Race(20);
        Horse horse1 = new Horse('B', "Bobby Blazin;", 0.5);
        Horse horse2 = new Horse('C', "Coco Chanel", 0.5);
        Horse horse3 = null; // No third horse added

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);

        race.startRace();
    }

    // Test for a negative track distance
    public static void testRace3() {
        Race race = new Race(-30); // Negative track distance
        Horse horse1 = new Horse('N', "Neighing Neighbour", 0.5);
        Horse horse2 = new Horse('M', "Magnetic Neigh", 0.5);
        Horse horse3 = new Horse('H', "Hackerhorse", 0.5);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
    }

    // Test for adding the same horse to multiple lanes
    public static void testRace4() {
        Race race = new Race(10); // Negative track distance
        Horse horse1 = new Horse('O', "Ominous Rider", 0.5);
        Horse horse2 = new Horse('W', "Whiteboard Whiff", 0.5);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse2, 3);

        race.startRace();
    }
}
