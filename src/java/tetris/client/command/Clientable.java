package tetris.client.command;

import tetris.client.TetrisClient;
import tetris.command.Sendable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>Clientable</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 19:10:17
 *
 * @author Roman R&auml;dle
 * @version $Id: Clientable.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public interface Clientable extends Sendable {
    public void execute(TetrisClient tetrisClient);
    public String getMessageKey();
}
