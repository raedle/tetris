package tetris.client;

import java.io.*;
import java.net.*;
import javax.swing.*;

import tetris.gui.*;
import tetris.gui.battlefield.BattleField;
import tetris.server.TetrisServer;
import tetris.server.command.impl.StartGameRequest;

/**
 * <p>Headline: tetris.client.TetrisClient</p>
 * <p>Description: This client implements a tetris client inherit the socket connection.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class TetrisClient implements Serializable {

    // The client dummy is used for synchronized
    // client output.
    private final Object clientDummy = new Object();

    public Object getClientDummy() {
        return clientDummy;
    }

    private BattleField battleField;

    public BattleField getBattleField() {
        return battleField;
    }

    //tetris client socket to tetris server
    private Socket clientSocket;

    //tetris client name
    private String playerName = "Player";

    /**
     * Returns client player name.
     *
     * @return String Client player name
     */
    public String getPlayerName() {
        return this.playerName;
    }

    //tetris server ip
    private String host = "localhost";

    //tetris server port
    private int port = 8181;

    //tetris client running boolean
    protected boolean running;

    //thread client in
    private ClientInput input;

    //thread client outputStream
    private ClientOutput output;

    /**
     * Returns thread client outputStream.
     *
     * @return ClientOutput Tetris client thread output stream
     */
    public ClientOutput getOutput() {
        return output;
    }

    //tetris server
    protected TetrisServer tetrisServer = null;

    //client ID
    protected int clientId = 0;

    public int getClientId() {
        return clientId;
    }

    /**
     * Set client clientId.
     *
     * @param clientId int Each client get a clientId number
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    protected ClientFrame clientFrame;

    /**
     * Constructor initialize host, IPStatic, port and set running true.
     *
     * @param host         String An i-net adress like "localhost" or "192.168.1.20"
     * @param port       int A port number like 8181
     * @param clientFrame         ClientFrame Tetris client tetrisFrame
     * @param clientName String Tetris client name
     */
    public TetrisClient(String host, int port, ClientFrame clientFrame, String clientName) {
        this.host = host;
        this.port = port;
        this.clientFrame = clientFrame;
        this.playerName = clientName;

        try {
            this.run();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(clientFrame.getContentPane(), "Es konnte an " + host + ":" + port + " kein Server gefunden werden!", "Verbindungsfehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method open a client socket to server. In addition initialize a input and a output
     * for this client. Furthermore the battle field, client name and start game request will be
     * send and initialize.
     *
     * @throws IOException ???
     */
    public void run() throws IOException {

        //open new client socket connection
        clientSocket = new Socket(host, port);

        //start output and input for each client
        output = new ClientOutput(clientSocket, clientDummy, playerName);
        input = new ClientInput(clientSocket, this);
        output.start();

        /*
        * initialization of the battle field
        * !!! don't move this behind input.start(); because battleField should be generated
        * before input start.
        */
        battleField = new BattleField(this, clientFrame);
        clientFrame.getTetrisFrame().addInternalFrame(battleField);

        input.start();

        //set client name (ClientFrame -> playerName text field)
        output.setClientName(playerName);

        //send start game request to server
        output.addSendable(new StartGameRequest(playerName));
        try {
            synchronized (clientDummy) {
                clientDummy.notifyAll();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns tetris client socket.
     *
     * @return Socket Tetris client socket
     */
    public Socket getSocket() {
        return this.clientSocket;
    }

    /**
     * Output game start.
     */
    public void startGame() {
        System.out.println(playerName + ": Spiel gestartet.");
    }

    /**
     * Output game stop.
     */
    public void stopGame() {
        this.running = false;
    }

    /**
     * Show final points on tetris clients battle field
     *
     * @param clientPoints int Final tetris client points
     */
    public void setFinalPoints(int clientPoints) {
        this.battleField.showFinalPoints(clientPoints);
    }
}
