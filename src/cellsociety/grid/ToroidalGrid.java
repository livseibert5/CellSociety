package cellsociety.grid;

import cellsociety.cells.Cell;
import cellsociety.cells.Neighbors;
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
      Map<String, Double> params, Neighbors neighborDirections) {
    super(width, height, fileName, type, params, neighborDirections);
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
      return getCellAtLocation(i, j);
    } else if (wrapsRight(i, j)) {
      return getCellAtLocation(i, 0);
    } else if (wrapsLeft(i, j)) {
      return getCellAtLocation(i, getSizeOfGrid()[1] - 1);
    } else if (wrapsTop(i, j)) {
      return getCellAtLocation(getSizeOfGrid()[0] - 1, j);
    } else if (wrapsBottom(i, j)) {
      return getCellAtLocation(0, j);
    } else {
      return null;
    }
  }

  /**
   * Since toroidal grids wrap around, instead of doing nothing when a specified location is out of
   * bounds, the toroidal grid needs to wrap to the other side and check the cell there. Overriding
   * setCellAtLocation allows setNeighbors to get a cell's neighbors that are wrapped across the
   * grid.
   *
   * @param i    row of cell
   * @param j    col of cell
   * @param cell Cell object to put at indicated location in  grid
   */
  @Override
  public void setCellAtLocation(int i, int j, Cell cell) {
    if (isInBounds(i, j)) {
      setCellAtLocation(i, j, cell);
    } else if (wrapsRight(i, j)) {
      setCellAtLocation(i, 0, cell);
    } else if (wrapsLeft(i, j)) {
      setCellAtLocation(i, getSizeOfGrid()[1] - 1, cell);
    } else if (wrapsTop(i, j)) {
      setCellAtLocation(getSizeOfGrid()[0] - 1, j, cell);
    } else if (wrapsBottom(i, j)) {
      setCellAtLocation(0, j, cell);
    }
  }

  @Override
  protected Grid copySelf() {
    return new ToroidalGrid(getSizeOfGrid()[1], getSizeOfGrid()[0], this.fileName, this.type,
        getParams(), this.neighborDirections);
  }

  private boolean wrapsRight(int i, int j) {
    return j == getSizeOfGrid()[1] && i >= 0 && i < getSizeOfGrid()[0];
  }

  private boolean wrapsLeft(int i, int j) {
    return j == -1 && i >= 0 && i < getSizeOfGrid()[0];
  }

  private boolean wrapsTop(int i, int j) {
    return i == getSizeOfGrid()[0] && j >= 0 && j < getSizeOfGrid()[1];
  }

  private boolean wrapsBottom(int i, int j) {
    return i == -1 && j >= 0 && j < getSizeOfGrid()[1];
  }
}
