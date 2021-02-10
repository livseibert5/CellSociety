package cellsociety.cells;

import java.util.Map;

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
   * Constructor for simulation cells, uses super constructor and sets the satisfied parameter. .30
   * is the default value for satisfied if no value specified.
   *
   * @param state  initial state of cell
   * @param row    row location of cell
   * @param col    column location of cell
   * @param params map of params needed for simulation
   */
  public SegregationCell(int state, int row, int col, Map<String, Double> params) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
    if (params.containsKey("satisfied")) {
      this.satisfied = params.get("satisfied");
    } else {
      this.satisfied = .30;
    }
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
    for (Cell neighbor : neighbors) {
      if (neighbor.getState() == state) {
        likeNeighbors++;
      }
    }
    return likeNeighbors;
  }
}
