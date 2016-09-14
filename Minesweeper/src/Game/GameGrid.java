package Game;

import java.util.Random;

public class GameGrid 
{
    
    private int gridSizeX;
    private int gridSizeY;
    private int mineCount;
    private MineSpot grid[][];

    public GameGrid(int sizeX, int sizeY)
    {
        gridSizeX = sizeX;
        gridSizeY = sizeY;
        grid = new MineSpot[gridSizeX][gridSizeY];
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                grid[i][j] = new MineSpot();
            }
        }
        initializeGrid();
        mineCount = 0;
    }
    
    //sets all of the grid spots to zero
    private void initializeGrid()
    {
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                grid[i][j].setMineValue(0);
            }
        }
    }
    
    //gets a copy of the grid.
    public MineSpot[][] getGrid()
    {
        return grid;
    }
    
    //gets a particular mine spot
    public MineSpot getMineSpot(int xCoord, int yCoord)
    {
        return grid[xCoord][yCoord];
    }
    
    //plants the bombs
    public void setBoard(int locationX, int locationY)
    {
        Random rn = new Random();
        int random;
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                random = rn.nextInt(25);
                if (random >= 21)
                {
                    grid[i][j].setMineValue(1);
                    mineCount++;
                }
            }
        }
        grid[locationX][locationY].setMineValue(0);
        updateSurroundValues();
        grid[locationX][locationY].setRevealed();
        if (grid[locationX][locationY].getSurroundValue() == 0)
        {
            crawlGrid(locationX, locationY);
        }
    }
    
    public boolean onClick(int x, int y)
    {
        grid[x][y].setRevealed();
        boolean hitMine = false;
        if (grid[x][y].getMineValue() == 1)
        {
            for (int i = 0; i < gridSizeX; i++)
            {
                for (int j = 0; j < gridSizeY; j++)
                {
                    grid[i][j].setRevealed();
                    grid[x][y].setMineValue(2); //why is this 2 again?
                    hitMine = true;
                }
            }
        }
        if (grid[x][y].getSurroundValue() == 0)
        {
            crawlGrid(x, y);
        }
        return hitMine;
    }
    
    //makes a bubble. Pre: The given spot has no mines around it
    private void crawlGrid(int x, int y)
    {
        if (this.checkValid(x, y + 1, true)) //down
        {
            grid[x][y+1].setRevealed();
            crawlGrid(x, y+1);
        }
        if (this.checkValid(x + 1, y, false)) //right
        {
            grid[x+1][y].setRevealed();
            crawlGrid(x + 1, y);
        }
        if (this.checkValid(x, y - 1, true)) //up
        {
            grid[x][y-1].setRevealed();
            crawlGrid(x, y - 1);
        }
        if (this.checkValid(x - 1, y, false)) //left
        {
            grid[x-1][y].setRevealed();
            crawlGrid(x - 1, y);
        }
        
    }
    
    //invalid if surroundValue is not 0, is revealed, has a mine, or not in array
    //also reveals tiles with mines around them
    private boolean checkValid(int x, int y, boolean upOrDown)
    {
        //in array?
        if (x > gridSizeX -1 || x < 0 || y > gridSizeY -1 || y < 0)
        {
            return false;
            
        }
        //has a mine around it?
        if (grid[x][y].getSurroundValue() != 0)
        {
            grid[x][y].setRevealed();
            if (upOrDown)
            {
                if (x - 1 <= gridSizeX -1 && x - 1 >= 0 && y <= gridSizeY -1 && y >= 0)
                {
                    if (grid[x-1][y].getSurroundValue() >= 1)
                    {
                        grid[x-1][y].setRevealed();
                    }
                    
                }
                if (x + 1 <= gridSizeX -1 && x + 1 >= 0 && y <= gridSizeY -1 && y >= 0)
                {
                    if (grid[x+1][y].getSurroundValue() >= 1)
                    {
                        grid[x+1][y].setRevealed();
                    }
                    
                }
            }
            else
            {
                if (x <= gridSizeX -1 && x >= 0 && y - 1 <= gridSizeY -1 && y - 1 >= 0)
                {
                    if (grid[x][y-1].getSurroundValue() >= 1)
                    {
                        grid[x][y-1].setRevealed();
                    }
                }
                if (x <= gridSizeX -1 && x >= 0 && y + 1 <= gridSizeY -1 && y + 1 >= 0)
                {
                    if (grid[x][y+1].getSurroundValue() >= 1)
                    {
                        grid[x][y+1].setRevealed();
                    }
                }
            }
            return false;
        }
        //is the spot already revealed or the spot has a mine?
        if (grid[x][y].getRevealed()) //|| grid[x][y].getMineValue() != 0)
        {
            return false;
        }
        if (grid[x][y].getMineValue() != 0)
        {
            return false;
        }
        return true;
    }
    
    public boolean checkWin()
    {
        int numRevealed = 0;
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                if (grid[i][j].getRevealed())
                {
                    numRevealed++;
                }
            }
        }
        
        if (numRevealed == (gridSizeX * gridSizeY) - mineCount)
        {
            return true;
        }
        return false;
    }
    
    public void revealBoard()
    {
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                grid[i][j].setRevealed();
            }
        }
    }
    
    //sets the surround values of the grid based on the mines around it
    private void updateSurroundValues()
    {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int mines = 0;
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                a = 0; //left
                b = 0; //above
                c = 0; //right
                d = 0; //bottom
                mines = 0;
                try 
                {
                     if (grid[i-1][j].getMineValue() == 1)
                     {
                         mines++;
                     }
                     a++;
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                     //do nothing
                }
                try 
                {
                   if (grid[i][j+1].getMineValue() == 1)
                   {
                       mines++;
                   }
                   b++;
               }
               catch (ArrayIndexOutOfBoundsException e)
               {
                   //do nothing
               }
                try 
                {
                   if (grid[i+1][j].getMineValue() == 1)
                   {
                       mines++;
                   }
                   c++;
               }
               catch (ArrayIndexOutOfBoundsException e)
               {
                   //do nothing
               }
                try 
                {
                   if (grid[i][j-1].getMineValue() == 1)
                   {
                       mines++;
                   }
                   d++;
               }
               catch (ArrayIndexOutOfBoundsException e)
               {
                   //do nothing
               }
                
                if (a == 1 && b == 1) //up left
                {
                    if (grid[i-1][j+1].getMineValue() == 1)
                    {
                        mines++;
                    }
                }
                if (b == 1 && c == 1) //up right
                {
                    if (grid[i+1][j+1].getMineValue() == 1)
                    {
                        mines++;
                    }
                }
                if (c == 1 && d == 1) //down right
                {
                    if (grid[i+1][j-1].getMineValue() == 1)
                    {
                        mines++;
                    }
                }
                if (d == 1 && a == 1) //down left
                {
                    if (grid[i-1][j-1].getMineValue() == 1)
                    {
                        mines++;
                    }
                }
                
                grid[i][j].setSurroundValue(mines);
                
            }
        }
    }
    
}
