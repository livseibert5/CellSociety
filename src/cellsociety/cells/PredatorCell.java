package cellsociety.cells;

import java.util.Map;

/**
 * Predator cell for the Wator simulation. This class extends the Wator cell so that this class and
 * the Prey class can share the same states, however the functionality of this class is specific to
 * the predator in the simulation.
 *
 * @author Livia Seibert
 */
public class PredatorCell extends WatorCell {

  private final double startingEnergy;
  private final double offspringEnergy;
  private double energyCounter;

  public static final double DEFAULT_STARTING_ENERGY = 5.0;
  public static final double DEFAULT_OFFSPRING_ENERGY = 5.0;
  public static final double ENERGY_INCREMENT = 5.0;

  /**
   * Constructor for the Predator cell, uses the WatorCell constructor and also sets the values for
   * the predator's starting energy and offspring energy based on XML input.
   *
   * @param cellState intial state of cell
   * @param row       row position of cell
   * @param col       column position of cell
   * @param params    list of parameters for the simulation
   */
  public PredatorCell(int cellState, int row, int col, Map<String, Double> params,
      Neighbors neighborDirections) {
    super(cellState, row, col, neighborDirections);
    setState(PREDATOR);
    this.startingEnergy = params.getOrDefault("startingEnergy", DEFAULT_STARTING_ENERGY);
    this.offspringEnergy = params.getOrDefault("offspringEnergy", DEFAULT_OFFSPRING_ENERGY);
    energyCounter = startingEnergy;
  }

  /**
   * Determines whether the predator spawns or dies, decrements its energy each time the simulation
   * is updated.
   */
  @Override
  public void determineAction() {
    if (energyCounter == offspringEnergy) {
      nextAction = SPAWN;
    } else if (energyCounter == 0) {
      nextAction = DEAD;
    } else {
      nextAction = MOVE;
    }
  }

  /**
   * Since collisions between predators and prey are handled in the controller, the controller needs
   * to be able to increment a predator's energy when it eats prey.
   */
  public void incrementEnergy() {
    energyCounter += ENERGY_INCREMENT;
  }

  /**
   * Allows controller to decrement the predator's energy when it moves.
   */
  public void decrementEnergy() {
    energyCounter--;
  }

  /**
   * Allows access to predator's energy level when it is first spawned.
   *
   * @return predator's initial energy level
   */
  public double getStartingEnergy() {
    return startingEnergy;
  }

  /**
   * Allows access to energy level needed before shark can spawn.
   *
   * @return energy needed for predator to have children
   */
  public double getOffspringEnergy() {
    return offspringEnergy;
  }
}
