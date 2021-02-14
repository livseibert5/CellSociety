package cellsociety.grid;

import cellsociety.cells.Cell;
import java.util.Map;

/**
 * Class for Grid objects that wrap around at the edges.
 *
 * @author Livia Seibert
 */
public class ToroidalGrid extends Grid {

  /**
   * Constructor for ToroidalGrid objects, creates a new grid based on the specifications passed in
   * from the XML file.
   *
   * @param width    number of columns of cell
   * @param height   number of rows of cell
   * @param fileName .txt file with initial layout of grid
   * @param type     type of simulation to run
   * @param params   map of parameters needed for simulation
   */
  public ToroidalGrid(int width, int height, String fileName, Type type,
      Map<String, Double> params) {
    super(width, height, fileName, type, params);
  }

  /**
   * ToroidalGrid objects wrap around instead of having strict boundaries. This method overrides the
   * parent getCellAtLocation method so that neighbors can be found by wrapping around the grid.
   *
   * @param i row of cell
   * @param j col of cell
   * @return cell object at the given location
   */
  @Override
  public Cell getCellAtLocation(int i, int j) {
    if (isInBounds(i, j)) {
      return grid[i][j];
    } else if (wrapsRight(i, j)) {
      return grid[i][0];
    } else if (wrapsLeft(i, j)) {
      return grid[i][grid[i].length - 1];
    } else if (wrapsTop(i, j)) {
      return grid[grid.length - 1][j];
    } else if (wrapsBottom(i, j)) {
      return grid[0][j];
    } else {
      return null;
    }
  }

  @Override
  public void setCellAtLocation(int i, int j, Cell cell) {
    if (isInBounds(i, j)) {
       grid[i][j] = cell ;
    } else if (wrapsRight(i, j)) {
       grid[i][0] = cell;
    } else if (wrapsLeft(i, j)) {
       grid[i][grid[i].length - 1] = cell;
    } else if (wrapsTop(i, j)) {
       grid[grid.length - 1][j] = cell;
    } else if (wrapsBottom(i, j)) {
       grid[0][j] = cell;
    }
  }
  private boolean wrapsRight(int i, int j) {
    return j == grid[0].length && i >= 0 && i < grid.length;
  }

  private boolean wrapsLeft(int i, int j) {
    return j == -1 && i >= 0 && i < grid.length;
  }

  private boolean wrapsTop(int i, int j) {
    return i == grid.length && j >= 0 && j < grid[0].length;
  }

  private boolean wrapsBottom(int i, int j) {
    return i == -1 && j >= 0 && j < grid[0].length;
  }
}
