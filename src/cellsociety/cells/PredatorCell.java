package cellsociety.cells;

import java.util.Map;

public class PredatorCell extends WatorCell {

  private double energy;
  private double offspringEnergy;

  public PredatorCell(int state, int row, int col, Map<String, Double> params) {
    super(state, row, col);
    if (params.containsKey("offspringEnergy")) {
      this.offspringEnergy = params.get("offspringEnergy");
    } else {
      this.offspringEnergy = 5.0;
    }
  }

  @Override
  public void determineNextState() {

  }
}
