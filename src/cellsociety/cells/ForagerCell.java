package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell class that represents the grid cells for the ant simulation.
 *
 * @author Livia Seibert
 */
public class ForagerCell extends Cell {

  private double foodPheromones = 0.0;
  private double homePheromones = 0.0;

  public final static int NEST = 0;
  public final static int FOOD_SOURCE = 1;
  public final static int EMPTY = 2;
  public final static int OBSTACLE = 3;

  private List<InsectCell> ants;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state initial state of cell
   * @param row   row of cell
   * @param col   col of cell
   */
  public ForagerCell(int state, int row, int col, Neighbors neighborDirections) {
    super(state, row, col, neighborDirections.directions());
    ants = new ArrayList<>();
  }

  /**
   * Location of food sources, nests, and obstacles don't change.
   */
  @Override
  public void determineNextState() {
    setNextState(getState());
  }

  /**
   * Determines whether ant should find food or return to its nest.
   */
  public void determineNextAction() {
    ants.forEach(ant -> {
      if (ant.hasFoodItem()) {
        ant.returnToNest();
      } else {
        ant.findFoodSource();
      }
    });
  }

  /**
   * Allows access to pheromone levels so ant can determine where to move.
   *
   * @param type food or home pheromones
   * @return pheromone level of desired type
   */
  public double getPheromones(String type) {
    if (type.equals("Food")) {
      return foodPheromones;
    } else {
      return homePheromones;
    }
  }

  /**
   * Allows ant to modify the pheromone levels of a cell.
   *
   * @param type       food or home pheromones
   * @param pheromones new pheromone level
   */
  public void setPheromones(String type, double pheromones) {
    if (type.equals("Food")) {
      foodPheromones = pheromones;
    } else {
      homePheromones = pheromones;
    }
  }

  /**
   * Move an ant to a new cell.
   *
   * @param ant InsectCell object to be added to ants list
   */
  public void addAnt(InsectCell ant) {
    if (getState() == NEST) {
      ant.dropFoodItem();
    } else if (getState() == FOOD_SOURCE) {
      ant.findFoodSource();
    }
    ants.add(ant);
  }

  /**
   * Remove an ant from the current cell.
   *
   * @param ant InsectCell object to be removed from the cell
   */
  public void removeAnt(InsectCell ant) {
    ants.remove(ant);
  }

  /**
   * Allows controller to access the list of InsectCells a ForagerCell has
   *
   * @return ants list of InsectCell objects
   */
  public List<InsectCell> getAnts() {
    return ants;
  }
}
