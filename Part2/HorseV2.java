/**
 * HorseV2.java
 * Stores attributes and all related getter and setter methods about a horse
 * 
 * @author Emman Ruiz Cunanan Medina
 * @version v1.0
 */
public class HorseV2
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distanceTravelled = 0;
    private double confidenceRating;
    private boolean isFallen = false;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public HorseV2(char horseSymbol, String horseName, double horseConfidence)
    {
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidenceRating = horseConfidence;
    }
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.isFallen = true;

        // decrease confidence rating by 0.1
        // only do this as long as the confidence rating is above 0.1
        if (this.confidenceRating > 0.1) {
            setConfidence(this.confidenceRating - 0.1);
        }
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
    
    public void goBackToStart()
    {
        this.distanceTravelled = 0;   
    }
    
    public boolean hasFallen()
    {
        return this.isFallen;
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
            System.out.println("ERROR: Confidence must be between 0 and 1.");
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
