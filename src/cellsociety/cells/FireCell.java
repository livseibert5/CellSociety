package cellsociety.cells;

import cellsociety.grid.Type;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Cell class for fire simulation cells.
 *
 * @author Livia Seibert
 */
public class FireCell extends Cell {

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;

  private int[][] neighborDirections;
  private Map<Type, String> typesToGraphics;
  private List<Cell> neighbors;

  public FireCell(int type, int row, int col) {
    super(type, row, col, new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}});
  }
}
