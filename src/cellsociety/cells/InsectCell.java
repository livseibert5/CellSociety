package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents ants, a list of InsectCell objects is present in every ForagerCell.
 *
 * @author Livia Seibert
 */
public class InsectCell extends Cell {

  private Orientation orientation;
  private int nextAction;
  private int[] nextLocation;
  private boolean hasFoodItem;
  private ForagerCell foragerCell;

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
  public InsectCell(int state, int row, int col) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}, {0, 0}});
    hasFoodItem = false;
    orientation = Orientation.NORTH;
    forwardNeighbors = new ArrayList<>();
  }

  /**
   * Ant cells always remain ants.
   */
  @Override
  public void determineNextState() {
    nextState = state;
  }

  /**
   * Directs forager towards food source.
   */
  public void findFoodSource() {
    getForagerCell();
    Cell maxPheromones = checkForwardAndNeighbors("Food");
    if (foragerCell.getState() == ForagerCell.NEST) {
      determineOrientation(maxPheromones);
    }
    maxPheromones = checkForwardAndNeighbors("Food");
    if (maxPheromones != null) {
      nextAction = DROP_HOME_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  /**
   * Directs forager towards nest.
   */
  public void returnToNest() {
    getForagerCell();
    Cell maxPheromones = checkForwardAndNeighbors("Home");
    if (foragerCell.getState() == ForagerCell.NEST) {
      determineOrientation(maxPheromones);
    }
    maxPheromones = checkForwardAndNeighbors("Home");
    if (maxPheromones != null) {
      nextAction = DROP_FOOD_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  /**
   * Determine which cell the ant lives on so that it's state can be used.
   */
  private void getForagerCell() {
    neighbors.forEach(cell -> {
      if (cell.getLocation()[0] == this.row && cell.getLocation()[1] == this.col) {
        foragerCell = (ForagerCell) cell;
      }
    });
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
            && location[1] == this.col + directions[i][1] && !isCurrentCell(location)) {
          forwardNeighbors.add(cell);
        }
      }
    }
  }

  private boolean isCurrentCell(int[] location) {
    return this.row == location[0] && this.col == location[1];
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
      if (((ForagerCell) cell).getPheromones(type) > maxPheromones && !isCurrentCell(
          cell.getLocation()) && foragerCell.getState() != ForagerCell.OBSTACLE) {
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
          && location[1] == this.col + directions[i][1] && !isCurrentCell(location)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Lets ForagerCell determine whether or not the ant has food.
   *
   * @return hasFoodItem true if ant is carrying food
   */
  public boolean hasFoodItem() {
    return hasFoodItem;
  }

  /**
   * Lets ForagerCell set hasFoodItem to false if the ant drops the food into the nest.
   */
  public void dropFoodItem() {
    hasFoodItem = false;
  }

  /**
   * Lets ForagerCell set hasFoodItem to true if the ant is at the food source.
   */
  public void getFoodItem() {
    hasFoodItem = true;
  }

  /**
   * Lets Controller determine how the pheromone levels of ForagerCells should be incremented.
   *
   * @return nextAction next action for ant to perform
   */
  public int getNextAction() {
    return nextAction;
  }

  /**
   * Lets Controller determine where to move the ant to.
   *
   * @return nextLocation next cell for ant to move to
   */
  public int[] getNextLocation() {
    return nextLocation;
  }
}
