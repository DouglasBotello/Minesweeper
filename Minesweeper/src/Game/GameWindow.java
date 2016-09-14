package Game;

import java.awt.Color;

import CS2114.Button;
import CS2114.Shape;
import CS2114.SquareShape;
import CS2114.TextShape;
import CS2114.Window;
import CS2114.WindowSide;

public class GameWindow 
{
    private Window window;
    private int gridSizeX;
    private int gridSizeY;
    private Button chooseSmall;
    private Button chooseLarge;
    private Button playAgain;
    private SquareShape[][] grid;
    private TextShape[][] numGrid;
    private boolean started;
    GameGrid gg;
    
    
    public GameWindow()
    {
        window = new Window("Minesweeper");
        gameStart();
        this.chooseSmall = new Button("small");
        
        this.chooseLarge = new Button("large");
       
        chooseSmall.onClick(this, "clickedChooseSmall");
        chooseLarge.onClick(this, "clickedChooseLarge");
        window.addButton(chooseSmall, WindowSide.SOUTH);
        window.addButton(chooseLarge, WindowSide.SOUTH);
        started = false;
        
        
    }
    
    
    public void newGame(Button button)
    {
        window.removeAllShapes();
        window.removeButton(playAgain, WindowSide.SOUTH);
        gameStart();
        this.chooseSmall = new Button("small");
        
        this.chooseLarge = new Button("large");
       
        chooseSmall.onClick(this, "clickedChooseSmall");
        chooseLarge.onClick(this, "clickedChooseLarge");
        window.addButton(chooseSmall, WindowSide.SOUTH);
        window.addButton(chooseLarge, WindowSide.SOUTH);
        started = false;
    }
    
    
    public static void main(String[] args)
    {
        GameWindow gw = new GameWindow();
        
    }
    
    private void gameStart()
    {
        TextShape welcome = new TextShape(window.getGraphPanelWidth() / 3 - 70,
                window.getGraphPanelHeight() / 2, "Welcome To Minesweeper! Coded by Douglas Botello", Color.BLACK);
        TextShape chooseSize = new TextShape(window.getGraphPanelWidth() / 3 - 6,
                window.getGraphPanelHeight() / 2 + 15, "Choose a size: small or large", Color.BLACK);
        welcome.setBackgroundColor(Color.WHITE);
        chooseSize.setBackgroundColor(Color.WHITE);
        
        window.addShape(welcome);
        window.addShape(chooseSize);
    }
    
    private void startGame(String start)
    {
        if (start.equals("small"))
        {
            gridSizeX = 6;
            gridSizeY = 6;
        }
        else
        {
            gridSizeX = 12;
            gridSizeY = 8;
        }
        gg = new GameGrid(gridSizeX, gridSizeY);
        window.removeAllShapes();
        clearBoard();
        beginGame(gg);
    }
    
    private void clearBoard() 
    {
        window.removeButton(this.chooseSmall, WindowSide.SOUTH);
        window.removeButton(this.chooseLarge, WindowSide.SOUTH);
        
    }
    
    public void clickedChooseSmall(Button button)
    {
        startGame("small");
        
    }
    
    public void clickedChooseLarge(Button button)
    {
        startGame("large");
    }
    
    private void beginGame(GameGrid demention)
    {
        grid = new SquareShape[gridSizeX][gridSizeY];
        
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                grid[i][j] = new SquareShape(10 + (i * 34), 10 + (j * 34), 31);
            }
        }
        
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                window.addShape(grid[i][j]);
                grid[i][j].onClick(this, "firstClick");
            }
        }
        

        for (int j = 0; j < gridSizeX + 1; j++) //grid
        {
            window.addShape(new Shape(8 + (j * 34), 8, 1, (34 * gridSizeY), Color.black));
        }
        for (int i = 0; i < gridSizeY + 1; i++)
        {
            window.addShape(new Shape(8, 8 + (i * 34), (34 * gridSizeX), 1, Color.black));
        }
        
        TextShape instruction1 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                window.getGraphPanelHeight() / 4, "Click on a square!", Color.BLACK);
        instruction1.setBackgroundColor(Color.white);
        window.addShape(instruction1);
        TextShape instruction2 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                window.getGraphPanelHeight() / 4 + 30, "If a square turns red...", Color.BLACK);
        instruction2.setBackgroundColor(Color.white);
        window.addShape(instruction2);
        TextShape instruction3 = new TextShape(window.getGraphPanelWidth() / 2 + 150,
                window.getGraphPanelHeight() / 4 + 45, "you lose!", Color.BLACK);
        instruction3.setBackgroundColor(Color.white);
        window.addShape(instruction3);
        
    }
    
    
    public void firstClick(Shape shape)
    {
        if (!started)
        {
            int tileX = -2;
            int tileY = -2;
            for (int x = 0; x < gridSizeX; x++)
            {
                for (int y = 0; y < gridSizeY; y++) //locates the button that you pressed
                {
                    if (shape == grid[x][y])
                    {
                        tileX = x;
                        tileY = y;
                        break; //not working
                    }
                }
                
            }
            gg.setBoard(tileX, tileY);
            
            interpretGrid();
            
            for (int i = 0; i < gridSizeX; i++)
            {
                for (int j = 0; j < gridSizeY; j++)
                {
                    grid[i][j].onClick(this, "nextClick");
                }
            }
            started = true;
        }
        
        TextShape instruction1 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                window.getGraphPanelHeight() / 4, "Click on a square!", Color.BLACK);
        instruction1.setBackgroundColor(Color.white);
        window.addShape(instruction1);
        TextShape instruction2 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                window.getGraphPanelHeight() / 4 + 30, "If a square turns red...", Color.BLACK);
        instruction2.setBackgroundColor(Color.white);
        window.addShape(instruction2);
        TextShape instruction3 = new TextShape(window.getGraphPanelWidth() / 2 + 150,
                window.getGraphPanelHeight() / 4 + 45, "you lose!", Color.BLACK);
        instruction3.setBackgroundColor(Color.white);
        window.addShape(instruction3);
    }
    
    public void nextClick(Shape shape)
    {
        int tileX = -2;
        int tileY = -2;
        for (int x = 0; x < gridSizeX; x++)
        {
            for (int y = 0; y < gridSizeY; y++)
            {
                if (shape == grid[x][y])
                {
                    tileX = x;
                    tileY = y;
                    break; //not working
                }
            }
            
        }
        boolean hitMine = gg.onClick(tileX, tileY);
        window.removeAllShapes();
        
        if (hitMine)
        {
            interpretGrid();
            TextShape loseMessage = new TextShape(window.getGraphPanelWidth() / 2 + 180,
                    window.getGraphPanelHeight() / 2, "You lose!", Color.red);
            loseMessage.setBackgroundColor(Color.white);
            window.addShape(loseMessage);
            this.playAgain = new Button("Play Again?");
            playAgain.onClick(this, "newGame");
            window.addButton(playAgain, WindowSide.SOUTH);
        }
        else
        {
            if (gg.checkWin())
            {
                gg.revealBoard();
                interpretGrid();
                TextShape winMessage1 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                        window.getGraphPanelHeight() / 2, "Congratulations!", Color.BLACK);
                winMessage1.setBackgroundColor(Color.white);
                window.addShape(winMessage1);
                TextShape winMessage2 = new TextShape(window.getGraphPanelWidth() / 2 + 150,
                        window.getGraphPanelHeight() / 2 + 15, "You've won!", Color.BLACK);
                winMessage2.setBackgroundColor(Color.white);
                window.addShape(winMessage2);
                this.playAgain = new Button("Play Again?");
                playAgain.onClick(this, "newGame");
                window.addButton(playAgain, WindowSide.SOUTH);
                
            }
            else
            {
                interpretGrid();
                TextShape instruction1 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                        window.getGraphPanelHeight() / 4, "Click on a square!", Color.BLACK);
                instruction1.setBackgroundColor(Color.white);
                window.addShape(instruction1);
                TextShape instruction2 = new TextShape(window.getGraphPanelWidth() / 2 + 130,
                        window.getGraphPanelHeight() / 4 + 30, "If a square turns red...", Color.BLACK);
                instruction2.setBackgroundColor(Color.white);
                window.addShape(instruction2);
                TextShape instruction3 = new TextShape(window.getGraphPanelWidth() / 2 + 150,
                        window.getGraphPanelHeight() / 4 + 45, "you lose!", Color.BLACK);
                instruction3.setBackgroundColor(Color.white);
                window.addShape(instruction3);
            }
        }
    }
    
    
    private void interpretGrid()
    {
        window.removeAllShapes();
        numGrid = new TextShape[gridSizeX][gridSizeY];
        for (int i = 0; i < gridSizeX; i++)
        {
            for (int j = 0; j < gridSizeY; j++)
            {
                if (gg.getMineSpot(i, j).getRevealed())
                {
                    if (gg.getMineSpot(i, j).getMineValue() > 0)
                    {
                        grid[i][j] = new SquareShape(10 + (i * 34), 10 + (j * 34), 31);
                        grid[i][j].setBackgroundColor(Color.RED);
                        grid[i][j].setForegroundColor(Color.RED);
                        window.addShape(grid[i][j]);
                    }
                    else if (gg.getMineSpot(i, j).getSurroundValue() > 0)
                    {
                        numGrid[i][j] = new TextShape(10 + (i * 34) + 7, 10 + (j * 34) + 7, 
                                Integer.toString(gg.getMineSpot(i, j).getSurroundValue()), Color.BLACK);
                        numGrid[i][j].setBackgroundColor(Color.white);
                        window.addShape(numGrid[i][j]);
                    }
                }
                else
                {
                    window.addShape(grid[i][j]);
                }
            }
        }
        
        for (int j = 0; j < gridSizeX + 1; j++) //grid
        {
            window.addShape(new Shape(8 + (j * 34), 8, 1, (34 * gridSizeY), Color.black));
        }
        for (int i = 0; i < gridSizeY + 1; i++)
        {
            window.addShape(new Shape(8, 8 + (i * 34), (34 * gridSizeX), 1, Color.black));
        }
    }
    
}


