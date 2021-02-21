package cellsociety.cells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents ants, a list of InsectCell objects is present in every ForagerCell.
 *
 * @author Livia Seibert
 */
public class InsectCell extends Cell {

  private Orientation orientation;
  private int[] nextLocation;
  private boolean hasFoodItem;
  private ForagerCell foragerCell;
  private int row;
  private int col;

  private List<Cell> forwardNeighbors;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state initial state of cell
   * @param row   row of cell
   * @param col   col of cell
   */
  public InsectCell(int state, int row, int col) {
    super(state, row, col, Neighbors.SQUARE_MOORE.directions());
    hasFoodItem = false;
    orientation = chooseStartingOrientation();
    forwardNeighbors = new ArrayList<>();
  }

  private Orientation chooseStartingOrientation() {
    List<Orientation> list = new ArrayList<>(
        Arrays.asList(Orientation.NORTH, Orientation.SOUTH, Orientation.EAST, Orientation.WEST, Orientation.NORTHEAST, Orientation.NORTHWEST, Orientation.SOUTHEAST, Orientation.SOUTHWEST));
    Collections.shuffle(list);
    return list.get(0);
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
    Cell location = getMaxPheromones(forwardNeighbors, ForagerCell.FOOD);
    if (location == null) {
      location = getMaxPheromones(getNeighbors(), ForagerCell.FOOD);
    }
    if (location != null) {
      determineOrientation(location);
      nextLocation = location.getLocation();
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
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  /**
   * Sets forager's forward neighbors depending on its current orientation.
   */
  private void setForwardNeighbors() {
    forwardNeighbors = new ArrayList<>();
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
      List<Orientation> list = new ArrayList<>(
          Arrays.asList(Orientation.NORTH, Orientation.SOUTH, Orientation.EAST, Orientation.WEST, Orientation.NORTHEAST, Orientation.NORTHWEST, Orientation.SOUTHEAST, Orientation.SOUTHWEST));
      Collections.shuffle(list);
      for (Orientation o: list) {
        if (isFacing(cell, o)) {
          orientation = o;
          break;
        }
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
   * Determines if cell is an obstacle.
   *
   * @param cell cell to check state of
   * @return true if cell is not an obstacle
   */
  private boolean isNotObstacle(Cell cell) {
    return cell.getState() != ForagerCell.OBSTACLE;
  }

  /**
   * Updates instance variables so they are accurate when referenced by other helper methods.
   */
  private void updateInstanceVariables(ForagerCell cell) {
    this.foragerCell = cell;
    setNeighbors(foragerCell.getNeighbors());
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
   * Lets Controller determine where to move the ant to.
   *
   * @return nextLocation next cell for ant to move to
   */
  public int[] getNextLocation() {
    return nextLocation;
  }
}
