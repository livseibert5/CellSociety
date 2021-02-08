package cellsociety.cells;

import java.util.List;
import cellsociety.grid.Type;

/**
 * Superclass that all cell types will inherit from.
 *
 * @author Livia Seibert
 */
public abstract class Cell {

  private int type;

  /**
   * Accesses simulation type of cell.
   *
   * @return type of cell in simulation
   */
  public int getType() {
    return type;
  }

  /**
   * Accesses cell's neighbors so its state can be updated.
   *
   * @return list of cell's neighboring cells
   */
  abstract public List<Cell> getNeighbors();

  public abstract int[] getCoordinates();
}
