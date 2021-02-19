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
  private final double K = .001;
  private final double N = 10.0;

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
    setNextState(getState());
  }

  /**
   * Directs forager towards food source.
   */
  public void findFoodSource() {
    getForagerCell();
    if (foragerCell.getState() == ForagerCell.NEST) {
      Cell maxPheromones = getMaxPheromones(neighbors, "Food");
      determineOrientation(maxPheromones);
    }
    setForwardNeighbors();
    Cell location = selectLocation(forwardNeighbors);
    if (location == null) {
      location = selectLocation(neighbors);
    }
    if (location != null) {
      nextAction = DROP_HOME_PHEROMONES;
      determineOrientation(location);
      nextLocation = location.getLocation();
    }
  }

  /**
   * Directs forager towards nest.
   */
  public void returnToNest() {
    getForagerCell();
    Cell maxPheromones = getMaxPheromones(neighbors, "Home");
    if (foragerCell.getState() == ForagerCell.NEST) {
      determineOrientation(maxPheromones);
    }
    maxPheromones = getMaxPheromones(forwardNeighbors, "Home");
    if (maxPheromones == null) {
      maxPheromones = getMaxPheromones(neighbors, "Home");
    }
    if (maxPheromones != null) {
      nextAction = DROP_FOOD_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  private Cell selectLocation(List<Cell> cells) {
    List<Cell> idealLocation = new ArrayList<>();
    for (Cell cell: cells) {
      if (!isCurrentCell(cell.getLocation()) && ((ForagerCell) cell).getAnts().size() < 10 && cell.getState() != ForagerCell.OBSTACLE) {
        idealLocation.add(cell);
      }
    }
    if (idealLocation.size() == 0) {
      return null;
    } else {
      double random = Math.random();
      double cumulativeProb = 0.0;
      for (Cell cell: idealLocation) {
        cumulativeProb += getWeight((ForagerCell) cell);
        if (random <= cumulativeProb) {
          return cell;
        }
      }
    }
    return idealLocation.get(0);
  }

  private double getWeight(ForagerCell cell) {
    return Math.pow((K + cell.getPheromones("Food")), N);
  }

  /**
   * Determine which cell the ant lives on so that it's state can be used.
   */
  private void getForagerCell() {
    neighbors.forEach(cell -> {
      if (cell.getLocation()[0] == this.getLocation()[0] && cell.getLocation()[1] == this.getLocation()[1]) {
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
      for (int[] direction: directions) {
        if (location[0] == this.getLocation()[0] + direction[0]
            && location[1] == this.getLocation()[1] + direction[1] && !isCurrentCell(location)) {
          forwardNeighbors.add(cell);
        }
      }
    }
  }

  private boolean isCurrentCell(int[] location) {
    return this.getLocation()[0] == location[0] && this.getLocation()[1] == location[1];
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
    for (int[] direction: directions) {
      if (location[0] == this.getLocation()[0] + direction[0]
          && location[1] == this.getLocation()[1] + direction[1] && !isCurrentCell(location)) {
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
