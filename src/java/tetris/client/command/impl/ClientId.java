package tetris.client.command.impl;

import tetris.client.TetrisClient;
import tetris.client.command.Clientable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>Clientable</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 19:13:50
 *
 * @author Roman R&auml;dle
 * @version $Id: ClientId.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class ClientId implements Clientable {

    private int id;

    public ClientId(int id) {
        this.id = id;
    }

    public void execute(TetrisClient tetrisClient) {
        tetrisClient.setClientId(id);
    }

    public String getMessageKey() {
        return "tetris.client.command.impl.ClientId";
    }
}
