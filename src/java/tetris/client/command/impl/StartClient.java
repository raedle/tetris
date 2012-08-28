package tetris.client.command.impl;

import tetris.client.TetrisClient;
import tetris.client.command.Clientable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>StartClient</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 18:19:42
 *
 * @author Roman R&auml;dle
 * @version $Id: StartClient.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class StartClient implements Clientable {

    public void execute(TetrisClient tetrisClient) {
        tetrisClient.startGame();
    }

    public String getMessageKey() {
        return "tetris.client.command.impl.StartClient";
    }
}
