package tetris.server.command.impl;

import tetris.server.command.Serverable;
import tetris.server.TetrisServer;

/**
 * TODO: document me!!!
 * <p/>
 * <code>StartGameRequest</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 20:23:23
 *
 * @author Roman R&auml;dle
 * @version $Id: StartGameRequest.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class StartGameRequest implements Serverable {

    private String playerName;

    public StartGameRequest() {
    }

    public StartGameRequest(String clientName) {
        this.playerName = clientName;
    }

    public void execute(TetrisServer tetrisServer) {
        tetrisServer.getLog().info("The player \"" + playerName + "\" requests to start the game.");
    }
}
