package tetris.server.logging;

/**
 * TODO: document me!!!
 * <p/>
 * <code>Logable</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 22:41:51
 *
 * @author Roman R&auml;dle
 * @version $Id: Logable.java,v 1.1.1.1 2006/03/23 23:35:55 raedler Exp $
 */
public interface Logable {
    public void info(String message);
    public void error(String message);
}
