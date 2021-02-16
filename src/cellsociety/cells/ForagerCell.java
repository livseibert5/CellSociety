package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell class that represents forager objects in the ant simulation.
 *
 * @author Livia Seibert
 */
public class ForagerCell extends Cell {

  private boolean hasFoodItem;
  private int nextAction;
  private int[] nextLocation;
  private Orientation orientation;

  private double foodPheromones = 0.0;
  private double homePheromones = 0.0;

  public static final int EMPTY = 0;
  public static final int FORAGER = 1;

  public static final int DROP_FOOD_PHEROMONES = 0;
  public static final int DROP_HOME_PHEROMONES = 1;

  private List<Cell> forwardNeighbors;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state initial state of cell
   * @param row   row of cell
   * @param col   col of cell
   */
  public ForagerCell(int state, int row, int col) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
    state = FORAGER;
    hasFoodItem = false;
    orientation = Orientation.NORTH;
    forwardNeighbors = new ArrayList<>();
  }

  /**
   * Foragers always remain foragers.
   */
  @Override
  public void determineNextState() {
    nextState = state;
  }

  /**
   * Determines whether forager should find food or return to its nest.
   */
  public void determineNextAction() {
    setForwardNeighbors();
    if (hasFoodItem) {
      returnToNest();
    } else {
      findFoodSource();
    }
  }

  /**
   * Directs forager towards food source.
   */
  private void findFoodSource() {
    Cell maxPheromones = checkForwardAndNeighbors("Food");
    if (maxPheromones != null) {
      nextAction = DROP_HOME_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  /**
   * Directs forager towards nest.
   */
  private void returnToNest() {
    Cell maxPheromones = checkForwardAndNeighbors("Home");
    if (maxPheromones != null) {
      nextAction = DROP_FOOD_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  /**
   * Sets forager's forward neighbors depending on its current orientation.
   */
  private void setForwardNeighbors() {
    for (Cell cell : neighbors) {
      int[] location = cell.getLocation();
      int[][] directions = orientation.directions();
      for (int i = 0; i < directions.length; i++) {
        if (location[0] == this.row + directions[i][0]
            && location[1] == this.col + directions[i][1]) {
          forwardNeighbors.add(cell);
        }
      }
    }
  }

  /**
   * Determines next location of cell by finding the cell with the most amount of pheromones of the
   * given type.
   *
   * @param type desired type of pheromones
   * @return cell with the most of the given pheromone type
   */
  private Cell checkForwardAndNeighbors(String type) {
    determineOrientation(getMaxPheromones(neighbors, type));
    setForwardNeighbors();
    Cell maxPheromones = getMaxPheromones(forwardNeighbors, type);
    if (maxPheromones == null) {
      maxPheromones = getMaxPheromones(neighbors, type);
    }
    return maxPheromones;
  }

  /**
   * Finds the cell with most amount of pheromones of the given type.
   *
   * @param neighbors list of neighbors to check for pheromones
   * @param type      desired type of pheromones
   * @return cell with the most of the given pheromone type
   */
  private Cell getMaxPheromones(List<Cell> neighbors, String type) {
    double maxPheromones = 0;
    Cell pheromoneCell = null;
    for (Cell cell : neighbors) {
        if (((ForagerCell) cell).getPheromones(type) > maxPheromones) {
          maxPheromones = ((ForagerCell) cell).getPheromones(type);
          pheromoneCell = cell;
        }
    }
    return pheromoneCell;
  }

  /**
   * Determine the next orientation of the cell based on the location of the cell it should be
   * facing.
   *
   * @param cell cell that ant should be oriented towards
   */
  private void determineOrientation(Cell cell) {
    if (cell != null) {
      if (isFacing(cell, Orientation.NORTH)) {
        orientation = Orientation.NORTH;
      } else if (isFacing(cell, Orientation.SOUTH)) {
        orientation = Orientation.SOUTH;
      } else if (isFacing(cell, Orientation.EAST)) {
        orientation = Orientation.EAST;
      } else if (isFacing(cell, Orientation.WEST)) {
        orientation = Orientation.WEST;
      }
    }
  }

  private boolean isFacing(Cell cell, Orientation orientation) {
    int[] location = cell.getLocation();
    int[][] directions = orientation.directions();
    return checkCoordinates(location, directions);
  }

  private boolean checkCoordinates(int[] location, int[][] directions) {
    for (int i = 0; i < directions.length; i++) {
      if (location[0] == this.row + directions[i][0]
          && location[1] == this.col + directions[i][1]) {
        return true;
      }
    }
    return false;
  }

  public double getPheromones(String type) {
    if (type.equals("Food")) {
      return foodPheromones;
    } else {
      return homePheromones;
    }
  }

  public int getNextAction() {
    return nextAction;
  }

  public int[] getNextLocation() {
    return nextLocation;
  }
}
