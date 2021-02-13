package cellsociety.cells;

/**
 * Cell class for Wa-Tor simulation cells.
 *
 * @author Livia Seibert
 */
public abstract class WatorCell extends Cell {

  protected final int SPAWN = 0;
  protected final int MOVE = 1;
  protected final int DEAD = 2;

  /**
   * Constructor for Wa-Tor simulation cells, uses super constructor. Checks if parameters breedTime
   * (time before prey can produce offspring) and offspringEnergy (energy predator needs before it
   * can breed) are specified in the XML file, otherwise it sets them to a default value of 5.0.
   *
   * @param cellState initial state of cell
   * @param row       row location of cell
   * @param col       col location of cell
   */
  public WatorCell(int cellState, int row, int col) {
    super(cellState, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
  }
}
