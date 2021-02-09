package cellsociety.cells;

/**
 * Cell class for segregation simulation cells.
 *
 * @author Livia Seibert
 */
public class SegregationCell extends Cell {

  private final int TYPEX = 0;
  private final int TYPEO = 1;
  private final int EMPTY = 2;
  private final int MOVE = 3;
  private final double satisfied;

  private boolean isSatisfied = false;

  /**
   * Constructor for simulation cells, uses super constructor and sets the satisfied
   * parameter.
   *
   * @param state initial state of cell
   * @param row row location of cell
   * @param col column location of cell
   * @param satisfied % of like neighbors needed to be satisfied
   */
  public SegregationCell(int state, int row, int col, double satisfied) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
    this.satisfied = satisfied;
  }

  /**
   * Determines if the cell is satisfied based on the number of like neighbors.
   */
  public void determineNextState() {
    double percentLikeNeighbors = (double) countLikeNeighbors() / neighbors.size();
    isSatisfied = percentLikeNeighbors >= satisfied;
    if (isSatisfied) {
      nextState = state;
    } else {
      nextState = MOVE;
    }
  }

  private int countLikeNeighbors() {
    int likeNeighbors = 0;
    for (Cell neighbor: neighbors) {
      if (neighbor.getState() == state) {
        likeNeighbors++;
      }
    }
    return likeNeighbors;
  }
}
