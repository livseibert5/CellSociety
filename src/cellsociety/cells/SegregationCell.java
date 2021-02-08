package cellsociety.cells;

public class SegregationCell extends Cell {

  private final int TYPEX = 0;
  private final int TYPEO = 1;

  public SegregationCell(int type, int row, int col) {
    super(type, row, col,
        new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}});
  }
  public void determineNextState() {

  }
}
