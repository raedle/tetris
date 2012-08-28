package tetris.server.command.impl;

import tetris.server.command.Serverable;
import tetris.server.TetrisServer;
import tetris.server.logging.Logable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>ServerLog</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 20:29:47
 *
 * @author Roman R&auml;dle
 * @version $Id: ServerLog.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class ServerLog implements Serverable {

    public enum Type {INFO, ERROR}

    private Type messageType;

    private String message;

    public ServerLog(Type messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public void execute(TetrisServer tetrisServer) {

        Logable log = tetrisServer.getLog();

        switch (messageType) {
            case INFO:
                log.info(message);
                break;
            case ERROR:
                log.error(message);
                break;
        }
    }
}
