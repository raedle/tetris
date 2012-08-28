package tetris.gui.figure;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Headline: GUI.Block</p>
 * <p>Description: This class creates a Tetris block.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class Block extends JComponent {

  //width of the tetris block
  protected int blockWidth = 20;

  //height of the teris block
  protected int blockHeight = 20;

  //x-coordinate of the tetris block
  protected int blockX = 0;

  //y-coordinate of the tetris block
  protected int blockY = 0;

  //color of the tetris block
  protected Color color = Color.RED;

  /**
   * standard constructor
   */
  public Block() {

  }

  /**
   * Initialization of the tetris block with width, height and color.
   *
   * @param width int Width of the tetris block
   * @param height int Heigth of the tetris block
   * @param x x-coordinate of the tetris block
   * @param y y-coordinate of the tetris block
   * @param color Color Color of the tetris block
   */
  public Block(int width, int height, int x, int y, Color color) {
    this.blockWidth = width;
    this.blockHeight = height;
    this.blockX = x;
    this.blockY = y;
    this.setLocation(x, y);
    this.setSize(width, height);
    this.color = color;
  }

  /**
   * Draw a filled rectangle as tetris block and a draw rectangle as
   *
   * @param g Graphics Graphics object -> tetris block
   */
  public void paintComponent(Graphics g) {
    g.setColor(color);
    g.fill3DRect(0, 0, 19, 19, true);
    g.setColor(Color.WHITE);
    g.drawRect(0, 0, 20, 20);
  }

  /**
   * Set the x-coordinate of the tetris block.
   *
   * @param x int x-coordinate of the tetris block
   */
  public void setBlockX(int x) {
    this.blockX = x;
  }

  /**
   * Set the y-coordinate of the tetris block.
   *
   * @param y int y-coordinate of the tetris block
   */
  public void setBlockY(int y) {
    this.blockY = y;
  }

  /**
   * Returns the width of the tetris block.
   *
   * @return int Width of tetris block
   */
  public int getBlockWidth() {
    return this.blockWidth;
  }

  /**
   * Returns the height of the tetris block.
   *
   * @return int Height of tetris block
   */
  public int getBlockHeight() {
    return this.blockHeight;
  }

  /**
   * Returns the actual x-coordinate of the tetris block.
   *
   * @return int actual x-coordinate of the tetris block
   */
  public int getBlockX() {
    return this.blockX;
  }

  /**
   * Returns the actual y-coordinate of the tetris block.
   *
   * @return int actual y-coordinate of the tetris block
   */
  public int getBlockY() {
    return this.blockY;
  }

  /**
   * Returns color of the tetris block.
   *
   * @return Color Tetris block color
   */
  public Color getBlockColor() {
    return this.color;
  }

  /**
   * Returns tetris block size.
   *
   * @return int Tetris block size
   */
  public int getBlockSize() {
    return ((blockWidth + blockHeight) / 2);
  }

  /**
   * Set tetris block to parameter x value.
   *
   * @param x int x-coordinate of tetris block location
   */
  public void setX(int x) {
    this.setLocation(x, this.getY());
  }

  /**
   * Set tetris block to parameter y value.
   *
   * @param y int y-coordinate of tetris block location
   */
  public void setY(int y) {
    this.setLocation(this.getX(), y);
  }
}
