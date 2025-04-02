
/**
 * Stores attributes and all related getter and setter methods about a horse
 * 
 * @author Emman Ruiz Cunanan Medina
 * @version v1.0
 */
public class Horse
{
    //Fields of class Horse
    String name;
    char symbol;
    int distanceTravelled = 0;
    double confidenceRating;
    boolean isFallen = false;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidenceRating = horseConfidence;
    }
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.isFallen = true;
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
        this.confidenceRating = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
