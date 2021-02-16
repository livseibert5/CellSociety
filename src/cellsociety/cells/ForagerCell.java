package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

public class ForagerCell extends Cell {

  private boolean hasFoodItem;
  private int nextAction;
  private int[] nextLocation;
  private Orientation orientation;

  public static final int FORAGER = 1;
  public static final int FIND_FOOD = 0;
  public static final int RETURN_TO_NEST = 1;
  public static final int DROP_PHEROMONES = 2;

  private List<Cell> forwardNeighbors;
  private int[][] forwardDirections;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public ForagerCell(int state, int row, int col, int[][] neighborDirections) {
    super(state, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
    state = FORAGER;
    hasFoodItem = false;
    orientation = Orientation.NORTH;
    forwardNeighbors = new ArrayList<>();
  }

  /**
   *
   */
  @Override
  public void determineNextState() {
    nextState = state;
  }

  public void determineNextAction() {
    setForwardNeighbors();
    if (hasFoodItem) {
      nextAction = RETURN_TO_NEST;
      returnToNest();
    } else {
      nextAction = FIND_FOOD;
      findFoodSource();
    }
  }

  private void findFoodSource() {

  }

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

  private void returnToNest() {
    determineOrientation(getMaxPheromones(neighbors, "Home"));
    setForwardNeighbors();
    Cell maxPheromones = getMaxPheromones(forwardNeighbors, "Home");
    if (maxPheromones == null) {
      maxPheromones = getMaxPheromones(neighbors, "Home");
    }
    if (maxPheromones != null) {
      nextAction = DROP_PHEROMONES;
      determineOrientation(maxPheromones);
      nextLocation = maxPheromones.getLocation();
    }
  }

  private Cell getMaxPheromones(List<Cell> neighbors, String type) {
    double maxPheromones = 0;
    Cell pheromoneCell = null;
    for (Cell cell : neighbors) {
      if (type.equals("Food") && cell instanceof FoodPheromoneCell) {
        if (((PheromoneCell) cell).getPheromones() > maxPheromones) {
          maxPheromones = ((PheromoneCell) cell).getPheromones();
          pheromoneCell = cell;
        }
      } else if (type.equals("Home") && cell instanceof HomePheromoneCell) {
        if (((PheromoneCell) cell).getPheromones() > maxPheromones) {
          maxPheromones = ((PheromoneCell) cell).getPheromones();
          pheromoneCell = cell;
        }
      }
    }
    return pheromoneCell;
  }

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
}
