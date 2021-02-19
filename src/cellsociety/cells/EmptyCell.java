package cellsociety.cells;

/**
 * Class that represents an empty cell that does nothing. The purpose of an empty cell is to handle
 * ambiguous XML data entry by putting in a block that does nothing so that no errors are thrown by
 * the grid and controller.
 *
 * @author Livia Seibert
 */
public class EmptyCell extends Cell {

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param cellState initial state of cell
   * @param row       row of cell
   * @param col       col of cell
   */
  public EmptyCell(int cellState, int row, int col) {
    super(cellState, row, col, new int[0][0]);
  }

  /**
   * State never changes because this cell is just a placeholder so that the grid doesn't throw
   * errors.
   */
  @Override
  public void determineNextState() {
    setNextState(getState());
  }
}
