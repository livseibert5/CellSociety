package cellsociety.cells;

/**
 * Associates orientations with the directions that would be considered forward from those
 * orientations.
 *
 * @author Livia Seibert
 */
enum Orientation {
  NORTH(new int[][]{{-1, 0}, {-1, -1}, {-1, 1}}),
  EAST(new int[][]{{0, 1}, {1, 1}, {-1, 1}}),
  SOUTH(new int[][]{{1, 0}, {1, 1}, {1, -1}}),
  WEST(new int[][]{{0, -1}, {-1, -1}, {1, -1}});

  private final int[][] directions;

  Orientation(int[][] directions) {
    this.directions = directions;
  }

  int[][] directions() {
    return directions;
  }
}
