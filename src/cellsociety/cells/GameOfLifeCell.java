package cellsociety.cells;

public class GameOfLifeCell extends Cell {

  private final int ALIVE = 1;
  private final int DEAD = 0;

  public GameOfLifeCell(int type, int row, int col) {
    super(type, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
  }

  public void determineNextState() {
    int livingNeighbors = countLiveNeighbors();
    if (state == ALIVE && (livingNeighbors < 2 || livingNeighbors >= 4)) {
      nextState = DEAD;
    } else if (state == ALIVE || (state == DEAD && livingNeighbors == 3)) {
      nextState = ALIVE;
    } else if (state == DEAD) {
      nextState = DEAD;
    }
  }

  private int countLiveNeighbors() {
    int livingNeighbors = 0;
    for (Cell neighbor: neighbors) {
      if (neighbor.getState() == ALIVE) {
        livingNeighbors++;
      }
    }
    return livingNeighbors;
  }
}
