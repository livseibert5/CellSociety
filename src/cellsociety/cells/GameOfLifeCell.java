package cellsociety.cells;

public class GameOfLifeCell extends Cell {

  private final int ALIVE = 1;
  private final int DEAD = 0;

  public GameOfLifeCell(int type, int row, int col) {
    super(type, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
  }

  public void determineNextState() {

  }
}
