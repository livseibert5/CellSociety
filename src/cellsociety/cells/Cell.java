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
  private int row;
  private int col;
  private List<Cell> neighbors;
  private int[][] neighborDirections;

  public Cell(int type, int row, int col, int[][] neighborDirections) {
    this.type = type;
    this.row = row;
    this.col = col;
    this.neighborDirections = neighborDirections;
  }

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
  public List<Cell> getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(List<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  public int[][] getNeighborDirections() {
    return neighborDirections;
  }
}
