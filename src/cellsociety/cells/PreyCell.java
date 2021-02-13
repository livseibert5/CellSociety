package cellsociety.cells;

import java.util.Map;

/**
 * Prey cell for the Wator simulation. This class extends the Wator cell so that this class and the
 * Predator class can share the same states, however the functionality of this class is specific to
 * the prey in the simulation.
 *
 * @author Livia Seibert
 */
public class PreyCell extends WatorCell {

  private double breedTime;
  private double breedTimeCounter;

  /**
   * Constuctor for the Prey cell, uses the WatorCell constructor and also sets the values for the
   * prey's time before breeding based on XML input.
   *
   * @param cellState initial state of cell
   * @param row       row position of cell
   * @param col       column position of cell
   * @param params    list of parameters for the simulation
   */
  public PreyCell(int cellState, int row, int col, Map<String, Double> params) {
    super(cellState, row, col);
    state = PREY;
    breedTimeCounter = 0;
    if (params.containsKey("breedTime")) {
      this.breedTime = params.get("breedTime");
    } else {
      this.breedTime = 5.0;
    }
  }

  /**
   * A prey cell always remains a prey cell.
   */
  @Override
  public void determineNextState() {
    nextState = PREY;
  }

  /**
   * Increments the prey's breed time counter every time the simulation is updated, detects if the
   * prey is ready to spawn.
   */
  @Override
  public void determineAction() {
    breedTimeCounter++;
    if (breedTimeCounter == breedTime) {
      nextAction = SPAWN;
      breedTimeCounter = 0;
    }
  }
}
