package cellsociety.cells;

import cellsociety.grid.Type;
import java.util.Map;

/**
 * Cell class for fire simulation cells.
 *
 * @author Livia Seibert
 */
public class FireCell extends Cell {

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;
  private final double probCatch;

  private Map<Type, String> typesToGraphics;

  public FireCell(int type, int row, int col, double probCatch) {
    super(type, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
    this.probCatch = probCatch;
  }

  /**
   * EMPTY and BURNING cells always have a next state of EMPTY, TREE's
   * next state is determined based on the probCatch parameter if it has
   * a burning neighbor.
   */
  public void determineNextState() {
    if (state == TREE) {
      boolean neighborIsBurning = false;
      for (int i = 0; i < neighbors.size(); i++) {
        if (neighbors.get(i).getState() == BURNING) {
          neighborIsBurning = true;
        }
      }
      if (Math.random() <= probCatch && neighborIsBurning) {
        nextState = BURNING;
      } else {
        nextState = TREE;
      }
    } else if (state == EMPTY || state == BURNING) {
      nextState = EMPTY;
    }
  }
}
