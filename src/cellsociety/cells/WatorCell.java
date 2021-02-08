package cellsociety.cells;

/**
 * Cell class for Wa-Tor simulation cells.
 *
 * @author Livia Seibert
 */
public class WatorCell extends Cell {

  private int EMPTY = 0;
  private int PREDATOR = 1;
  private int PREY = 2;

  /**
   * Constructor for Wa-Tor simulation cells, uses super constructor.
   *
   * @param state initial state of Wa-Tor cell
   * @param row row location of cell
   * @param col col location of cell
   */
  public WatorCell(int state, int row, int col) {
    super(state, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
  }

  public void determineNextState() {

  }
}