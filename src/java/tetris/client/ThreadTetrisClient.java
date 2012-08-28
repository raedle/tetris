package tetris.client;

import tetris.gui.*;

/**
 * <p>Headline: tetris.client.ThreadTetrisClient</p>
 * <p>Description: This class implements a thread for tetris client.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class ThreadTetrisClient extends Thread {

    //server ip
    protected String host = "localhost";

    //server port
    protected int port = 8181;

    //client name
    protected String playerName = "Player";

    //tetris client tetrisFrame
    protected ClientFrame clientFrame;

    //tetris client
    public TetrisClient tetrisClient;

    /**
     * Constructor initialize the ip and port of the tetris server and tetris client player name.
     *
     * @param host        String host adress of the tetris server like "localhost" or "192.168.1.20"
     * @param port        int Port of the tetris server like 8181
     * @param clientName  String Tetris client player name
     * @param clientFrame ClientFrame Tetris client tetrisFrame
     */
    public ThreadTetrisClient(String host, int port, String clientName, ClientFrame clientFrame) {
        this.host = host;
        this.port = port;
        this.playerName = clientName;
        this.clientFrame = clientFrame;

        start();
    }

    /**
     * start tetris client thread
     */
    public void run() {
        tetrisClient = new TetrisClient(host, port, clientFrame, playerName);
    }
}
