package tetris.gui.figure;

import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
 * <p>Headline: GUI.Figure</p>
 * <p>Description: This class creates a Tetris figure with four blocks.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class Figure extends JComponent implements Serializable {

    //tetris blocks
    private Block blockOne;
    private Block blockTwo;
    private Block blockThree;
    private Block blockFour;

    public Block getBlockOne() {
        return blockOne;
    }

    public Block getBlockTwo() {
        return blockTwo;
    }

    public Block getBlockThree() {
        return blockThree;
    }

    public Block getBlockFour() {
        return blockFour;
    }

    //rotate position of the tetris figure
    private int figurePosition = 0;

    public int getFigurePosition() {
        return figurePosition;
    }

    //color of the tetris figure
    protected Color randomFigure = null;

    //inherit the tetris figure type 1,2,3,4,5,6 or 7
    protected int figureType = 0;

    //x start position of the tetris figure on the tetris battle field
    protected int startX = 20;

    //y start position of the tetris figure on the tetris battle field
    private int startY = 20;

    public int getStartY() {
        return startY;
    }

    //tetris block width
    protected int blockWidth = 20;

    //tetris block heigth
    protected int blockHeight = 20;

    //size of each block
    protected int blockSize = 20;

    //add x value for shift left, right and down
    protected int addX = 20;

    //add x value for shift left, right and down
    protected int addY = 20;

    //static tetris block width -> addForInfoPanel
    protected static int sBlockWidth = 20;

    //static tetris block height -> addForInfoPanel
    protected static int sBlockHeight = 20;

    /**
     * standard constructor
     */
    public Figure() {

    }

    /**
     * Initialization of the tetris figure with size and start position on the tetris battle field.
     *
     * @param startX int x start position of tetris figure on the tetris battle field
     * @param startY int y start position of tetris figure on the tetris battle field
     * @param width  int Width of each tetris block
     * @param height int Height of each tetris block
     */
    public Figure(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.blockWidth = width;
        this.blockHeight = height;
        this.addX = width;
        this.addY = height;
        this.blockSize = ((width + height) / 2);
        this.makeFigure();
    }

    /**
     * Used for repaint hierarchy.
     *
     * @param g Graphics not used
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Creates two random int values. The first random called figure type creates an integer value range
     * starting at 1 till 7. This is used for creating one of the seven tetris figures. The second random
     * integer value called figure position creates a value from 0 till 3. And it is used to create the
     * figure rotate position.
     *
     * @todo Angabe der figurePosition soll möglich sein. Fehler taucht jetzt auf da Zahlen von 0 bis 3
     * generiert werden, aber in manchen make figures nur zahlen von 0 bis 1 oder nur 0 erlaubt sind.
     */
    public void makeFigure() {
        //creates an integer value 1 - 7
        this.figureType = (int) (Math.random() * 8);
        //creates an integer value 0 - 3
        //this.figurePosition = (int) (Math.random() * 3);

        if (this.figureType == 0) {
            this.makeFigure();
        }
        else {
            switch (this.figureType) {
                case 1:
                    this.makeFigureOne();
                    break;
                case 2:
                    this.makeFigureTwo();
                    break;
                case 3:
                    this.makeFigureThree();
                    break;
                case 4:
                    this.makeFigureFour();
                    break;
                case 5:
                    this.makeFigureFive();
                    break;
                case 6:
                    this.makeFigureSix();
                    break;
                case 7:
                    this.makeFigureSeven();
                    break;
            }
        }
    }

    /**
     * Creates figure one
     */
    public void makeFigureOne() {
        figurePosition %= 2;
        if (figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.RED);
            this.blockTwo = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.RED);
            this.blockThree = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.RED);
            this.blockFour = new Block(blockWidth, blockHeight, startX - (2 * addX), startY, Color.RED);
        }
        else if (figurePosition == 1) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.RED);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.RED);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.RED);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY - (2 * addY), Color.RED);
        }
        this.randomFigure = Color.RED;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Creates figure two
     */
    public void makeFigureTwo() {
        if (figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.GREEN);
            this.blockTwo = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.GREEN);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.GREEN);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY - (2 * addY), Color.GREEN);
        }
        else if (figurePosition == 1) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.GREEN);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.GREEN);
            this.blockThree = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.GREEN);
            this.blockFour = new Block(blockWidth, blockHeight, startX + (2 * addX), startY, Color.GREEN);
        }
        else if (this.figurePosition == 2) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.GREEN);
            this.blockTwo = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.GREEN);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.GREEN);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY + (2 * addY), Color.GREEN);
        }
        else if (this.figurePosition == 3) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.GREEN);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.GREEN);
            this.blockThree = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.GREEN);
            this.blockFour = new Block(blockWidth, blockHeight, startX - (2 * addX), startY, Color.GREEN);
        }
        this.randomFigure = Color.GREEN;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Creates figure three
     */
    public void makeFigureThree() {
        if (this.figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.ORANGE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.ORANGE);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.ORANGE);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY - (2 * addY), Color.ORANGE);
        }
        else if (this.figurePosition == 1) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.ORANGE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.ORANGE);
            this.blockThree = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.ORANGE);
            this.blockFour = new Block(blockWidth, blockHeight, startX + (2 * addX), startY, Color.ORANGE);
        }
        else if (this.figurePosition == 2) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.ORANGE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.ORANGE);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.ORANGE);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY + (2 * addY), Color.ORANGE);
        }
        else if (this.figurePosition == 3) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.ORANGE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.ORANGE);
            this.blockThree = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.ORANGE);
            this.blockFour = new Block(blockWidth, blockHeight, startX - (2 * addX), startY, Color.ORANGE);
        }
        this.randomFigure = Color.ORANGE;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Creates figure four
     */
    public void makeFigureFour() {
        if (this.figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.BLUE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.BLUE);
            this.blockThree = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.BLUE);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.BLUE);
        }
        else if (this.figurePosition == 1) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.BLUE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.BLUE);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.BLUE);
            this.blockFour = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.BLUE);
        }
        else if (this.figurePosition == 2) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.BLUE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.BLUE);
            this.blockThree = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.BLUE);
            this.blockFour = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.BLUE);
        }
        else if (this.figurePosition == 3) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.BLUE);
            this.blockTwo = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.BLUE);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.BLUE);
            this.blockFour = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.BLUE);
        }
        this.randomFigure = Color.BLUE;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Creates figure five
     */
    public void makeFigureFive() {
        figurePosition %= 4;
        if (this.figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.YELLOW);
            this.blockTwo = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.YELLOW);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.YELLOW);
            this.blockFour = new Block(blockWidth, blockHeight, startX + (1 * addX), startY - (1 * addY), Color.YELLOW);
        }
        this.randomFigure = Color.YELLOW;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Creates figure six
     */
    public void makeFigureSix() {
        figurePosition %= 2;
        if (this.figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.PINK);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.PINK);
            this.blockThree = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.PINK);
            this.blockFour = new Block(blockWidth, blockHeight, startX - (1 * addX), startY - (1 * addY), Color.PINK);
        }
        else if (this.figurePosition == 1) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.PINK);
            this.blockTwo = new Block(blockWidth, blockHeight, startX - (1 * addX), startY, Color.PINK);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.PINK);
            this.blockFour = new Block(blockWidth, blockHeight, startX + (1 * addX), startY - (1 * addY), Color.PINK);
        }
        this.randomFigure = Color.PINK;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Creates figure three
     */
    public void makeFigureSeven() {
        figurePosition %= 2;
        if (this.figurePosition == 0) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.DARK_GRAY);
            this.blockTwo = new Block(blockWidth, blockHeight, startX, startY + (1 * addY), Color.DARK_GRAY);
            this.blockThree = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.DARK_GRAY);
            this.blockFour = new Block(blockWidth, blockHeight, startX + (1 * addX), startY - (1 * addY), Color.DARK_GRAY);
        }
        else if (this.figurePosition == 1) {
            this.blockOne = new Block(blockWidth, blockHeight, startX, startY, Color.DARK_GRAY);
            this.blockTwo = new Block(blockWidth, blockHeight, startX + (1 * addX), startY, Color.DARK_GRAY);
            this.blockThree = new Block(blockWidth, blockHeight, startX, startY - (1 * addY), Color.DARK_GRAY);
            this.blockFour = new Block(blockWidth, blockHeight, startX - (1 * addX), startY - (1 * addY), Color.DARK_GRAY);
        }
        this.randomFigure = Color.DARK_GRAY;
        this.add(blockOne);
        this.add(blockTwo);
        this.add(blockThree);
        this.add(blockFour);
    }

    /**
     * Returns tetris block size.
     *
     * @return int Tetris block size
     */
    public int getBlockSize() {
        return this.blockSize;
    }

    /**
     * Returns tetris figure color.
     *
     * @return Color tetris figure color
     */
    public Color getColor() {
        return this.randomFigure;
    }

    /**
     * Returns the highest x-coordinate of all four tetris blocks.
     *
     * @return int Highest x-coordinate of all four tetris blocks
     */
    public int getHighestX() {
        int max = this.blockOne.getX();
        if (this.blockTwo.getX() > max) {
            max = this.blockTwo.getX();
        }
        if (this.blockThree.getX() > max) {
            max = this.blockThree.getX();
        }
        if (this.blockFour.getX() > max) {
            max = this.blockFour.getX();
        }
        return max;
    }

    /**
     * Returns the lowest x-coordinate of all four tetris blocks.
     *
     * @return int Lowest x-coordinate of all four tetris blocks
     */
    public int getLowestX() {
        int min = this.blockOne.getX();
        if (this.blockTwo.getX() < min) {
            min = this.blockTwo.getX();
        }
        if (this.blockThree.getX() < min) {
            min = this.blockThree.getX();
        }
        if (this.blockFour.getX() < min) {
            min = this.blockFour.getX();
        }

        return min;
    }

    /**
     * Returns the highest y-coordinate of all four tetris blocks.
     *
     * @return int Highest y-coordinate of all four tetris blocks
     */
    public int getHighestY() {
        int max = this.blockOne.getY();
        if (this.blockTwo.getY() > max) {
            max = this.blockTwo.getY();
        }
        if (this.blockThree.getY() > max) {
            max = this.blockThree.getY();
        }
        if (this.blockFour.getY() > max) {
            max = this.blockFour.getY();
        }
        return max;
    }

    /**
     * Returns the lowest y-coordinate of all four tetris blocks.
     *
     * @return int Lowest y-coordinate of all four tetris blocks
     */
    public int getLowestY() {
        int min = this.blockOne.getY();
        if (this.blockTwo.getY() < min) {
            min = this.blockTwo.getY();
        }
        if (this.blockThree.getY() < min) {
            min = this.blockThree.getY();
        }
        if (this.blockFour.getY() < min) {
            min = this.blockFour.getY();

        }
        return min;
    }

    /**
     * Move tetris figure down.
     */
    public void moveDown() {
        this.setLocation(this.getX(), this.getY() + (1 * addY));
        this.repaint();
    }

    /**
     * Move tetris figure left.
     */
    public void moveLeft() {
        this.setLocation(this.getX() - (1 * addX), this.getY());
        this.repaint();
    }

    /**
     * Move tetris figure right.
     */
    public void moveRight() {
        this.setLocation(this.getX() + (1 * addX), this.getY());
        this.repaint();
    }

    /**
     * Left rotation of tetris figure. All cases are implemented in this method.
     */
    public void rotateLeft() {
        this.removeAll();
        if (this.randomFigure.equals(Color.RED)) {
            this.figurePosition = (this.figurePosition + 1) % 2;
            this.makeFigureOne();
        }
        else if (this.randomFigure.equals(Color.GREEN)) {
            this.figurePosition = (this.figurePosition + 1) % 4;
            this.makeFigureTwo();
        }
        else if (this.randomFigure.equals(Color.ORANGE)) {
            this.figurePosition = (this.figurePosition + 1) % 4;
            this.makeFigureThree();
        }
        else if (this.randomFigure.equals(Color.BLUE)) {
            this.figurePosition = (this.figurePosition + 1) % 4;
            this.makeFigureFour();
        }
        else if (this.randomFigure.equals(Color.YELLOW)) {
            this.figurePosition = (this.figurePosition + 1) % 4;
            this.makeFigureFive();
        }
        else if (this.randomFigure.equals(Color.PINK)) {
            this.figurePosition = (this.figurePosition + 1) % 2;
            this.makeFigureSix();
        }
        else if (this.randomFigure.equals(Color.DARK_GRAY)) {
            this.figurePosition = (this.figurePosition + 1) % 2;
            this.makeFigureSeven();
        }
        this.repaint();
    }

    /**
     * Pre rotation of the tetris figure. Used for check if rotate left is allowed.
     *
     * @param color Color Current color of the tetris figure
     * @return int[] Tetris figure positions -> preLowestX and preHighestX
     */
    public int[] preRotate(Color color) {
        int position = this.figurePosition;
        int[] preX = new int[2];
        int preLowestX = 0;
        int preHighestX = 420;
        if (color.equals(Color.RED)) {
            if (position == 0) {
                position = 1;
            }
            else if (position == 1) {
                position = 0;
            }
            if (position == 0) {
                preLowestX = this.getX() + (3 * addX);
                preHighestX = this.getX() + (6 * addX);
            }
            else if (position == 1) {
                preLowestX = this.getX() + (5 * addX);
                preHighestX = this.getX() + (6 * addX);
            }
        }
        else if (color.equals(Color.GREEN)) {
            if (position == 0) {
                position = 1;
            }
            else if (position == 1) {
                position = 2;
            }
            else if (position == 2) {
                position = 3;
            }
            else if (position == 3) {
                position = 0;
            }
            if (position == 0) {
                preLowestX = this.getX() + (3 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 1) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (7 * addX);
            }
            else if (position == 2) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 3) {
                preLowestX = this.getX() + (3 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
        }
        else if (color.equals(Color.ORANGE)) {
            if (position == 0) {
                position = 1;
            }
            else if (position == 1) {
                position = 2;
            }
            else if (position == 2) {
                position = 3;
            }
            else if (position == 3) {
                position = 0;
            }
            if (position == 0) {
                preLowestX = this.getX() + (3 * addX);
                preHighestX = this.getX() + (6 * addX);
            }
            else if (position == 1) {
                preLowestX = this.getX() + (5 * addX);
                preHighestX = this.getX() + (7 * addX);
            }
            else if (position == 2) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 3) {
                preLowestX = this.getX() + (3 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
        }
        else if (color.equals(Color.BLUE)) {
            if (position == 0) {
                position = 1;
            }
            else if (position == 1) {
                position = 2;
            }
            else if (position == 2) {
                position = 3;
            }
            else if (position == 3) {
                position = 0;
            }
            if (position == 0) {
                preLowestX = this.getX() + (5 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 1) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 2) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 3) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (6 * addX);
            }
        }
        else if (color.equals(Color.PINK)) {
            if (position == 0) {
                position = 1;
            }
            else if (position == 1) {
                position = 0;
            }
            if (position == 0) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 1) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (6 * addX);
            }
        }
        else if (color.equals(Color.DARK_GRAY)) {
            if (position == 0) {
                position = 1;
            }
            else if (position == 1) {
                position = 0;
            }
            if (position == 0) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (4 * addX);
            }
            else if (position == 1) {
                preLowestX = this.getX() + (4 * addX);
                preHighestX = this.getX() + (6 * addX);
            }
        }
        preX[0] = preLowestX;
        preX[1] = preHighestX;
        return preX;
    }

    /**
     * Right rotation of tetris figure. All cases are implemented in this method.
     */
    public void rotateRight() {
        this.removeAll();
        if (this.randomFigure.equals(Color.RED)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 1;
            }
            this.makeFigureOne();
        }
        else if (this.randomFigure.equals(Color.GREEN)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 3;
            }
            this.makeFigureTwo();
        }
        else if (this.randomFigure.equals(Color.ORANGE)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 3;
            }
            this.makeFigureThree();
        }
        else if (this.randomFigure.equals(Color.BLUE)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 3;
            }
            this.makeFigureFour();
        }
        else if (this.randomFigure.equals(Color.YELLOW)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 3;
            }
            this.makeFigureFive();
        }
        else if (this.randomFigure.equals(Color.PINK)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 1;
            }
            this.makeFigureSix();
        }
        else if (this.randomFigure.equals(Color.DARK_GRAY)) {
            this.figurePosition--;
            if (this.figurePosition == -1) {
                this.figurePosition = 1;
            }
            this.makeFigureSeven();
        }
        this.repaint();
    }

    /**
     * Creates tetris figure one for add to info panel.
     *
     * @return Component Return tetris figure one for add to info panel
     */
    public Component getFigureOne() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 20, Color.RED);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 60, 20, Color.RED);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 40, 20, Color.RED);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 20, 20, Color.RED);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Creates tetris figure two for add to info panel.
     *
     * @return Component Return tetris figure two for add to info panel
     */
    public Component getFigureTwo() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 40, Color.GREEN);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 60, 40, Color.GREEN);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 80, 20, Color.GREEN);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 80, 0, Color.GREEN);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Creates tetris figure three for add to info panel.
     *
     * @return Component Return tetris figure three for add to info panel
     */
    public Component getFigureThree() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 40, Color.ORANGE);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 100, 40, Color.ORANGE);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 80, 20, Color.ORANGE);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 80, 0, Color.ORANGE);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Creates static tetris figure four for add to info panel.
     *
     * @return Component Return tetris figure four for add to info panel
     */
    public Component getFigureFour() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 40, Color.BLUE);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 80, 60, Color.BLUE);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 100, 40, Color.BLUE);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 80, 20, Color.BLUE);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Creates tetris figure five for add to info panel.
     *
     * @return Component Return tetris figure five for add to info panel
     */
    public Component getFigureFive() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 40, Color.YELLOW);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 100, 40, Color.YELLOW);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 80, 20, Color.YELLOW);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 100, 20, Color.YELLOW);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Creates tetris figure six for add to info panel.
     *
     * @return Component Return tetris figure six for add to info panel
     */
    public Component getFigureSix() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 40, Color.PINK);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 80, 60, Color.PINK);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 60, 40, Color.PINK);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 60, 20, Color.PINK);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Creates tetris figure seven for add to info panel.
     *
     * @return Component Return tetris figure seven for add to info panel
     */
    public Component getFigureSeven() {
        Figure returnFigure = new Figure();
        returnFigure.blockOne = new Block(sBlockWidth, sBlockHeight, 80, 40, Color.DARK_GRAY);
        returnFigure.blockTwo = new Block(sBlockWidth, sBlockHeight, 80, 60, Color.DARK_GRAY);
        returnFigure.blockThree = new Block(sBlockWidth, sBlockHeight, 100, 40, Color.DARK_GRAY);
        returnFigure.blockFour = new Block(sBlockWidth, sBlockHeight, 100, 20, Color.DARK_GRAY);
        returnFigure.setVisible(true);
        returnFigure.add(returnFigure.blockOne);
        returnFigure.add(returnFigure.blockTwo);
        returnFigure.add(returnFigure.blockThree);
        returnFigure.add(returnFigure.blockFour);
        return returnFigure;
    }

    /**
     * Removes all block on this tetris figure.
     */
    public void removeBlocks() {
        this.removeAll();
    }

}
