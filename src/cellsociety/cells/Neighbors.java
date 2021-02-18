package cellsociety.cells;

public enum Neighbors {
  SQUARE_MOORE(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}}),
  SQUARE_NEUMANN(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}),
  TRIANGLE_MOORE_UP(
      new int[][]{{-1, 0}, {-1, 1}, {-1, -1}, {0, -1}, {0, -2}, {0, 1}, {0, 2}, {1, 0}, {1, -1},
          {1, -2}, {1, 1}, {1, 2}}),
  TRIANGLE_MOORE_DOWN(
      new int[][]{{-1, 0}, {-1, -1}, {-1, -2}, {-1, 1}, {-1, 2}, {0, -1}, {0, -2}, {0, 1}, {0, 2},
          {1, 0}, {1, -1}, {1, 1}}),
  TRIANGLE_NEUMANN_UP(new int[][]{{0, -1}, {0, 1}, {1, 0}}),
  TRIANGLE_NEUMANN_DOWN(new int[][]{{-1, 0}, {0, -1}, {0, 1}});

  private final int[][] directions;

  Neighbors(int[][] directions) {
    this.directions = directions;
  }

  int[][] directions() {
    return directions;
  }
}
