package tetris.client;

import tetris.server.command.Serverable;
import tetris.command.Sendable;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * TODO: document me!!!
 *
 * <p>Headline: ServerClient.ThreadClientout</p>
 * <p>Description: Implements the ObjectOutputStream.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version $Id: ClientOutput.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */

public class ClientOutput extends Thread {

    //tetris client object output stream
    private ObjectOutputStream outputStream;

    //command list
    private List<Sendable> sendables;

    /**
     * Add command object to command list.
     *
     * @param sendable SimpleCommandObject Tetris command object
     */
    public void addSendable(Serverable sendable) {
        this.sendables.add(sendable);
    }

    //tetris client dummy
    private final Object clientDummy;

    /**
     * Returns client dummy object.
     *
     * @return Object Client dummy
     */
    public Object getClientDummy() {
        return this.clientDummy;
    }

    //client player name
    private String clientName = "Player";

    /**
     * Returns client name.
     *
     * @return String Client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Set client name.
     *
     * @param clientName String Client name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    //game is over boolean
    public boolean gameIsOverNow = false;

    /**
     * Initialize the thread client outputStream with socket socket, client clientDummy and client name.
     *
     * @param socket     Socket Tetris socket socket
     * @param clientDummy      Object Client clientDummy
     * @param clientName String Client name
     */
    public ClientOutput(Socket socket, Object clientDummy, String clientName) {
        this.clientDummy = clientDummy;
        this.clientName = clientName;

        sendables = new ArrayList<Sendable>();

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implements the run method
     */
    public void run() {

        //noinspection InfiniteLoopStatement
        while (true) {

            try {
                synchronized (clientDummy) {
                    clientDummy.wait();
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
                    ioe.printStackTrace();
                }
            }
        }
    }
}
