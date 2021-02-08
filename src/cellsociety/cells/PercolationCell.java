package cellsociety.cells;

public class PercolationCell extends Cell {

  private final int BLOCKED = 0;
  private final int OPEN = 1;
  private final int PERCOLATED = 2;

  public PercolationCell(int type, int row, int col) {
    super(type, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
  }

  public void determineNextState() {
    if (state == BLOCKED) {
      nextState = BLOCKED;
    } else if (state == PERCOLATED) {
      nextState = PERCOLATED;
    } else if (state == OPEN) {
      for (Cell neighbor: neighbors) {
        if (neighbor.getState() == PERCOLATED) {
          nextState = PERCOLATED;
          return;
        }
      }
      nextState = OPEN;
    }
  }
}
