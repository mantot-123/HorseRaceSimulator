public class TestRace {
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

    public static void testRace2() {
        Race race = new Race(20);
        Horse horse1 = new Horse('B', "Bobby Blazin;", 0.5);
        Horse horse2 = new Horse('C', "Coco Chanel", 0.5);
        Horse horse3 = null; // No third horse added

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);

        race.startRace();
    }

    public static void testRace3() {

    }
}
