package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell class that represents forager objects in the ant simulation.
 *
 * @author Livia Seibert
 */
public class ForagerCell extends Cell {

  private double foodPheromones = 0.0;
  private double homePheromones = 0.0;

  public final static int NEST = 0;
  public final static int FOOD_SOURCE = 1;
  public final static int EMPTY = 2;

  private List<InsectCell> ants;

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
    ants = new ArrayList<>();
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
    ants.forEach(ant -> {
      if (ant.hasFoodItem()) {
        ant.returnToNest();
      } else {
        ant.findFoodSource();
      }
    });
  }

  public double getPheromones(String type) {
    if (type.equals("Food")) {
      return foodPheromones;
    } else {
      return homePheromones;
    }
  }

  public void setPheromones(String type, double pheromones) {
    if (type.equals("Food")) {
      foodPheromones = pheromones;
    } else {
      homePheromones = pheromones;
    }
  }

  public List<InsectCell> getAnts() {
    return ants;
  }
}
