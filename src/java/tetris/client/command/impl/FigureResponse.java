package tetris.client.command.impl;

import tetris.client.command.Clientable;
import tetris.client.TetrisClient;

import java.util.List;

/**
 * TODO: document me!!!
 * <p/>
 * <code>FigureResponse</code>.
 * <p/>
 * User: rro
 * Date: 23.03.2006
 * Time: 19:48:55
 *
 * @author Roman R&auml;dle
 * @version $Id: FigureResponse.java,v 1.1.1.1 2006/03/23 23:35:56 raedler Exp $
 */
public class FigureResponse implements Clientable {

    private List figures;

    public FigureResponse(List figures) {
        this.figures = figures;
    }

    public void execute(TetrisClient tetrisClient) {
        tetrisClient.getBattleField().addNewFigureList(figures);
    }

    public String getMessageKey() {
        return "tetris.client.command.impl.FigureResponse";
    }
}
