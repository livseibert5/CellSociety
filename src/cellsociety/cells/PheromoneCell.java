package cellsociety.cells;

public class PheromoneCell extends Cell {

  private double pheromones;

  /**
   * Cell constructor used to set basic properties of cell object.
   *
   * @param state              initial state of cell
   * @param row                row of cell
   * @param col                col of cell
   * @param neighborDirections directions to neighboring cells
   */
  public PheromoneCell(int state, int row, int col, int[][] neighborDirections) {
    super(state, row, col, neighborDirections);
  }

  public double getPheromones() {
    return pheromones;
  }

  @Override
  public void determineNextState() {

  }
}
