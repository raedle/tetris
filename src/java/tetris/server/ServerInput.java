package tetris.server;

import java.io.*;
import java.net.*;

import tetris.server.command.Serverable;

/**
 * <p>Headline: tetris.server.ServerInput</p>
 * <p>Description: Implements the server ObjectInputStream.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class ServerInput extends Thread {

    //client socket
    private Socket clientSocket = null;

    //thread server outputStream
    private ServerOutput serverOutput = null;

    //server object input stream
    private ObjectInputStream inputStream = null;

    //client name
    public String clientName = null;

    //tetris server
    public TetrisServer tetrisServer = null;

    /**
     * Initialize thread server inputStream with client socket, thread server outputStream and tetris server object.
     *
     * @param client Socket Tetris client socket
     * @param serverOutput   ServerOutput Thread server outputStream object
     * @param tetrisServer     TetrisServer Tetris server object
     */
    public ServerInput(Socket client, ServerOutput serverOutput, TetrisServer tetrisServer) {
        this.serverOutput = serverOutput;
        this.clientSocket = client;
        this.tetrisServer = tetrisServer;

        try {
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Implements the run method.
     */
    public void run() {
        //while socket connection is established do this loop
        while (true) {
            try {
                //if there is no object to read the loop rests here
                Object o = inputStream.readObject();

                if (o instanceof Serverable) {
                    ((Serverable) o).execute(tetrisServer);
                }
            }
            catch (IOException ioe) {
                tetrisServer.getLog().error(ioe.getMessage());
                ioe.printStackTrace();
            }
            catch (ClassNotFoundException cnfe) {
                tetrisServer.getLog().error(cnfe.getMessage());
                cnfe.printStackTrace();
            }
        }
    }

    /**
     * Returns client name.
     *
     * @return String Client name
     */
    public String getClientName() {
        return this.clientName;
    }
}
