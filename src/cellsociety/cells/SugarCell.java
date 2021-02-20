package cellsociety.cells;

import java.util.Map;

/**
 * Class represents cells that make up the grid for the SugarScape simulation.
 *
 * @author Livia Seibert
 */
public class SugarCell extends Cell {

  private double sugar;
  private double maxSugarCapacity;
  private double sugarGrowBackIntervalCounter;
  private AgentCell agent;
  private boolean hasAgent;

  private final double SUGAR_GROWBACK_RATE = 1;
  private final double SUGAR_GROWBACK_INTERVAL = 1;
  private final double DEFAULT_MAX_SUGAR = 8.0;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public SugarCell(int state, int row, int col, Map<String, Double> params, Neighbors neighborDirections) {
    super(state, row, col, neighborDirections.directions());
    this.maxSugarCapacity = params.getOrDefault("maxSugarCapacity", DEFAULT_MAX_SUGAR);
    hasAgent = false;
    sugarGrowBackIntervalCounter = 0;
    sugar = 0;
  }

  /**
   * Handles sugar grow back over time intervals.
   */
  @Override
  public void determineNextState() {
    setNextState(getState());
    sugarGrowBackIntervalCounter++;
    if (sugarGrowBackIntervalCounter == SUGAR_GROWBACK_INTERVAL) {
      sugarGrowBackIntervalCounter = 0;
      if (sugar + SUGAR_GROWBACK_RATE > maxSugarCapacity) {
        sugar = maxSugarCapacity;
      } else {
        sugar += SUGAR_GROWBACK_RATE;
      }
    }
  }

  /**
   * Returns whether the cell has an agent on it or not.
   *
   * @return true if cell has an agent
   */
  public boolean getHasAgent() {
    return hasAgent;
  }

  public AgentCell getAgent() {
    return this.agent;
  }
  /**
   * Allows controller to remove agent when moving it to a new cell.
   */
  public void removeAgent() {
    hasAgent = false;
  }

  /**
   * Allows controller to set the agent of a cell so that it can move an agent to a new cell.
   *
   * @param agent new agent inhabitant
   */
  public void setAgent(AgentCell agent) {
    this.agent = agent;
    hasAgent = true;
  }

  /**
   * Allows controller to access sugar level on cell so that agent can acquire this sugar.
   *
   * @return current sugar amount on cell
   */
  public double getSugar() {
    return sugar;
  }
}
