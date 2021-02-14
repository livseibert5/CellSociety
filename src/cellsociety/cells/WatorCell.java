package cellsociety.cells;

/**
 * Cell class for Wa-Tor simulation cells.
 *
 * @author Livia Seibert
 */
public abstract class WatorCell extends Cell {

  public static final int SPAWN = 0;
  public static final int MOVE = 1;
  public static final int DEAD = 2;

  public static final int PREDATOR = 0;
  public static final int PREY = 1;

  protected int nextAction;

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

  /**
   * Determine whether the animal should spawn, move, or die.
   */
  public abstract void determineAction();

  /**
   * Allow controller to access whether the animal should spawn, move, or die.
   *
   * @return action animal should do on update
   */
  public int getNextAction() {
    return nextAction;
  }
}
