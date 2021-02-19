package cellsociety.cells;

import java.util.Map;

/**
 * Cell class for segregation simulation cells.
 *
 * @author Livia Seibert
 */
public class SegregationCell extends Cell {

  public static final int EMPTY = 2;
  public static final int MOVE = 3;
  private final double satisfied;
  private final double DEFAULT_SATISFIED = .30;

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
  public SegregationCell(int state, int row, int col, Map<String, Double> params, Neighbors neighborDirections) {
    super(state, row, col,
        neighborDirections.directions());
    this.satisfied = params.getOrDefault("satisfied", DEFAULT_SATISFIED);
  }

  /**
   * Determines if the cell is satisfied based on the number of like neighbors.
   */
  @Override
  public void determineNextState() {
    double percentLikeNeighbors = (double) countLikeNeighbors() / neighbors.size();
    isSatisfied = percentLikeNeighbors >= satisfied;
    setNextState(isSatisfied ? getState() : MOVE);
  }

  private int countLikeNeighbors() {
    int likeNeighbors = 0;
    for (Cell neighbor : neighbors) {
      if (neighbor.getState() == this.getState()) {
        likeNeighbors++;
      }
    }
    return likeNeighbors;
  }

  /**
   * Allows access to whether or not the cell should be shuffled.
   *
   * @return true if cell should stay where it is, otherwise false
   */
  public boolean getIsSatisfied() {
    return isSatisfied;
  }

  /**
   * Allows controller to access the game parameter.
   *
   * @return percentage of like neighbors needed to be satisfied
   */
  public double getSatisfied() {
    return satisfied;
  }
}
