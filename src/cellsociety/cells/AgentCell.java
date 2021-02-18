package cellsociety.cells;

import java.util.ArrayList;
import java.util.List;

public class AgentCell extends Cell {

  private double sugar;
  private double vision;
  private double sugarMetabolism;
  private int nextAction;
  private List<int[][]> visionLocations;
  private int[] nextLocation;

  public static final int MOVE = 0;
  public static final int SUGAR_CELL = 1;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public AgentCell(int state, int row, int col, int[][] neighborDirections) {
    super(state, row, col, neighborDirections);
    state = SUGAR_CELL;
    getNeighborLocations();
    setNeighborDirections();
  }

  @Override
  public void determineNextState() {
    nextState = state;
  }

  public void determineNextAction() {
    SugarCell maxSugar = findMaxSugar();
    nextLocation = maxSugar.getLocation();
    nextAction = MOVE;
  }

  private void getNeighborLocations() {
    visionLocations.add(neighborDirections);
    if (vision > 1) {
      for (int i = (int) vision; i > 1; i--) {
        int[][] newDirections = new int[neighborDirections.length][neighborDirections[0].length];
        for (int j = 0; j < neighborDirections.length; j++) {
          newDirections[j][0] = neighborDirections[j][0] * (int) vision;
          newDirections[j][1] = neighborDirections[j][1] * (int) vision;
        }
        visionLocations.add(newDirections);
      }
    }
  }

  private void setNeighborDirections() {
    int[][] newDirections = new int[visionLocations.size() * visionLocations.get(0).length][2];
    int newDirectionIndex = 0;
    for (int i = 0; i < visionLocations.size(); i++) {
      for (int j = 0; j < visionLocations.get(i).length; j++) {
        newDirections[newDirectionIndex][0] = visionLocations.get(i)[j][0];
        newDirections[newDirectionIndex][1] = visionLocations.get(i)[j][1];
        newDirectionIndex++;
      }
    }
    neighborDirections = newDirections;
  }

  private SugarCell findMaxSugar() {
    SugarCell maxSugarCell = null;
    double maxSugarVal = 0;
    for (Cell neighbor: neighbors) {
      if (!((SugarCell) neighbor).getHasAgent() && ((SugarCell) neighbor).getSugar() > maxSugarVal) {
        maxSugarVal = ((SugarCell) neighbor).getSugar();
        maxSugarCell = (SugarCell) neighbor;
      }
    }
    return maxSugarCell;
  }
}
