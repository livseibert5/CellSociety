package cellsociety.cells;

import java.util.Map;

public class PreyCell extends WatorCell {

  private double breedTime;
  private double breedTimeCounter;

  /**
   * Constructor for Wa-Tor simulation cells, uses super constructor. Checks if parameters breedTime
   * (time before prey can produce offspring) and offspringEnergy (energy predator needs before it can
   * breed) are specified in the XML file, otherwise it sets them to a default value of 5.0.
   *
   * @param state  initial state of Wa-Tor cell
   * @param row    row location of cell
   * @param col    col location of cell
   * @param params
   */
  public PreyCell(int state, int row, int col,
      Map<String, Double> params) {
    super(state, row, col);
    if (params.containsKey("breedTime")) {
      this.breedTime = params.get("breedTime");
    } else {
      this.breedTime = 5.0;
    }
  }

  @Override
  public void determineNextState() {

  }
}
