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

  private final double breedTime;
  private double breedTimeCounter;

  public static final double DEFAULT_BREEDTIME = 5.0;

  /**
   * Constuctor for the Prey cell, uses the WatorCell constructor and also sets the values for the
   * prey's time before breeding based on XML input.
   *
   * @param cellState initial state of cell
   * @param row       row position of cell
   * @param col       column position of cell
   * @param params    list of parameters for the simulation
   */
  public PreyCell(int cellState, int row, int col, Map<String, Double> params,
      Neighbors neighborDirections) {
    super(cellState, row, col, neighborDirections);
    setState(PREY);
    breedTimeCounter = 0;
    this.breedTime = params.getOrDefault("breedTime", DEFAULT_BREEDTIME);
  }

  /**
   * A prey cell always remains a prey cell.
   */
  @Override
  public void determineNextState() {
    setNextState(PREY);
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
    } else {
      nextAction = MOVE;
    }
  }

  /**
   * Allows access to time until prey can spawn a child.
   *
   * @return time until breeding
   */
  public double getBreedTime() {
    return breedTime;
  }
}
