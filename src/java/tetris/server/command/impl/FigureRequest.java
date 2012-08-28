package tetris.server.command.impl;

import tetris.server.command.Serverable;
import tetris.server.TetrisServer;
import tetris.server.ServerOutput;
import tetris.gui.figure.Figure;
import tetris.client.command.impl.FigureResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document me!!!
 * <p/>
 * <code>FigureRequest</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 20:11:24
 *
 * @author Roman R&auml;dle
 * @version $Id: FigureRequest.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class FigureRequest implements Serverable {

    private String playerName;

    public FigureRequest(String clientName) {
        this.playerName = clientName;
    }

    public void execute(TetrisServer tetrisServer) {

        tetrisServer.getLog().info("The player \"" + playerName + "\" requests new figures.");

        List<Figure> figures = new ArrayList<Figure>();

        // Creates a figure list with 10 figures.
        for (int i = 0; i < 10; i++) {
            figures.add(new Figure(100, 0, 20, 20));
        }

        for (ServerOutput serverOutput : tetrisServer.getServerOutputs()) {
            serverOutput.addSendable(new FigureResponse(figures));

            synchronized (serverOutput.getTickDummy()) {
                serverOutput.getTickDummy().notifyAll();
            }
        }
    }
}
