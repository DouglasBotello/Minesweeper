package Game;

public class MineSpot 
{

    private int mineValue;
    private int surroundValue;
    private boolean revealed;
    
    public MineSpot()
    {
        mineValue = 0; //any value above 0 is a bomb
        //value will be 2 if the bomb was the clicked bomb
        surroundValue = 0;
        revealed = false;
    }
    
    public int getMineValue()
    {
        return mineValue;
    }
    
    public int getSurroundValue()
    {
        return surroundValue;
    }
    
    public void setSurroundValue(int newValue)
    {
        surroundValue = newValue;
    }
    
    public void setMineValue(int newValue)
    {
        mineValue = newValue;
    }
    
    public void setRevealed()
    {
        revealed = true;
    }
    
    public boolean getRevealed()
    {
        return revealed;
    }
    
}
