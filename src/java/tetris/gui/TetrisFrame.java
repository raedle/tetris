package tetris.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Headline: GUI.TetrisFrame</p>
 * <p>Description: This class implements a Tetris GUI.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class TetrisFrame extends JFrame {

    //tetris tetrisFrame -> heart of this tetris
    //public JFrame tetrisFrame = null;

    //tetris tetrisFrame desktop pane
    private JDesktopPane desktopPane = null;

    public void addInternalFrame(JInternalFrame internalFrame) {
        desktopPane.add(internalFrame);
    }

    //client server panel
    private JPanel pClientServer = null;

    //menu item new game
    private JMenuItem mGameNew = null;

    //tetris client tetrisFrame
    public ClientFrame cf = null;

    /**
     * Initialization of TetrisFrame
     */
    public TetrisFrame() {
        super("Tetris Frame");
        try {
            tetrisFrameInit();
            //new ClientFrame();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a MenuBar.
     *
     * @return MenuBar With game options
     */
    public JMenuBar menuBarInit() {
        JMenuBar mBar = new JMenuBar();

        //mGame menu
        JMenu mGame = new JMenu("Spiel");
        mGameNew = new JMenuItem("Neues Spiel");
        JMenuItem mGameExit = new JMenuItem("Beenden");
        mGame.add(mGameNew);
        mGame.add(mGameExit);
        mBar.add(mGame);

        //mHelp menu
        JMenu mHelp = new JMenu("Hilfe");
        JMenuItem mHelpAbout = new JMenuItem("Über Tetris");
        mHelp.add(mHelpAbout);
        mBar.add(mHelp);

        //add action listeners
        mGameNew.addActionListener(new TetrisFrame_mGameNew_actionAdapter(this));
        mGameExit.addActionListener(new TetrisFrame_mGameExit_actionAdapter(this));

        return mBar;
    }

    /**
     * This method initializes the tetris tetrisFrame and tetris tetrisFrame desktop pane.
     *
     * @throws Exception
     */
    public void tetrisFrameInit() throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);

        getContentPane().setBackground(Color.WHITE);
        setSize(new Dimension(900, 700));
        getContentPane().setLayout(new BorderLayout());
        setJMenuBar(menuBarInit());
        setResizable(true);
        setLocation(20, 20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        //initialization of client server panel
        cf = new ClientFrame(this);
        pClientServer = new JPanel();
        pClientServer.add(cf.clientServerPanel);
        getContentPane().add(pClientServer, BorderLayout.WEST);

        //initialization of tetris tetrisFrame desktop pane
        desktopPane = new JDesktopPane();
        desktopPane.setPreferredSize(new Dimension(1600, 1200));
        desktopPane.setBackground(Color.WHITE);
        desktopPane.setVisible(true);
        getContentPane().add(desktopPane, BorderLayout.CENTER);

        validate();
    }

    /**
     * Implements the start method.
     *
     * @param args String[] not used
     */
    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        new TetrisFrame();
    }

    /**
     * By press mGameExit menu item the system exit
     *
     * @param e ActionEvent
     */
    void mGameExit_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    class TetrisFrame_mGameExit_actionAdapter implements ActionListener {
        TetrisFrame adaptee;

        TetrisFrame_mGameExit_actionAdapter(TetrisFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.mGameExit_actionPerformed(e);
        }
    }

    /**
     * By press mGameNew menu item client tetrisFrame start
     *
     * @param e ActionEvent
     */
    void mGameNew_actionPerformed(ActionEvent e) {
        TetrisFrame tf = new TetrisFrame();
        //mGameNew.setEnabled(false);
    }

    class TetrisFrame_mGameNew_actionAdapter implements ActionListener {
        TetrisFrame adaptee;

        TetrisFrame_mGameNew_actionAdapter(TetrisFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.mGameNew_actionPerformed(e);
        }
    }
}
