package cellsociety.cells;

import java.util.List;

/**
 * Class for cell that represents agents that live on SugarCell objects.
 *
 * @author Livia Seibert
 */
public class AgentCell extends Cell {

  private double sugar;
  private double vision;
  private double sugarMetabolism;
  private int nextAction;
  private List<int[][]> visionLocations;
  private int[] nextLocation;

  public static final int SUGAR_CELL = 1;
  public static final int DEAD = 2;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public AgentCell(int state, int row, int col, int[][] neighborDirections) {
    super(state, row, col, neighborDirections);
    state = SUGAR_CELL;
    getNeighborLocations();
    setNeighborDirections();
  }

  /**
   * Next state stays the same unless it is updated in incrementSugar.
   */
  @Override
  public void determineNextState() {
    nextState = state;
  }

  /**
   * Determines location for agent to move to.
   */
  public void determineNextLocation() {
    SugarCell maxSugar = findMaxSugar();
    nextLocation = maxSugar.getLocation();
  }

  /**
   * Gets all of the locations in the four directions up to vision blocks away.
   */
  private void getNeighborLocations() {
    visionLocations.add(neighborDirections);
    if (vision > 1) {
      for (int i = (int) vision; i > 1; i--) {
        int[][] newDirections = new int[neighborDirections.length][neighborDirections[0].length];
        for (int j = 0; j < neighborDirections.length; j++) {
          newDirections[j][0] = neighborDirections[j][0] * (int) vision;
          newDirections[j][1] = neighborDirections[j][1] * (int) vision;
        }
        visionLocations.add(newDirections);
      }
    }
  }

  /**
   * Sets the locations up to vision blocks away as the new neighborDirections array so that all of
   * these locations can be accessed.
   */
  private void setNeighborDirections() {
    int[][] newDirections = new int[visionLocations.size() * visionLocations.get(0).length][2];
    int newDirectionIndex = 0;
    for (int i = 0; i < visionLocations.size(); i++) {
      for (int j = 0; j < visionLocations.get(i).length; j++) {
        newDirections[newDirectionIndex][0] = visionLocations.get(i)[j][0];
        newDirections[newDirectionIndex][1] = visionLocations.get(i)[j][1];
        newDirectionIndex++;
      }
    }
    neighborDirections = newDirections;
  }

  /**
   * Finds the neighboring cell with the maxmimum amount of sugar.
   *
   * @return neighbor with most sugar
   */
  private SugarCell findMaxSugar() {
    SugarCell maxSugarCell = null;
    double maxSugarVal = 0;
    for (Cell neighbor : neighbors) {
      if (!((SugarCell) neighbor).getHasAgent()
          && ((SugarCell) neighbor).getSugar() > maxSugarVal) {
        maxSugarVal = ((SugarCell) neighbor).getSugar();
        maxSugarCell = (SugarCell) neighbor;
      }
    }
    return maxSugarCell;
  }

  /**
   * Allows controller to access next location of the agent so it can move the agent there.
   *
   * @return int array where 0 is next row of the cell and 0 is the next column
   */
  public int[] getNextLocation() {
    return nextLocation;
  }

  /**
   * Increments sugar amount when agent moves to a new cell.
   *
   * @param sugar amount to increment sugar by.
   */
  public void incrementSugar(double sugar) {
    this.sugar += sugar;
    this.sugar -= sugarMetabolism;
    if (this.sugar <= 0) {
      nextState = DEAD;
    } else {
      nextState = SUGAR_CELL;
    }
  }
}
