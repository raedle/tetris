package tetris.server.command.impl;

import tetris.server.command.Serverable;
import tetris.server.TetrisServer;

/**
 * TODO: document me!!!
 * <p/>
 * <code>ClientPoints</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 20:27:18
 *
 * @author Roman R&auml;dle
 * @version $Id: ClientPoints.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class ClientPoints implements Serverable {

    private String clientName;

    private int clientID;

    private int points;

    public ClientPoints(String clientName, int clientID, int points) {
        this.clientName = clientName;
        this.clientID = clientID;
        this.points = points;
    }

    public void execute(TetrisServer tetrisServer) {

        tetrisServer.clientPoints[clientID - 1] += points;

        int max = tetrisServer.clientPoints[0];

        for (int i = 1; i < tetrisServer.clientPoints.length; i++) {
            if (max < tetrisServer.clientPoints[i]) {
                max = tetrisServer.clientPoints[i];
            }
        }

        if (max % 100 == 0) {
            tetrisServer.getTicker().accelerateTick(1.25);
        }
    }
}
