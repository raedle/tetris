package tetris.client;

import tetris.server.TetrisServer;
import tetris.client.TetrisClient;
import tetris.client.command.Clientable;
import tetris.server.command.Tickable;
import tetris.server.command.impl.ServerLog;

import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * <p>Headline: tetris.client.ClientInput</p>
 * <p>Description: Implements the ObjectInputStream.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class ClientInput extends Thread {

    //tetris client
    protected TetrisClient tetrisClient;

    //game is over boolean
    protected boolean gameIsOver;

    //tetris client object input stream
    protected ObjectInputStream in;

    //tetris server socket
    protected Socket serverSocket;

    //tetris server
    protected TetrisServer tetrisServer;

    //first time boolean
    public boolean firstTime = true;

    /**
     * Initialization of server socket, tetris client and ObjectInputStream.
     *
     * @param server Socket Tetris server socket
     * @param client TetrisClient Tetris client
     */
    ClientInput(Socket server, TetrisClient client) {
        this.serverSocket = server;
        this.tetrisClient = client;
        try {
            this.in = new ObjectInputStream(serverSocket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Start tetris client in thread.
     */
    public void run() {

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                // Read object of ObjectInputStream.
                Object o = in.readObject();

                if (o instanceof Tickable) {
                    tetrisClient.getBattleField().newRepaint();
                }
                else if (o instanceof Clientable) {
                    synchronized (tetrisClient.getClientDummy()) {
                        tetrisClient.getClientDummy().notifyAll();
                    }

                    Clientable clientable = (Clientable) o;

                    clientable.execute(tetrisClient);

                    tetrisClient.getOutput().addSendable(new ServerLog(ServerLog.Type.INFO, clientable.getMessageKey()));
                }
            }
            //in case the connection is broken ... close and exit app
            catch (IOException ioe) {
                JOptionPane.showMessageDialog(tetrisClient.getBattleField().getContentPane(), "Verbindung zu Server verloren!", "Fehler", JOptionPane.ERROR_MESSAGE);
                ioe.printStackTrace();
            }
            catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            }
        }
    }
}
