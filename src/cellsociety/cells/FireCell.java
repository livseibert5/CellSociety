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
  private int row;
  private int col;
  private Map<Type, String> typesToGraphics;
  private List<Cell> neighbors;

  public FireCell(int type, int row, int col) {
    this.type = type;
    this.row = row;
    this.col = col;
  }

  private void findNeighbors() {
    int[][] neighborDirections = {{0, 1}, {1,0}, {-1, 0}, {0, -1}};
    for (int i = 0; i < neighborDirections.length; i++) {

    }
  }

  /**
   * Accesses the adjacent cells needed to update the state of the cell.
   *
   * @return list of neighboring cells
   */
  public List<Cell> getNeighbors() {
    return neighbors;
  }

  public int[] getCoordinates() {
    return new int[]{row, col};
  }
}
