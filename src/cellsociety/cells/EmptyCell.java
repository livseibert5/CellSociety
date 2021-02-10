package cellsociety.cells;

public class EmptyCell extends Cell {

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   */
  public EmptyCell(int state, int row, int col) {
    super(state, row, col, new int[0][0]);
  }

  @Override
  public void determineNextState() {
    nextState = state;
  }
}
