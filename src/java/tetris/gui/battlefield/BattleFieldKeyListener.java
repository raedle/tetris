package tetris.gui.battlefield;

import tetris.gui.battlefield.BattleField;

import java.awt.event.*;

/**
 * <p>Headline: GUI.BattleFieldKeyListener</p>
 * <p>Description: This class implements the tetris battle field key listener.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class BattleFieldKeyListener extends KeyAdapter {

  //tetris battle field
  protected BattleField battleField = null;

  /**
   * Initialize the battle field key listener with tetris battle field.
   *
   * @param bf BattleField Tetris battle field
   */
  public BattleFieldKeyListener(BattleField bf) {
    this.battleField = bf;
  }

  /**
   * Implements the key pressed action.
   *
   * @param evt KeyEvent Pressed key
   */
  public void keyPressed(KeyEvent evt) {

    //code of the pressed key
    int keyCode = evt.getKeyCode();

    if (this.battleField.firstFigure != null) {
      switch (keyCode) {
        case KeyEvent.VK_RIGHT: {
          if (this.battleField.checkShiftRight(this.battleField.firstFigure)) {
            this.battleField.firstFigure.moveRight();
          }
          break;
        }
        case KeyEvent.VK_LEFT: {
          if (this.battleField.ckeckShiftLeft(this.battleField.firstFigure)) {
            this.battleField.firstFigure.moveLeft();
          }
          break;
        }
        case KeyEvent.VK_DOWN: {
          if (this.battleField.ckeckShiftDown(this.battleField.firstFigure)) {
            this.battleField.firstFigure.moveDown();
          }
          break;
        }
        case KeyEvent.VK_Y: {
          if (this.battleField.checkRotateLeft(this.battleField.firstFigure)) {
            this.battleField.firstFigure.rotateLeft();
          }
          break;
        }
        case KeyEvent.VK_X: {
          if (this.battleField.checkRotate(this.battleField.firstFigure)) {
            this.battleField.firstFigure.rotateRight();
          }
          break;
        }
      }
    }
    else {
      System.out.println("BattleFieldKeyListener: There is no figure to move!");
    }
  }
}
