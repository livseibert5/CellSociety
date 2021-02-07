package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell class for fire simulation cells.
 *
 * @author Livia Seibert
 */
public class FireCell extends Cell {

  private List<Cell> neighbors;

  public FireCell() {
    neighbors = new ArrayList<Cell>();
  }

  /**
   * Accesses the adjacent cells needed to update the state of the cell.
   *
   * @return list of neighboring cells
   */
  public List<Cell> getNeighbors() {
    return neighbors;
  }
}
