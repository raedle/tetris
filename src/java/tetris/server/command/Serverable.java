package tetris.server.command;

import tetris.server.TetrisServer;
import tetris.command.Sendable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>Serverable</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 19:10:11
 *
 * @author Roman R&auml;dle
 * @version $Id: Serverable.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public interface Serverable extends Sendable {
    public void execute(TetrisServer tetrisServer);
}
