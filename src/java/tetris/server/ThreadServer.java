package tetris.server;

import tetris.gui.ServerFrame;
import tetris.server.logging.Logable;

/**
 * <p>Headline: tetris.server.ThreadServer</p>
 * <p>Description: Implements the thread of tetris server.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class ThreadServer extends Thread {

    //tetris server port
    private int port = 8181;

    //player number
    private int numberPlayers = 1;

    //tetris play time
    private int playTime = 100000;

    private Logable log;

    /**
     * Initialize the tetris thread server with port, number of players and play time.
     *
     * @param port     int Server port
     * @param number   int Number of tetris players
     * @param playTime int Tetris play time
     */
    public ThreadServer(int port, int number, int playTime, Logable log) {
        this.port = port;
        this.numberPlayers = number;
        this.playTime = playTime;
        this.log = log;

        start();
    }

    /**
     * Implements the run method
     */
    public void run() {
        try {
            TetrisServer tetrisServer = new TetrisServer(log);
            tetrisServer.setNumberPlayers(numberPlayers);
            tetrisServer.runTetrisServer(port, playTime);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
