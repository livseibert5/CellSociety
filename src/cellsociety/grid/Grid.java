package cellsociety.grid;

import cellsociety.cells.Cell;

public class Grid {

  Cell[][] grid;

  public Grid(int width, int height) {
    grid = new Cell[width][height];
  }

  public Cell getCellAtLocation(int i, int j) {
    if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length) {
      return grid[i][j];
    }
    return null;
  }

}
