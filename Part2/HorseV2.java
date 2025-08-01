/**
 * HorseV2.java
 * Stores attributes and all related getter and setter methods about a horse
 * 
 * @author Emman Ruiz Cunanan Medina
 * @version v2.0
 */
public class HorseV2
{
    //Fields of class Horse
    private String id;

    // By default, the horse doesn't have any equipment, meaning there are no modifiers provided for this horse
    private Equipment equipment = new Equipment(null, "None", 0, 0);
    private String name;
    private char symbol;
    private int distanceTravelled = 0;
    private double confidenceRating;
    private boolean isFallen = false;
    
    private int gamesPlayed;
    private int gamesWon;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */

    // A unique ID is automatically generated when these 2 constructors are used
    // Normally used for adding new horses to a race
    public HorseV2(char horseSymbol, String horseName, double horseConfidence)
    {
        this.id = Helpers.randomString(10);
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidenceRating = horseConfidence;
    }

    public HorseV2(char horseSymbol, String horseName, double horseConfidence, Equipment equipment)
    {
        this.id = Helpers.randomString(10);
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidenceRating = horseConfidence;
        this.equipment = equipment;
    }

    // These 2 constructors should be normally used for loading already existing horses
    
    // Overloading = You can add an optional ID to your horse
    public HorseV2(String id, char horseSymbol, String horseName, double horseConfidence, int gamesPlayed, int gamesWon)
    {
        this.id = id;
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidenceRating = horseConfidence;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }

    // Overloading = Equipment and number of games played and won can be added
    public HorseV2(String id, char horseSymbol, String horseName, double horseConfidence, Equipment equipment, int gamesPlayed, int gamesWon)
    {
        this.id = id;
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidenceRating = horseConfidence;
        this.equipment = equipment;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        this.isFallen = true;

        // decrease confidence rating by 0.11
        // only do this as long as the confidence rating is above 0.11
        if (this.confidenceRating > 0.11) {
            setConfidence(this.confidenceRating - 0.1);
        }
    }

    public void win() {
        // Increase confidence rating by 0.1
        if (this.confidenceRating < 1.0) {
            this.setConfidence(this.confidenceRating + 0.1);
        }
        this.gamesWon++;
    }

    public void addGamePlayed() {
        this.gamesPlayed++;
    }

    public void resetFallen()
    {
        this.isFallen = false;
    }

    public String getId() {
        return this.id;
    }

    public double getConfidence()
    {
        return this.confidenceRating;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public Equipment getEquipment() {
        return this.equipment;
    }

    public void goBackToStart()
    {
        this.distanceTravelled = 0;   
    }
    
    public boolean hasFallen()
    {
        return this.isFallen;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getGamesWon() {
        return this.gamesWon;
    }

    public double getWinRating() {
        if(this.gamesWon == 0) {
            return 0.0;
        }
        return (double)this.gamesWon / (double)this.gamesPlayed;
    }

    public void moveForward()
    {  
        this.distanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        if(newConfidence > 0.0 && newConfidence < 1.0) {
            this.confidenceRating = newConfidence;
        } else {
            System.out.println("Confidence must be between 0 and 1. Horse " + this.getName() + "'s confidence rating stays at " + this.getConfidence());
        }
    }

    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
    @Override
    public String toString() {
        return this.name + ", " + this.symbol;
    }
}
