package cellsociety.controller;

/**
 * Class that controls the gameoflife game, Uses the grid class and extends basic controller class
 * Called from game loop to update gameoflife
 *
 * @author billyluqiu
 */
public class GameOfLifeController extends Controller {

  /**
   * default constructor calls super constructor
   */
  public GameOfLifeController() {
    super();
  }

  /**
   * game of life simulation never ends
   * @return false at all times
   */
  @Override
  public boolean simulationEnded() {
    return false;
  }

}
