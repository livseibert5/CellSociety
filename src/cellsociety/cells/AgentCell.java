package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for cell that represents agents that live on SugarCell objects.
 *
 * @author Livia Seibert
 */
public class AgentCell extends Cell {

  private double sugar;
  private final double vision;
  private final double sugarMetabolism;
  private List<int[][]> visionLocations;
  private int[] nextLocation;

  public static final int ALIVE = 0;
  public static final int SUGAR_CELL = 1;
  public static final int DEAD = 2;

  private final double DEFAULT_SUGAR = 10.0;
  private final double DEFAULT_VISION = 3.0;
  private final double DEFAULT_METABOLISM = 2.0;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public AgentCell(int state, int row, int col, Map<String, Double> params,
      int[][] neighborDirections) {
    super(state, row, col, neighborDirections);
    sugar = params.getOrDefault("sugar", DEFAULT_SUGAR);
    vision = params.getOrDefault("vision", DEFAULT_VISION);
    sugarMetabolism = params.getOrDefault("metabolism", DEFAULT_METABOLISM);
    this.visionLocations = new ArrayList<>();
    state = SUGAR_CELL;
    getNeighborLocations();
    setNeighborDirections();
  }

  /**
   * Determines location for agent to move to.
   */
  public void determineNextLocation(SugarCell sugarCell) {
    setNeighborDirections(sugarCell.getNeighborDirections());
    visionLocations = new ArrayList<>();
    getNeighborLocations();
    setNeighborDirections();
    SugarCell maxSugar = findMaxSugar();
    if (maxSugar != null) {
      nextLocation = maxSugar.getLocation();
    } else {
      nextLocation = getLocation();
    }
  }

  /**
   * Gets all of the locations in the four directions up to vision blocks away.
   */
  private void getNeighborLocations() {
    visionLocations.add(getNeighborDirections());
    if (vision > 1) {
      for (int i = (int) vision; i > 1; i--) {
        int[][] newDirections = new int[getNeighborDirections().length][getNeighborDirections()[0].length];
        for (int j = 0; j < getNeighborDirections().length; j++) {
          newDirections[j][0] = getNeighborDirections()[j][0] * (int) vision;
          newDirections[j][1] = getNeighborDirections()[j][1] * (int) vision;
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
    setNeighborDirections(newDirections);
  }

  /**
   * Finds the neighboring cell with the maxmimum amount of sugar.
   *
   * @return neighbor with most sugar
   */
  private SugarCell findMaxSugar() {
    SugarCell maxSugarCell = null;
    double maxSugarVal = 0;
    for (Cell neighbor : getNeighbors()) {
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
      setNextState(DEAD);
    } else {
      setNextState(SUGAR_CELL);
    }
  }
}
