package tetris.server;

import java.io.*;
import java.net.*;
import java.util.*;

import tetris.gui.figure.Figure;

import tetris.server.timer.TickTask;
import tetris.server.command.impl.StartGameRequest;
import tetris.server.command.impl.StopGameRequest;
import tetris.server.logging.Logable;
import tetris.client.command.impl.ClientId;
import tetris.client.command.impl.FigureResponse;
import tetris.client.command.impl.Points;

/**
 * TODO: document me!!!
 *
 * <p>Headline: tetris.server.TetrisServer</p>
 * <p>Description: This class implements the Tetris Server.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version $Id: TetrisServer.java,v 1.1.1.1 2006/03/23 23:35:55 raedler Exp $
 */

public class TetrisServer {

    //tetris clientSocket
    protected Socket clientSocket = null;

    //client threads in stack
    protected List<ServerInput> serverInputs;

    //client threads outputStream stack
    protected List<ServerOutput> serverOutputs;

    public List<ServerOutput> getServerOutputs() {
        return serverOutputs;
    }

    //tetris figure list
    protected List<Figure> figures;

    //number of tetris clients
    protected int numberClients = 0;

    //number of tetris players
    protected int numberPlayers = 1;

    //tetris server socket
    protected ServerSocket serverSocket = null;

    //tick dummy -> used for tetris thread outputStream
    protected final Object tickDummy = new Object();

    //tetris client points array
    public int[] clientPoints = null;

    //sleep time
    protected int time = 4000;

    //tetris thread timer task -> tick
    private TickTask ticker;

    public TickTask getTicker() {
        return ticker;
    }

    //tetris play time
    private int playTime = 0;

    //to break tetris server
    public boolean breakServer = false;

    private Logable log;

    public Logable getLog() {
        return log;
    }

    public void setLog(Logable log) {
        this.log = log;
    }

    /**
     * Consructor creates a stack for client threads in and client threads outputStream and the first figure
     * list for the clients.
     */
    public TetrisServer(Logable log) {
        this.log = log;

        serverInputs = new ArrayList<ServerInput>();
        serverOutputs = new ArrayList<ServerOutput>();

        //list with figures for tetris clients
        figures = new ArrayList<Figure>();

        for (int i = 0; i < 10; i++) {
            Figure f = new Figure(120, 60, 20, 20);
            figures.add(f);
        }
    }

    /**
     * Start method for the tetris server
     *
     * @param port  int The server running port
     * @param playTime int Tetris play time
     * @throws IOException ???
     */
    public void runTetrisServer(int port, int playTime) throws IOException {

        this.playTime = playTime;

        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException ioe) {
            log.error("Tetris Server: Couldn't listen on port: " + port);
            ioe.printStackTrace();
        }

        log.info("The absence of players: " + numberPlayers);

        while (numberPlayers > 0) {
            try {
                clientSocket = serverSocket.accept();

                //start input and output for this client on server side
                ServerOutput output = new ServerOutput(clientSocket, tickDummy);
                ServerInput input = new ServerInput(clientSocket, output, this);
                output.start();
                input.start();

                //send figure list to client
                output.addSendable(new FigureResponse(figures));
                synchronized (tickDummy) {
                    tickDummy.notifyAll();
                }

                //send client id to client
                output.addSendable(new ClientId(numberPlayers));
                synchronized (tickDummy) {
                    tickDummy.notifyAll();
                }

                //add threads in and outputStream to stack
                serverInputs.add(input);
                serverOutputs.add(output);

                //player number decrement
                numberPlayers--;

                log.info("The absence of players: " + numberPlayers);

                numberClients++;

                try {
                    Thread.sleep(time);
                }
                catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            catch (IOException ioe) {

                log.error("The tetris client accept failed on port: " + port);

                ioe.printStackTrace();
            }
        }

        log.info("Log on time is over.");

        log.info("The game starts with " + serverInputs.size() + " players");

        //send start game command to each client
        for (ServerOutput serverOutput : serverOutputs) {
            serverOutput.addSendable(new StartGameRequest());
            log.info("The server sends the start game command.");
        }

        //TimerTask tick
        ticker = new TickTask(serverOutputs, 1000, tickDummy);
        ticker.start();

        while (playTime > 0) {

            if (breakServer) {
                break;
            }

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            playTime -= 1000;
        }

        //send finalpoints to each client
        int i = 0;
        for (ServerOutput serverOutput : serverOutputs) {
            serverOutput.addSendable(new Points(clientPoints[i]));

            log.info("Sending client points to each client.");

            synchronized (serverOutput.getTickDummy()) {
                serverOutput.getTickDummy().notifyAll();
            }

            i++;
        }

        log.info("The game is over.");

        try {
            Thread.sleep(2000);
        }

        catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        //send stop game command to each client
        for (ServerOutput serverOutput : serverOutputs) {

            serverOutput.addSendable(new Points(clientPoints[i]));

            synchronized (serverOutput.getTickDummy()) {
                serverOutput.getTickDummy().notifyAll();
            }

            serverOutput.addSendable(new StopGameRequest());
            synchronized (serverOutput.getTickDummy()) {
                serverOutput.getTickDummy().notifyAll();
            }
        }

        stopTick();
    }

    /**
     * Stops tick and break server.
     */
    public void stopTick() {
        this.breakServer = true;
        this.ticker.destroyTickTask();
    }

    /**
     * Set tetris number players.
     *
     * @param i int Number of tetris players
     */
    public void setNumberPlayers(int i) {
        this.numberPlayers = i;
        this.clientPoints = new int[i];
    }
}
