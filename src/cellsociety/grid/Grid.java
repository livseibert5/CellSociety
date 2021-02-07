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
    if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length) {
      return grid[i][j];
    }
    return null;
  }

}
