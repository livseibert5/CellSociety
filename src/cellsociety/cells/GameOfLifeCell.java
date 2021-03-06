package cellsociety.cells;

/**
 * Cell class for Game of Life Cells.
 *
 * @author Livia Seibert
 */
public class GameOfLifeCell extends Cell {

  public static final int ALIVE = 1;
  public static final int DEAD = 0;

  /**
   * Constructor for game of life cell, uses super constructor.
   *
   * @param state initial state of cell
   * @param row   row location of cell
   * @param col   col location of cell
   */
  public GameOfLifeCell(int state, int row, int col, Neighbors neighborDirections) {
    super(state, row, col, neighborDirections.directions());
  }

  /**
   * Calculates next state of cell based on the number of living neighbors the cell has.
   */
  @Override
  public void determineNextState() {
    int livingNeighbors = countLiveNeighbors();
    if (getState() == ALIVE && (livingNeighbors < 2 || livingNeighbors >= 4)) {
      setNextState(DEAD);
    } else if (getState() == ALIVE || (getState() == DEAD && livingNeighbors == 3)) {
      setNextState(ALIVE);
    } else if (getState() == DEAD) {
      setNextState(DEAD);
    }
  }

  private int countLiveNeighbors() {
    int livingNeighbors = 0;
    for (Cell neighbor : getNeighbors()) {
      if (neighbor.getState() == ALIVE) {
        livingNeighbors++;
      }
    }
    return livingNeighbors;
  }
}
