package cellsociety.cells;

import java.util.Map;

public class PreyCell extends WatorCell {

  private double breedTime;
  private double breedTimeCounter;

  public PreyCell(int state, int row, int col, Map<String, Double> params) {
    super(state, row, col);
    breedTimeCounter = 0;
    if (params.containsKey("breedTime")) {
      this.breedTime = params.get("breedTime");
    } else {
      this.breedTime = 5.0;
    }
  }

  @Override
  public void determineNextState() {
    breedTimeCounter++;
    if (breedTimeCounter == breedTime) {
      nextState = SPAWN;
      breedTimeCounter = 0;
    }
  }
}
