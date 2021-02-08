package cellsociety.grid;

import cellsociety.cells.Cell;

/**
 * Class that holds cell in proper formation for simulation.
 *
 * @author Livia Seibert
 */
public class Grid {

  Cell[][] grid;

  public Grid(int width, int height) {
    grid = new Cell[width][height];
  }

  /**
   * Allows access to cell objects at specific locations.
   *
   * @param i row of cell
   * @param j col of cell
   * @return Cell object located at indicated position in grid
   */
  public Cell getCellAtLocation(int i, int j) {
    if (isInBounds(i, j)) {
      return grid[i][j];
    }
    return null;
  }

  /**
   * Puts cell object in simulation grid at row i and col j.
   *
   * @param i row of cell
   * @param j col of cell
   * @param cell Cell object to put at given location
   */
  public void setCellAtLocation(int i, int j, Cell cell) {
    if (isInBounds(i, j)) {
      grid[i][j] = cell;
    }
  }

  private boolean isInBounds(int i, int j) {
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
  }

}
