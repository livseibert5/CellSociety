package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cell class for Wa-Tor simulation cells.
 *
 * @author Livia Seibert
 */
public abstract class WatorCell extends Cell {

  /**
   * Constructor for Wa-Tor simulation cells, uses super constructor. Checks if parameters breedTime
   * (time before prey can produce offspring) and offspringEnergy (energy predator needs before it
   * can breed) are specified in the XML file, otherwise it sets them to a default value of 5.0.
   *
   * @param state initial state of Wa-Tor cell
   * @param row   row location of cell
   * @param col   col location of cell
   */
  public WatorCell(int state, int row, int col) {
    super(state, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
  }
}
