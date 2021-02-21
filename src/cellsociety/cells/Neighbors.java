package cellsociety.cells;

/**
 * Associates cell neighbor types with the coordinates of those neighbors.
 *
 * @author Livia Seibert
 */
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
  TRIANGLE_NEUMANN_DOWN(new int[][]{{-1, 0}, {0, -1}, {0, 1}}),
  SQUARE_1C(new int[][]{{-1, -1}}),
  SQUARE_2C(new int[][]{{-1, -1}, {-1, 1}}),
  SQUARE_3C(new int[][]{{-1, -1}, {-1, 1}, {1, -1}}),
  SQUARE_4C(new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}),
  SQUARE_5C(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}}),
  SQUARE_6C(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}}),
  SQUARE_7C(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}}),
  SQUARE_E1(new int[][]{{-1, 0}}),
  SQUARE_E2(new int[][]{{-1, 0}, {0, -1}}),
  SQUARE_E3(new int[][]{{-1, 0}, {0, -1}, {0, 1}}),
  SQUARE_E5(new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {1, 0}}),
  SQUARE_E6(new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {1, 0}, {0, 1}}),
  SQUARE_E7(new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {1, 0}, {0, 1}, {0, -1}}),
  SQUARE_K2(new int[][]{{-1, -1}, {0, 1}}),
  SQUARE_K3(new int[][]{{-1, -1}, {0, 1}, {1, 0}}),
  SQUARE_K4(new int[][]{{-1, -1}, {-1, 1}, {0, -1}, {1, 0}}),
  SQUARE_K5(new int[][]{{-1, 0}, {-1, 1}, {0, -1}, {1, -1}, {1, 1}}),
  SQUARE_K6(new int[][]{{-1, 0}, {-1, 1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}}),
  SQUARE_A2(new int[][]{{-1, -1}, {-1, 0}}),
  SQUARE_A3(new int[][]{{-1, -1}, {-1, 0}, {0, -1}}),
  SQUARE_A4(new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}}),
  SQUARE_A5(new int[][]{{1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}}),
  SQUARE_A6(new int[][]{{1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {0, -1}}),
  SQUARE_I2(new int[][]{{0, -1}, {0, 1}}),
  SQUARE_I3(new int[][]{{-1, -1}, {-1, 0}, {-1, 1}}),
  SQUARE_I4(new int[][]{{-1, -1}, {-1, 1}, {0, -1}, {0, 1}}),
  SQUARE_I5(new int[][]{{0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}),
  SQUARE_I6(new int[][]{{-1, -1}, {0, -1}, {1, -1}, {-1, 1}, {0, 1}, {1, 1}}),
  SQUARE_N2(new int[][]{{-1, -1}, {1, 1}}),
  SQUARE_N3(new int[][]{{-1, -1}, {-1, 1}, {0, 1}}),
  SQUARE_N4(new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {1, -1}}),
  SQUARE_N5(new int[][]{{-1, 0}, {0, -1}, {1, -1}, {1, 0}, {1, 1}}),
  SQUARE_N6(new int[][]{{-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}}),
  SQUARE_Y3(new int[][]{{-1, -1}, {-1, 1}, {1, 0}}),
  SQUARE_Y4(new int[][]{{-1, -1}, {-1, 1}, {0, 1}, {1, -1}}),
  SQUARE_Y5(new int[][]{{-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}}),
  SQUARE_Q3(new int[][]{{-1, -1}, {-1, 0}, {1, 1}}),
  SQUARE_Q4(new int[][]{{-1, -1}, {-1, 0}, {1, 1}, {0, -1}}),
  SQUARE_Q5(new int[][]{{-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}),
  SQUARE_J3(new int[][]{{-1, -1}, {-1, 0}, {0, 1}}),
  SQUARE_J4(new int[][]{{-1, -1}, {-1, 0}, {0, 1}, {1, 0}}),
  SQUARE_J5(new int[][]{{-1, 1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}}),
  SQUARE_R3(new int[][]{{-1, -1}, {-1, 0}, {1, 0}}),
  SQUARE_R4(new int[][]{{-1, -1}, {-1, 0}, {0, -1}, {0, 1}}),
  SQUARE_R5(new int[][]{{-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 1}}),
  SQUARE_T4(new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {1, 0}}),
  SQUARE_W4(new int[][]{{-1, -1}, {-1, 0}, {0, 1}, {1, 1}}),
  SQUARE_Z4(new int[][]{{-1, -1}, {-1, 0}, {1, 0,}, {1, 1}});


  private final int[][] directions;

  /**
   * Constructor for Neighbors items, associates item with list of neighbor directions.
   *
   * @param directions neighbor cell coordinates
   */
  Neighbors(int[][] directions) {
    this.directions = directions;
  }

  /**
   * Allows access to all neighbor coordinates belonging to the neighbor style.
   *
   * @return neighbor cell coordinates
   */
  public int[][] directions() {
    return directions;
  }
}
