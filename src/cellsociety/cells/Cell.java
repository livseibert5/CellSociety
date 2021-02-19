package cellsociety.cells;

import java.util.List;

/**
 * Superclass that all cell types will inherit from.
 *
 * @author Livia Seibert
 */
public abstract class Cell {

  protected int state;
  protected int nextState;
  protected int row;
  protected int col;
  protected List<Cell> neighbors;
  protected int[][] neighborDirections;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public Cell(int state, int row, int col, int[][] neighborDirections) {
    this.state = state;
    this.row = row;
    this.col = col;
    this.neighborDirections = neighborDirections;
  }

  /**
   * Calculate next state of cell depending on states of neighboring cells.
   */
  public abstract void determineNextState();

  /**
   * Accesses simulation type of cell.
   *
   * @return type of cell in simulation
   */
  public int getState() {
    return state;
  }

  /**
   * Sets a cell to its next state.
   */
  public void updateState() {
    state = nextState;
  }

  /**
   * Lets the grid determine a cell's neighbors and pass the resulting list back to the cell.
   *
   * @param neighbors list of adjacent cells
   */
  public void setNeighbors(List<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Each cell type has its own rules for which neighbors matter when determining the next state.
   * These rules are accessible here so that grid can find and return the correct neighbors.
   *
   * @return directions to cell's neighbors
   */
  public int[][] getNeighborDirections() {
    return neighborDirections;
  }

  /**
   * Allows access to the next state of the cell.
   *
   * @return new state of cell after next update
   */
  public int getNextState() {
    return nextState;
  }

  /**
   * Allows access to a list of the cell's neighbors so that the controller can properly handle
   * simulation updates.
   *
   * @return list of cell's neighbors
   */
  public List<Cell> getNeighbors() {
    return neighbors;
  }

  /**
   * Sets location of the cell.
   *
   * @param i row location of cell
   * @param j column location of cell
   */
  public void setDims(int i, int j) {
    this.row = i;
    this.col = j;
  }

  /**
   * Allows access to row and column position of cell.
   *
   * @return int array with row position at 0 and column position at 1
   */
  public int[] getLocation() {
    return new int[]{row, col};
  }

  protected void setNextState(int nextState) {
    this.nextState = nextState;
  }
}
