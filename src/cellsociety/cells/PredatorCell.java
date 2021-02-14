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



  private double startingEnergy;
  private double offspringEnergy;
  private double energyCounter;

  /**
   * Constructor for the Predator cell, uses the WatorCell constructor and also sets the values for
   * the predator's starting energy and offspring energy based on XML input.
   *
   * @param cellState intial state of cell
   * @param row       row position of cell
   * @param col       column position of cell
   * @param params    list of parameters for the simulation
   */
  public PredatorCell(int cellState, int row, int col, Map<String, Double> params) {
    super(cellState, row, col);
    state = PREDATOR;
    if (params.containsKey("startingEnergy")) {
      this.startingEnergy = params.get("startingEnergy");
    } else {
      this.startingEnergy = 10.0;
    }
    if (params.containsKey("offspringEnergy")) {
      this.offspringEnergy = params.get("offspringEnergy");
    } else {
      this.offspringEnergy = 5.0;
    }
    energyCounter = startingEnergy;
  }

  /**
   * A predator cell always remains a predator.
   */
  @Override
  public void determineNextState() {
    nextState = PREDATOR;
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
    }
    else    {
      nextAction = MOVE;
    }
    energyCounter--;
  }

  /**
   * Since collisions between sharks and fish are handled in the controller, the controller needs to
   * be able to increment a shark's energy when it eats a fish.
   */
  public void incrementEnergy() {
    energyCounter++;
  }

  public double getStartingEnergy() {
    return startingEnergy;
  }

  public double getOffspringEnergy() {
    return offspringEnergy;
  }
}
