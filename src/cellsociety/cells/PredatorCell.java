package cellsociety.cells;

import java.util.Map;

public class PredatorCell extends WatorCell {

  private double startingEnergy;
  private double offspringEnergy;
  private double energyCounter;

  public PredatorCell(int state, int row, int col, Map<String, Double> params) {
    super(state, row, col);
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

  @Override
  public void determineNextState() {
    if (energyCounter == offspringEnergy) {
      nextState = SPAWN;
    } else if (energyCounter == 0) {
      nextState = DEAD;
    }
    energyCounter--;
  }

  public void incrementEnergy() {
    energyCounter++;
  }
}
