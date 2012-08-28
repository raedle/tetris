package tetris.gui.battlefield;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Headline: GUI.BattleFieldRect</p>
 * <p>Description: This class implements the tetris battle field background.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class BattleFieldRect extends JComponent {

  /**
   * standard constructor
   */
  public BattleFieldRect() {

  }

  /**
   * Paint the tetris battle field backgound.
   *
   * @param g Graphics Tetris battle field background
   */
  public void paintComponent(Graphics g) {
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, 440, 40);
    g.fillRect(0, 20, 19, 441);
    g.fillRect(19, 440, 440, 20);
    g.fillRect(260, 40, 20, 420);
  }
}
