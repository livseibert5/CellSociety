package cellsociety.cells;

/**
 * Class represents cells that make up the grid for the SugarScape simulation.
 *
 * @author Livia Seibert
 */
public class SugarCell extends Cell {

  private double sugar;
  private double maxSugarCapacity;
  private double sugarGrowBackRate;
  private double sugarGrowBackInterval;
  private double sugarGrowBackIntervalCounter;
  private AgentCell agent;
  private boolean hasAgent;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public SugarCell(int state, int row, int col, int[][] neighborDirections) {
    super(state, row, col, neighborDirections);
    hasAgent = false;
    sugarGrowBackIntervalCounter = 0;
  }

  /**
   * Handles sugar grow back over time intervals.
   */
  @Override
  public void determineNextState() {
    sugarGrowBackIntervalCounter++;
    if (sugarGrowBackIntervalCounter == sugarGrowBackInterval) {
      sugarGrowBackIntervalCounter = 0;
      if (sugar + sugarGrowBackRate > maxSugarCapacity) {
        sugar = maxSugarCapacity;
      } else {
        sugar += sugarGrowBackRate;
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
