package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cell class for Wa-Tor simulation cells.
 *
 * @author Livia Seibert
 */
public class WatorCell extends Cell {

  private int EMPTY = 0;
  private int PREDATOR = 1;
  private int PREY = 2;

  private double breedTime;
  private double breedTimeCounter;
  private double energy;
  private double offspringEnergy;

  /**
   * Constructor for Wa-Tor simulation cells, uses super constructor. Checks if parameters breedTime
   * (time before prey can produce offspring) and offspringEnergy (energy predator needs before it
   * can breed) are specified in the XML file, otherwise it sets them to a default value of 5.0.
   *
   * @param state initial state of Wa-Tor cell
   * @param row   row location of cell
   * @param col   col location of cell
   */
  public WatorCell(int state, int row, int col, Map<String, Double> params) {
    super(state, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
    if (params.containsKey("breedTime")) {
      this.breedTime = params.get("breedTime");
    } else {
      this.breedTime = 5.0;
    }
    if (params.containsKey("offspringEnergy")) {
      this.offspringEnergy = params.get("offspringEnergy");
    } else {
      this.offspringEnergy = 5.0;
    }
  }

  public void determineNextState() {
    if (state == PREY) {
      List<Cell> freeSpaces = new ArrayList<>();
      for (Cell cell : neighbors) {
        if (cell.getState() == EMPTY) {
          freeSpaces.add(cell);
        }
      }
    } else if (state == PREDATOR) {

    }
  }
}
