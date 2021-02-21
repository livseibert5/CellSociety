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
  private int row;
  private int col;

  public static final int DROP_FOOD_PHEROMONES = 0;
  public static final int DROP_HOME_PHEROMONES = 1;
  private final double K = .001;
  private final double N = 10.0;
  private final int crowdedCellSize = 10;

  private final List<Cell> forwardNeighbors;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state initial state of cell
   * @param row   row of cell
   * @param col   col of cell
   */
  public InsectCell(int state, int row, int col) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
    hasFoodItem = false;
    orientation = Orientation.NORTH;
    forwardNeighbors = new ArrayList<>();
  }

  /**
   * Ant cells always remain ants.
   */
  @Override
  public void determineNextState() {
    setNextState(getState());
  }

  /**
   * Directs forager towards food source.
   */
  public void findFoodSource(ForagerCell cell) {
    updateInstanceVariables(cell);
    if (foragerCell.getState() == ForagerCell.NEST) {
      Cell maxPheromones = getMaxPheromones(getNeighbors(), ForagerCell.FOOD);
      determineOrientation(maxPheromones);
    }
    setForwardNeighbors();
    Cell location = selectLocation(forwardNeighbors);
    if (location == null) {
      location = selectLocation(getNeighbors());
    }
    if (location != null) {
      nextAction = DROP_HOME_PHEROMONES;
      determineOrientation(location);
      nextLocation = location.getLocation();
    } else {
      nextLocation = forwardNeighbors.get(0).getLocation();
    }
  }

  /**
   * Directs forager towards nest.
   */
  public void returnToNest(ForagerCell cell) {
    updateInstanceVariables(cell);
    Cell maxPheromones = getMaxPheromones(getNeighbors(), ForagerCell.HOME);
    if (foragerCell.getState() == ForagerCell.FOOD_SOURCE) {
      determineOrientation(maxPheromones);
    }
    setForwardNeighbors();
    maxPheromones = getMaxPheromones(forwardNeighbors, ForagerCell.HOME);
    if (maxPheromones == null) {
      maxPheromones = getMaxPheromones(getNeighbors(), ForagerCell.HOME);
    }
    if (maxPheromones != null) {
      nextAction = DROP_FOOD_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    } else {
      nextLocation = forwardNeighbors.get(0).getLocation();
    }
  }

  /**
   * Determines the best next location for forager headed towards a food source.
   *
   * @param cells potential next locations
   * @return cell to move to next
   */
  private Cell selectLocation(List<Cell> cells) {
    List<Cell> idealLocation = new ArrayList<>();
    for (Cell cell : cells) {
      if (isNotCrowded(cell) && isNotObstacle(cell)) {
        idealLocation.add(cell);
      }
    }
    if (idealLocation.isEmpty()) {
      return null;
    } else {
      return chooseCellWithProbability(idealLocation);
    }
  }

  /**
   * Uses the probability function specified in the foraging ants research paper to choose a new
   * location from the list of possible locations.
   *
   * @param idealLocation potential next locations
   * @return next location chosen from probability function
   */
  private Cell chooseCellWithProbability(List<Cell> idealLocation) {
    double random = Math.random();
    double cumulativeProb = 0.0;
    for (Cell cell : idealLocation) {
      cumulativeProb += getWeight((ForagerCell) cell);
      if (random <= cumulativeProb) {
        return cell;
      }
    }
    return idealLocation.get(0);
  }

  /**
   * Sets forager's forward neighbors depending on its current orientation.
   */
  private void setForwardNeighbors() {
    for (Cell cell : getNeighbors()) {
      int[] location = cell.getLocation();
      int[][] directions = orientation.directions();
      for (int[] direction : directions) {
        if (locationIsAtDirection(location, direction)) {
          forwardNeighbors.add(cell);
        }
      }
    }
  }

  /**
   * Finds the cell with most amount of pheromones of the given type from a given set of cells.
   *
   * @param neighbors list of neighbors to check for pheromones
   * @param type      desired type of pheromones
   * @return cell with the most of the given pheromone type
   */
  public Cell getMaxPheromones(List<Cell> neighbors, String type) {
    double maxPheromones = 0;
    Cell pheromoneCell = null;
    for (Cell cell : neighbors) {
      if (((ForagerCell) cell).getPheromones(type) > maxPheromones && isNotObstacle(foragerCell)) {
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

  /**
   * Determines where the cell the current cell should be facing is in relation to the current cell
   * so that the new orientation can be determines.
   *
   * @param cell        cell to check location of relative to the current cell
   * @param orientation potential cell orientation
   * @return true if the passed cell is at the given orientation relative to the current cell
   */
  private boolean isFacing(Cell cell, Orientation orientation) {
    int[] location = cell.getLocation();
    int[][] directions = orientation.directions();
    return checkCoordinates(location, directions);
  }

  /**
   * Checks if the cell's location is at any of the locations specified by the orientation.
   *
   * @param location   coordinates to check direction of relative to the current cell
   * @param directions locations specified by the orientation
   * @return true if passed coordinates are at given orientation relatie to the current cell
   */
  private boolean checkCoordinates(int[] location, int[][] directions) {
    for (int[] direction : directions) {
      if (locationIsAtDirection(location, direction)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Probability function for determining next location from a set of locations.
   *
   * @param cell cell to determine weight of
   * @return cell's calculated weight
   */
  private double getWeight(ForagerCell cell) {
    return Math.pow((K + cell.getPheromones(ForagerCell.FOOD)), N);
  }

  /**
   * Checks if location passed is at the specified direction from the row and col of the current
   * cell.
   *
   * @param location  location to check coordinates of
   * @param direction coordinates of cell distance from current cell
   * @return true if location is at the place specified by the direction
   */
  private boolean locationIsAtDirection(int[] location, int[] direction) {
    return location[0] == row + direction[0] && location[1] == col + direction[1];
  }

  /**
   * Determines if cell is too full of ants to move to.
   *
   * @param cell cell to count ants on
   * @return true if cell is not crowded
   */
  private boolean isNotCrowded(Cell cell) {
    return ((ForagerCell) cell).getAnts().size() < crowdedCellSize;
  }

  /**
   * Determines if cell is an obstacle.
   *
   * @param cell cell to check state of
   * @return true if cell is not an obstacle
   */
  private boolean isNotObstacle(Cell cell) {
    return cell.getState() != ForagerCell.OBSTACLE;
  }

  /**
   * Determines if location passed is equivalent to the location of the current cell.
   *
   * @param location coordinates to check for equality to current cell location
   * @return true if location passed is not where the current cell is
   */
  private boolean isNotCurrentCell(int[] location) {
    return !(row == location[0] && col == location[1]);
  }

  /**
   * Updates instance variables so they are accurate when referenced by other helper methods.
   */
  private void updateInstanceVariables(ForagerCell cell) {
    this.foragerCell = cell;
    this.row = this.getLocation()[0];
    this.col = this.getLocation()[1];
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
