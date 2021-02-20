package cellsociety.cells;

/**
 * Cell class for percolation simulation cells.
 *
 * @author Livia Seibert
 */
public class PercolationCell extends Cell {

  public static final int BLOCKED = 0;
  public static final int OPEN = 1;
  public static final int PERCOLATED = 2;

  /**
   * Constructor for percolation cells, uses super constructor.
   *
   * @param state initial state of cell
   * @param row   row location of cell
   * @param col   column location of cell
   */
  public PercolationCell(int state, int row, int col, Neighbors neighborDirections) {
    super(state, row, col, neighborDirections.directions());
}

  /**
   * Cells that are BLOCKED or PERCOLATED remain BLOCKED or PERCOLATED, OPEN cells with neighbors
   * that are PERCOLATED become PERCOLATED.
   */
  @Override
  public void determineNextState() {
    if (getState() == BLOCKED) {
      setNextState(BLOCKED);
    } else if (getState() == PERCOLATED) {
      setNextState(PERCOLATED);
    } else if (getState() == OPEN) {
      for (Cell neighbor : getNeighbors()) {
        if (neighbor.getState() == PERCOLATED) {
          setNextState(PERCOLATED);
          return;
        }
      }
      setNextState(OPEN);
    }
  }
}
