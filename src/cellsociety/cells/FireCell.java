package cellsociety.cells;

import java.util.Map;

/**
 * Cell class for fire simulation cells.
 *
 * @author Livia Seibert
 */
public class FireCell extends Cell {

  public static final int EMPTY = 0;
  public static final int TREE = 1;
  public static final int BURNING = 2;
  private final double probCatch;

  public static final double DEFAULT_PROB_CATCH = .30;

  /**
   * Constructor for fire cell, uses super constructor and initializes probCatch. If no probCatch
   * value specified in XML, it is set to .30 on default.
   *
   * @param state  initial state of cell
   * @param row    row of cell
   * @param col    col of cell
   * @param params map of params needed for simulation
   */
  public FireCell(int state, int row, int col, Map<String, Double> params, Neighbors neighborDirections) {
    super(state, row, col, neighborDirections.directions());
    this.probCatch = params.getOrDefault("probCatch", DEFAULT_PROB_CATCH);
  }

  /**
   * EMPTY and BURNING cells always have a next state of EMPTY, TREE's next state is determined
   * based on the probCatch parameter if it has a burning neighbor.
   */
  @Override
  public void determineNextState() {
    if (getState() == TREE) {
      boolean neighborIsBurning = false;
      for (int i = 0; i < neighbors.size(); i++) {
        if (neighbors.get(i).getState() == BURNING) {
          neighborIsBurning = true;
          break;
        }
      }
      setNextState(Math.random() < probCatch && neighborIsBurning ? BURNING : TREE);
    } else if (getState() == EMPTY || getState() == BURNING) {
      setNextState(EMPTY);
    }
  }
}
