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
  public PercolationCell(int state, int row, int col) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
  }

  /**
   * Cells that are BLOCKED or PERCOLATED remain BLOCKED or PERCOLATED, OPEN cells with neighbors
   * that are PERCOLATED become PERCOLATED.
   */
  @Override
  public void determineNextState() {
    if (state == BLOCKED) {
      nextState = BLOCKED;
    } else if (state == PERCOLATED) {
      nextState = PERCOLATED;
    } else if (state == OPEN) {
      for (Cell neighbor : neighbors) {
        if (neighbor.getState() == PERCOLATED) {
          nextState = PERCOLATED;
          return;
        }
      }
      nextState = OPEN;
    }
  }
}
