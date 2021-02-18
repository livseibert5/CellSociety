package cellsociety.cells;

public class SugarCell extends Cell {

  private double sugar;

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
  }

  @Override
  public void determineNextState() {
    sugar += 1;
  }
}
