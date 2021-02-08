package cellsociety.cells;

import cellsociety.grid.Type;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Cell class for fire simulation cells.
 *
 * @author Livia Seibert
 */
public class FireCell extends Cell {

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;

  private int type;
  private Map<Type, String> typesToGraphics;
  private List<Cell> neighbors;

  public FireCell(int type) {
    this.neighbors = new ArrayList<Cell>();
    this.type = type;
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
