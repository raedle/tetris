package tetris.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import tetris.client.ThreadTetrisClient;
import tetris.gui.figure.Figure;

/**
 * TODO: document me!!!
 *
 * <p>Headline: GUI.ClientFrame</p>
 * <p>Description: This class implements a Tetris Client GUI.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version $Id: ClientFrame.java,v 1.1.1.1 2006/03/23 23:35:49 raedler Exp $
 */

public class ClientFrame extends JFrame {

    //tetris tetrisFrame
    public TetrisFrame tetrisFrame = null;

    //client server panel -> left
    public JPanel clientServerPanel = null;

    //client panel in client server panel
    private JPanel clientPanel = null;

    //server panel in client server panel
    private JPanel serverPanel = null;

    //figure panel in client server panel
    private JPanel figurePanel = null;

    //points panel in client panel
    public JPanel pointsPanel = null;

    //singleplayer button
    private JButton bSinglePlayer = null;

    //mutliplayer button
    private JButton bMultiPlayer = null;

    //text field for input name, ip, port, serverPort, absence player, playtime
    private JTextField clientName = null;
    private JTextField host = null;
    private JTextField port = null;
    private JTextField tfServerPort = null;
    private JTextField tfAbsencePlayer = null;
    private JTextField tfPlayTime = null;

    //points of tetris client
    protected int clientPoints = 0;

    //tetris points label
    protected JLabel lPoints = null;

    //used for nextFigureForInfoPanel -> the last visible figure
    protected Figure lastFigure = null;

    /**
     * constructor initialize client panel
     */
    public ClientFrame(TetrisFrame tf) {
        tetrisFrame = tf;
        try {
            clientPanelInit();
            panelInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialization of client tetrisFrame panel.
     *
     * @throws Exception InitialException
     */
    private void clientPanelInit() throws Exception {
        clientServerPanel = new JPanel();
        //clientServerPanel.setPreferredSize(new Dimension(300, 600));
        clientServerPanel.setLayout(new GridLayout(3, 1, 0, 40));
        clientServerPanel.setVisible(true);

        clientPanel = new JPanel();
        clientPanel.setLayout(new GridLayout(6, 1));
        clientPanel.setVisible(true);

        serverPanel = new JPanel();
        serverPanel.setLayout(new GridLayout(6, 1));
        serverPanel.setVisible(true);

        figurePanel = new JPanel();
        figurePanel.setLayout(new GridLayout(2, 1));
        JLabel figureLabel = new JLabel("Nächster Tetris Stein:");
        figurePanel.add(figureLabel);
        figurePanel.setVisible(true);

        pointsPanel = new JPanel();
        pointsPanel.setLayout(new GridLayout(1, 2));
        pointsPanel.setVisible(true);
    }

    /**
     * Panels for client tetrisFrame.
     *
     * @throws Exception InitialException
     */
    private void panelInit() throws Exception {
        //initialization of panels for client panel
        JPanel pName = new JPanel(new GridLayout(1, 2));
        JPanel pIP = new JPanel(new GridLayout(1, 2));
        JPanel pPort = new JPanel(new GridLayout(1, 2));
        JPanel pMultiSingleButton = new JPanel(new GridLayout(1, 2));

        //initialization of name label and name text field panel
        JLabel lClientName = new JLabel("Spielername:");
        clientName = new JTextField();
        clientName.addActionListener(new ClientNameAction());
        pName.add(lClientName);
        pName.add(clientName);

        //initialization of ip label and ip address panel
        JLabel lIP = new JLabel("Server host:");
        host = new JTextField("localhost");
        host.addActionListener(new HostAction());
        pIP.add(lIP);
        pIP.add(host);

        //initialization of port label and ip address panel
        JLabel lPort = new JLabel("Server Port:");
        port = new JTextField("8181");
        port.addActionListener(new HostAction());
        pPort.add(lPort);
        pPort.add(port);

        //initialization of single and multiplayer button panel
        bSinglePlayer = new JButton("Einzelspieler");
        bSinglePlayer.addActionListener(new SinglePlayerAction());
        bSinglePlayer.setEnabled(false);
        pMultiSingleButton.add(bSinglePlayer);

        //initialization of mutliplayer button
        bMultiPlayer = new JButton("Multiplayer");
        bMultiPlayer.addActionListener(new MultiPlayerAction());
        bMultiPlayer.setEnabled(false);
        pMultiSingleButton.add(bMultiPlayer);

        //initialization of points panel
        JLabel pointsLabel = new JLabel("Punkte: ");
        pointsLabel.setVisible(true);
        lPoints = new JLabel("0");
        lPoints.setVisible(true);
        pointsPanel.add(pointsLabel);
        pointsPanel.add(lPoints);

        //set panels on client panel
        clientPanel.add(new JLabel("Tetris Client"));
        clientPanel.add(pName);
        clientPanel.add(pIP);
        clientPanel.add(pPort);
        clientPanel.add(pMultiSingleButton);
        clientPanel.add(pointsPanel);

        //initialization of panels for server panel
        JPanel pServerPort = new JPanel(new GridLayout(1, 2));
        JPanel pAbsencePlayer = new JPanel(new GridLayout(1, 2));
        JPanel pPlayTime = new JPanel(new GridLayout(1, 2));
        JPanel pStartServerButton = new JPanel(new GridLayout(1, 1));

        //initialization of name label and name text field panel
        JLabel lServerPort = new JLabel("Port:");
        tfServerPort = new JTextField("8181");
        pServerPort.add(lServerPort);
        pServerPort.add(tfServerPort);

        //initialization of ip label and ip address panel
        JLabel lAbsencePlayer = new JLabel("Anzahl Spieler:");
        tfAbsencePlayer = new JTextField("1");
        pAbsencePlayer.add(lAbsencePlayer);
        pAbsencePlayer.add(tfAbsencePlayer);

        //initialization of port label and ip address panel
        JLabel lPlayTime = new JLabel("Spielzeit:");
        tfPlayTime = new JTextField("600000");
        pPlayTime.add(lPlayTime);
        pPlayTime.add(tfPlayTime);

        //initialization of server start button panel
        JButton bStartServer = new JButton("Tetris Server starten");
        bStartServer.addActionListener(new StartServerAction());
        pStartServerButton.add(bStartServer);

        //set panels on server panel
        serverPanel.add(new JLabel("Tetris Server"));
        serverPanel.add(pServerPort);
        serverPanel.add(pAbsencePlayer);
        serverPanel.add(pPlayTime);
        serverPanel.add(pStartServerButton);

        //set panels on client and server panel
        clientServerPanel.add(clientPanel);
        clientServerPanel.add(serverPanel);
        clientServerPanel.add(figurePanel);
    }

    /**
     * Prints the next tetris figure on tetris tetrisFrame.
     *
     * @param color Color Tetris figure color
     */
    public void addNextFigureForInfo(Color color) {
        if (lastFigure != null) {
            figurePanel.remove(lastFigure);
        }

        lastFigure = new Figure();

        if (color.equals(Color.RED)) {
            lastFigure = (Figure) lastFigure.getFigureOne();
        }
        else if (color.equals(Color.GREEN)) {
            lastFigure = (Figure) lastFigure.getFigureTwo();
        }
        else if (color.equals(Color.ORANGE)) {
            lastFigure = (Figure) lastFigure.getFigureThree();
        }
        else if (color.equals(Color.BLUE)) {
            lastFigure = (Figure) lastFigure.getFigureFour();
        }
        else if (color.equals(Color.YELLOW)) {
            lastFigure = (Figure) lastFigure.getFigureFive();
        }
        else if (color.equals(Color.PINK)) {
            lastFigure = (Figure) lastFigure.getFigureSix();
        }
        else if (color.equals(Color.DARK_GRAY)) {
            lastFigure = (Figure) lastFigure.getFigureSeven();
        }
        lastFigure.setLocation(0, 0);
        figurePanel.add(lastFigure);
        figurePanel.validate();
    }

    /**
     * Set client points on points panel.
     *
     * @param points int Points to add with clientPoints and set on points panel
     */
    public void setClientPointsForInfo(int points) {
        pointsPanel.remove(lPoints);
        clientPoints += points;
        lPoints = new JLabel(Integer.toString(clientPoints));
        lPoints.setVisible(true);
        pointsPanel.add(lPoints);
        pointsPanel.validate();
    }

    /**
     * By press playerName text field bSinglePlayer and bMultiPlayer will enable
     *
     * @param e ActionEvent
     */
    void tfName_actionPerformed(ActionEvent e) {


    }

    class ClientNameAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (clientName.getText().equals("")) {
                bSinglePlayer.setEnabled(false);
                bMultiPlayer.setEnabled(false);
                JOptionPane.showMessageDialog(tetrisFrame, "Bitte Spielername eingeben!", "Fehler", 2);
            }
            else if (!clientName.getText().equals("") || !host.getText().equals("")) {
                bSinglePlayer.setEnabled(true);
                bMultiPlayer.setEnabled(true);
            }
            else {
                bSinglePlayer.setEnabled(true);
                bMultiPlayer.setEnabled(false);
            }
        }
    }

    class HostAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (clientName.getText().equals("") || host.getText().equals("")) {
                bSinglePlayer.setEnabled(false);
                bMultiPlayer.setEnabled(false);
                JOptionPane.showMessageDialog(tetrisFrame, "Bitte Spielername eingeben!", "Fehler", 2);
            }
            else {
                bSinglePlayer.setEnabled(true);
                bMultiPlayer.setEnabled(true);
            }
        }
    }

    class StartServerAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Integer serverPort = Integer.parseInt(ClientFrame.this.tfServerPort.getText());
            Integer players = Integer.parseInt(tfAbsencePlayer.getText());
            Integer playTime = Integer.parseInt(tfPlayTime.getText());

            new ServerFrame(serverPort, players, playTime, tetrisFrame);
        }
    }

    class SinglePlayerAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("The single player mode is unsupported at the time.");
        }
    }

    class MultiPlayerAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String host = ClientFrame.this.host.getText();
            Integer port = Integer.parseInt(ClientFrame.this.port.getText());
            String clientName = ClientFrame.this.clientName.getText();

            new ThreadTetrisClient(host, port, clientName, ClientFrame.this);
        }
    }

    /**
     * Returns actual tetris tetrisFrame window.
     *
     * @return TetrisFrame Tetris tetrisFrame window
     */
    public TetrisFrame getTetrisFrame() {
        return tetrisFrame;
    }
}
