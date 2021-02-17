package cellsociety.cells;

public enum Neighbors {
  SQUARE_MOORE(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}}),
  SQUARE_NEUMANN(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}),
  TRIANGLE_MOORE_UP(),
  TRIANGLE_MOORE_DOWN(),
  TRIANGLE_NEUMANN_UP(),
  TRIANGLE_NEUMANN_DOWN();

  private final int directions;

  Neighbors(int[][] directions) {
    this.directions = directions;
  }
}
