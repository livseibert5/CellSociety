package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

public class SugarCell extends Cell {

  private double sugar;
  private double maxSugarCapacity;
  private double sugarGrowBackRate;
  private double sugarGrowBackInterval;
  private double sugarGrowBackIntervalCounter;
  private AgentCell agent;
  private boolean hasAgent;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public SugarCell(int state, int row, int col, int[][] neighborDirections) {
    super(state, row, col, neighborDirections);
    hasAgent = false;
    sugarGrowBackIntervalCounter = 0;
  }

  @Override
  public void determineNextState() {
    sugarGrowBackIntervalCounter++;
    if (sugarGrowBackIntervalCounter == sugarGrowBackInterval) {
      sugarGrowBackIntervalCounter = 0;
      if (sugar + sugarGrowBackRate > maxSugarCapacity) {
        sugar = maxSugarCapacity
      } else {
        sugar += sugarGrowBackRate;
      }
    }
  }

  public boolean getHasAgent() {
    return hasAgent;
  }

  public double getSugar() {
    return sugar;
  }
}
