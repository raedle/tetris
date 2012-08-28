package tetris.server;

import tetris.command.Sendable;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * TODO: document me!!!
 *
 * <p>Headline: tetris.server.ServerOutput</p>
 * <p>Description: This class implements output stream of the server.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class ServerOutput extends Thread {

    //client socket
    protected Socket clientSocket;

    //tetris server output stream
    protected ObjectOutputStream outputStream;

    //command list
    protected List<Sendable> sendables;

    /**
     * Add command object to object list.
     *
     * @param sendable Object Tetris command object
     */
    public void addSendable(Sendable sendable) {
        sendables.add(sendable);
    }

    //tick dummy -> server dummy
    private final Object tickDummy;

    public Object getTickDummy() {
        return tickDummy;
    }

    /**
     * Initialize thread server outputStream with clientSocket socket and tick dummy object.
     *
     * @param clientSocket    Socket Tetris clientSocket socket
     * @param tickDummy Object Tick dummy object
     */
    public ServerOutput(Socket clientSocket, Object tickDummy) {
        this.clientSocket = clientSocket;
        this.tickDummy = tickDummy;

        sendables = new ArrayList<Sendable>();

        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Implements the run method.
     */
    public void run() {

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                synchronized (tickDummy) {
                    tickDummy.wait();
                }
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            while (!sendables.isEmpty()) {
                Sendable sendable = sendables.remove(0);
                try {
                    outputStream.writeObject(sendable);
                }
                catch (IOException ioe) {
                    System.out.println("write object bug");
                    ioe.printStackTrace();
                }
            }
        }
    }
}
