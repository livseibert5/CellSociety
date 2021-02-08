package cellsociety.cells;

public class WatorCell extends Cell {

  private int EMPTY = 0;
  private int PREDATOR = 1;
  private int PREY = 2;

  public WatorCell(int type, int row, int col) {
    super(type, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
  }

  public void determineNextState() {

  }

}
