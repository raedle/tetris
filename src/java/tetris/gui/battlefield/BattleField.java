package tetris.gui.battlefield;

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;

import tetris.client.TetrisClient;
import tetris.server.command.impl.StopGameRequest;
import tetris.server.command.impl.FigureRequest;
import tetris.server.command.impl.ClientPoints;
import tetris.gui.figure.Figure;
import tetris.gui.figure.Block;
import tetris.gui.ClientFrame;

/**
 * <p>Headline: BattleField</p>
 * <p>Description: This class implements the tetris battle field.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class BattleField extends JInternalFrame {

    //figure list with tetris figures
    protected List<Figure> figures = null;

    //tetris tetrisFrame desktop pane
    //protected JDesktopPane desktopPane = null;

    //tetris tetrisFrame
    //protected TetrisFrame tetrisFrame;

    //battle field internal tetrisFrame
    //protected JInternalFrame battleFieldIntFrame = null;

    //tetris client/server buttons
    protected JButton multiButton = null;
    protected JButton singleButton = null;
    protected JButton startServerButton = null;

    //client name
    protected String playerName = null;

    //two-dimensional block array
    protected Block[][] blocksOnBattleField = null;

    protected TetrisClient tetrisClient;

    //client dummy to write on thread client outputStream
    protected final Object clientDummy;

    //first figure of tetris figure list
    public Figure firstFigure = null;

    //used for each new figure
    public boolean firstTime = true;

    //tetris battle field background
    protected BattleFieldRect bfr = null;

    //tetris client tetrisFrame
    protected ClientFrame clientFrame = null;

    public boolean gameOver = false;

    /**
     * Constructor initialize the battle field with tetris clients output stream
     *
     * @param tetrisClient
     */
    public BattleField(TetrisClient tetrisClient, ClientFrame clientFrame) {
        super("Battle Field");
        //tetrisFrame = clientFrame.getTetrisFrame();
        //desktopPane = tetrisFrame.desktopPane;

        //JFrame.setDefaultLookAndFeelDecorated(true);
        setClosable(true);
        setPreferredSize(new Dimension(290, 490));
        setSize(new Dimension(290, 490));
        addKeyListener(new BattleFieldKeyListener(this));
        setVisible(true);
        enableInputMethods(true);
        setFocusable(true);
        requestFocus();

        bfr = new BattleFieldRect();
        add(bfr);

        this.clientFrame = clientFrame;

        figures = new ArrayList<Figure>();
        blocksOnBattleField = new Block[13][31];

        this.tetrisClient = tetrisClient;
        this.clientDummy = tetrisClient.getClientDummy();
        this.playerName = tetrisClient.getPlayerName();

        //this.desktopPane.add(battleFieldIntFrame);
        firstTime = true;
    }

    /**
     * Paint complete battleField new; each time a tick is recommended in threadClientIn;
     * move current figure down or save it up in blocksOnBattlefield and take next figure
     * outputStream of figure List; in this case set points
     */

    public void newRepaint() {
        //first time figure shown on battleField
        if (firstTime) {

            if (figures != null && figures.size() > 0) {
                firstFigure = figures.remove(0);
                clientFrame.addNextFigureForInfo((figures.get(0)).getColor());
                add(firstFigure);
                validate();
            }

            firstTime = false;
        }

        //check shifting down figure
        if (this.ckeckShiftDown(firstFigure)) {
            this.firstFigure.moveDown();
        }
        else {
            firstTime = true;
            if (figures.size() <= 5) {

                //send get figure list request
                tetrisClient.getOutput().addSendable(new FigureRequest(playerName));

                try {
                    synchronized (clientDummy) {
                        clientDummy.notifyAll();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (firstFigure.getY() <= 20) {
                System.out.println("battle field is full");

                //send stop game request to server
                this.tetrisClient.getOutput().addSendable(new StopGameRequest(playerName));
                try {
                    synchronized (clientDummy) {
                        this.clientDummy.notifyAll();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                this.tetrisClient.getOutput().gameIsOverNow = true;
                return;
            }
            int x = this.firstFigure.getX();
            int y = this.firstFigure.getY();

            // save blocksoffigure in array; positions are taken from location ob battleField;
            // blocksize 20 pixel
            blocksOnBattleField[(x + firstFigure.getBlockOne().getX()) / 20][(y + firstFigure.getBlockOne().getY()) / 20] = firstFigure.getBlockOne();
            blocksOnBattleField[(x + firstFigure.getBlockTwo().getX()) / 20][(y + firstFigure.getBlockTwo().getY()) / 20] = firstFigure.getBlockTwo();
            blocksOnBattleField[(x + firstFigure.getBlockThree().getX()) / 20][(y + firstFigure.getBlockThree().getY()) / 20] = firstFigure.getBlockThree();
            blocksOnBattleField[(x + firstFigure.getBlockFour().getX()) / 20][(y + firstFigure.getBlockFour().getY()) / 20] = firstFigure.getBlockFour();

            firstFigure.removeBlocks();
            remove(firstFigure);
            System.out.println("set points command object");

            //send set points of client command
            clientFrame.setClientPointsForInfo(10);

            tetrisClient.getOutput().addSendable(new ClientPoints(playerName, tetrisClient.getClientId(), 10));
            synchronized (tetrisClient.getClientDummy()) {
                tetrisClient.getClientDummy().notifyAll();
            }

            // delete complete lines and set points
            lineManager();

            if (blocksOnBattleField != null) {
                paintBlocksOnBattleField();
            }
        }
    }

    /**
     * Add new linked list filled with tetris figures to this figure list.
     *
     * @param list LinkedList Filled with tetris figures
     */
    public void addNewFigureList(List<Figure> list) {
        this.figures.addAll(list);
    }

    /**
     * Returns current tetris figure.
     *
     * @return Figure Tetris figure
     */
    public Figure getCurrentFigure() {
        return this.firstFigure;
    }

    /**
     * Check if the parameter figure is allowed for move down.
     *
     * @param figure Figure Current tetris figure
     * @return boolean Return true if tetris figure is allowed for move down else false
     */
    public boolean ckeckShiftDown(Figure figure) {
        int x = figure.getX();
        int y = figure.getY();

        //check bounds
        if (y + figure.getHighestY() < 420) {
            if (y + figure.getHighestY() < 0) {
                return true;
            }
            //check bocksOnBattleField for free entries in next line, return true
            if ((blocksOnBattleField[(x + figure.getBlockOne().getX()) / 20][(y + figure.getBlockOne().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockTwo().getX()) / 20][(y + figure.getBlockTwo().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockThree().getX()) / 20][(y + figure.getBlockThree().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX()) / 20][(y + figure.getBlockFour().getY() + 20) / 20] == null)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * looking for complete lines on battleField, deleting them; move upper lines down; points
     */
    public void lineManager() {
        boolean filled = true;
        boolean moveDown = false;
        int count = 0;

        // looking for complete lines; check each cell equals null
        for (int i = this.blocksOnBattleField[0].length - 1; i > 1; i--) {
            for (int j = 1; j < this.blocksOnBattleField.length; j++) {
                if (this.blocksOnBattleField[j][i] == null) {
                    filled = false;
                    j = this.blocksOnBattleField.length;
                }
            }
            //found complete line(s) to delete
            if (filled) {
                moveDown = true;
                //set flag (dummyblock) in colum0 for as identifier
                this.blocksOnBattleField[0][i] = new Block();
                //count complete lines
                count++;
            }
            filled = true;
        }

        if (moveDown) {
            boolean control = false;

            //clone array blocksOnBattleField; delete blockOnBattleField
            Block[][] clone = (Block[][]) this.blocksOnBattleField.clone();
            this.blocksOnBattleField = null;
            this.blocksOnBattleField = new Block[13][31];

            //copy uncomplete lines frome clone to blockOnBattleField at new position
            for (int delete = 1; delete <= count; delete++) {
                for (int row = clone[0].length - 1; row >= count; row--) {
                    if (clone[0][row] == null && control == false) {
                        for (int col = clone.length - 1; col >= 0; col--) {
                            this.blocksOnBattleField[col][row] = clone[col][row];
                        }
                    }
                    else {
                        control = true;
                        for (int col = clone.length - 1; col >= 0; col--) {
                            this.blocksOnBattleField[col][row] = clone[col][row - delete];
                        }
                    }
                }
                control = false;
            }

            //clear intFrame
            removeAll();
            add(bfr);
            paintBlocksOnBattleField();
            //send set points of client command
            this.clientFrame.setClientPointsForInfo(100);
            tetrisClient.getOutput().addSendable(new ClientPoints(playerName, tetrisClient.getClientId(), 100));
            synchronized (tetrisClient.getClientDummy()) {
                tetrisClient.getClientDummy().notifyAll();
            }
        }
    }

    /**
     * Check if the parameter figure is allowed for shift left.
     *
     * @param figure Figure Current tetris figure on battle field
     * @return boolean Return true if figure is allowed for shift left else false
     */
    public boolean ckeckShiftLeft(Figure figure) {
        int x = figure.getX();
        int y = figure.getY();
        if (figure.getX() + figure.getLowestX() > 20) {
            return (blocksOnBattleField[(x + figure.getBlockOne().getX() - 20) / 20][(y + figure.getBlockOne().getY()) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockTwo().getX() - 20) / 20][(y + figure.getBlockTwo().getY()) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockThree().getX() - 20) / 20][(y + figure.getBlockThree().getY()) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() - 20) / 20][(y + figure.getBlockFour().getY()) / 20] == null);
        }
        else {
            return false;
        }
    }

    /**
     * Check if the parameter figure is allowed for shift right.
     *
     * @param figure Figure Current tetris figure on battle field
     * @return boolean Return true if figure is allowed for shift right else false
     */
    public boolean checkShiftRight(Figure figure) {
        int x = figure.getX();
        int y = figure.getY();
        if (figure.getX() + figure.getHighestX() <= 220) {
            return (this.blocksOnBattleField[(x + figure.getBlockOne().getX() + 20) / 20][(y + figure.getBlockOne().getY()) / 20] == null) &&
                    (this.blocksOnBattleField[(x + figure.getBlockTwo().getX() + 20) / 20][(y + figure.getBlockTwo().getY()) / 20] == null) &&
                    (this.blocksOnBattleField[(x + figure.getBlockThree().getX() + 20) / 20][(y + figure.getBlockThree().getY()) / 20] == null) &&
                    (this.blocksOnBattleField[(x + figure.getBlockFour().getX() + 20) / 20][(y + figure.getBlockFour().getY()) / 20] == null);
        }
        else {
            return false;
        }
    }

    /**
     * check rotation bounds for each figure and postion:
     * uses preRotate: simulate rotation and check new bounds in bounds of battlefield
     *
     * @param figure Figure
     * @return boolean
     */
    public boolean checkRotate(Figure figure) {
        int leftBorder = 0;
        int rightBorder = 260;

        //Figure one
        if (figure.getColor().equals(Color.RED) && figure.getFigurePosition() == 0) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.RED) && figure.getFigurePosition() == 1) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        //Figure two
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 0) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 1) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 2) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 3) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        //Figure three
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 0) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 1) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 2) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 3) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        //Figure four
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 0) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 1) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 2) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 3) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        //Figure six
        else if (figure.getColor().equals(Color.PINK) && figure.getFigurePosition() == 0) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.PINK) && figure.getFigurePosition() == 1) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        //Figure seven
        else if (figure.getColor().equals(Color.DARK_GRAY) && figure.getFigurePosition() == 0) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (figure.getColor().equals(Color.DARK_GRAY) && figure.getFigurePosition() == 1) {
            if (figure.preRotate(figure.getColor())[0] > leftBorder &&
                    figure.preRotate(figure.getColor())[1] < rightBorder) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }


    /**
     * paint each block found in array blocksOnBattleField and paint it on battlefield
     */
    public void paintBlocksOnBattleField() {

        boolean foundBlock = false;

        for (int i = 0; i < this.blocksOnBattleField.length; i++) {
            for (int j = 0; j < this.blocksOnBattleField[i].length; j++) {
                if (this.blocksOnBattleField[i][j] != null) {
                    //System.outputStream.println("Block bei " + i*20 + " " + j*20 + " gefunden!");
                    this.blocksOnBattleField[i][j].setLocation(i * 20, j * 20);
                    this.blocksOnBattleField[i][j].setVisible(true);
                    add(this.blocksOnBattleField[i][j]);
                    foundBlock = true;
                }
            }
            if (foundBlock) {
                foundBlock = false;
            }
        }
    }

    /**
     * show clientuser messagebox with final points, he made
     *
     * @param points int
     */
    public void showFinalPoints(int points) {
        JOptionPane.showMessageDialog(this, "Sie haben insgesamt: " + points + " Punkte erreicht!", "Spiel zu Ende...", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * check rotation for each figure and postion:
     *    use checkRotateLeft for checking bounds of rotated figure
     *    calculate new safeingpoints in array after rotation and check == null
     * @param figure Figure
     * @return boolean
     */
    public boolean checkRotateLeft(Figure figure) {
        int x = figure.getX();
        int y = figure.getStartY() + figure.getY();

        if (!checkRotate(figure)) {
            return false;
        }

        if (figure.getColor().equals(Color.RED) && figure.getFigurePosition() == 1) {
            if (blocksOnBattleField[(x + figure.getBlockFour().getX() + 20) / 20][(y + figure.getBlockFour().getY() - 20) / 20] == null &&
                    blocksOnBattleField[(x + figure.getBlockTwo().getX() - 20) / 20][(y + figure.getBlockTwo().getY() + 20) / 20] == null &&
                    blocksOnBattleField[(x + figure.getBlockOne().getX() - 40) / 20][(y + figure.getBlockFour().getY() + 40) / 20] == null) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.RED) && figure.getFigurePosition() == 0) {
            if (blocksOnBattleField[(x + figure.getBlockFour().getX() - 20) / 20][(y + figure.getBlockFour().getY() + 20) / 20] == null &&
                    blocksOnBattleField[(x + figure.getBlockTwo().getX() + 20) / 20][(y + figure.getBlockTwo().getY() - 20) / 20] == null &&
                    blocksOnBattleField[(x + figure.getBlockOne().getX() + 40) / 20][(y + figure.getBlockFour().getY() - 40) / 20] == null) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.PINK) && figure.getFigurePosition() == 0) {
            if ((blocksOnBattleField[(x + figure.getBlockThree().getX() + 20) / 20][(y + figure.getBlockThree().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() + 40) / 20][(y + figure.getBlockFour().getY()) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.PINK) && figure.getFigurePosition() == 1) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() + 20) / 20][(y + figure.getBlockTwo().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() - 40) / 20][(y + figure.getBlockFour().getY()) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.DARK_GRAY) && figure.getFigurePosition() == 0) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() - 20) / 20][(y + figure.getBlockTwo().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX()) / 20][(y + figure.getBlockFour().getY() + 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.DARK_GRAY) && figure.getFigurePosition() == 1) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() + 20) / 20][(y + figure.getBlockTwo().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX()) / 20][(y + figure.getBlockFour().getY() - 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 0) {
            if ((blocksOnBattleField[(x + figure.getBlockOne().getX() - 20) / 20][(y + figure.getBlockOne().getY()) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 1) {
            if ((blocksOnBattleField[(x + figure.getBlockOne().getX()) / 20][(y + figure.getBlockOne().getY() - 20) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 2) {
            if ((blocksOnBattleField[(x + figure.getBlockOne().getX() + 20) / 20][(y + figure.getBlockOne().getY()) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.BLUE) && figure.getFigurePosition() == 3) {
            if ((blocksOnBattleField[(x + figure.getBlockOne().getX()) / 20][(y + figure.getBlockOne().getY() + 20) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 0) {
            if ((blocksOnBattleField[(x + figure.getBlockThree().getX() + 20) / 20][(y + figure.getBlockThree().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() + 40) / 20][(y + figure.getBlockFour().getY() + 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 1) {
            if ((blocksOnBattleField[(x + figure.getBlockThree().getX() - 20) / 20][(y + figure.getBlockThree().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() - 40) / 20][(y + figure.getBlockFour().getY() + 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 2) {
            if ((blocksOnBattleField[(x + figure.getBlockThree().getX() - 20) / 20][(y + figure.getBlockThree().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() - 40) / 20][(y + figure.getBlockFour().getY() - 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.GREEN) && figure.getFigurePosition() == 3) {
            if ((blocksOnBattleField[(x + figure.getBlockThree().getX() + 20) / 20][(y + figure.getBlockThree().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() + 40) / 20][(y + figure.getBlockFour().getY() - 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 0) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() - 20) / 20][(y + figure.getBlockTwo().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() + 40) / 20][(y + figure.getBlockFour().getY() + 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 1) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() - 20) / 20][(y + figure.getBlockTwo().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() - 40) / 20][(y + figure.getBlockFour().getY() + 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 2) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() + 20) / 20][(y + figure.getBlockTwo().getY() - 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() - 40) / 20][(y + figure.getBlockFour().getY() - 40) / 20] == null)) {
                return true;
            }
        }
        else if (figure.getColor().equals(Color.ORANGE) && figure.getFigurePosition() == 3) {
            if ((blocksOnBattleField[(x + figure.getBlockTwo().getX() + 20) / 20][(y + figure.getBlockTwo().getY() + 20) / 20] == null) &&
                    (blocksOnBattleField[(x + figure.getBlockFour().getX() + 40) / 20][(y + figure.getBlockFour().getY() - 40) / 20] == null)) {
                return true;
            }
        }
        return false;
  }
}
