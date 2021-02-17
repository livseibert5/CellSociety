package cellsociety.grid;

import cellsociety.cells.Neighbors;
import java.util.Map;
import java.util.Scanner;

/**
 * Class that represents a grid of triangular cells. Overwrites readFile from Grid class so that
 * this grid can orient triangles properly and give them triangular neighbors as specified in the
 * neighbors enum.
 *
 * @author Livia Seibert
 */
public class TriangularGrid extends Grid {

  /**
   * Constructor for a TriangularGrid object that extends grid's functionality to create a grid with
   * triangular cells.
   *
   * @param width    number of columns of cell
   * @param height   number of rows of cell
   * @param fileName .txt file with initial layout of grid
   * @param type     type of simulation to run
   * @param params   map of parameters needed for simulation
   */
  public TriangularGrid(int width, int height, String fileName, Type type,
      Map<String, Double> params, Neighbors neighborDirections) {
    super(width, height, fileName, type, params, neighborDirections);
  }

  protected void readFile(String fileName) {
    Scanner reader = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));
    int row = 0;
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] gridRow = line.split("");
      for (int col = 0; col < gridRow.length; col++) {
        int cellState = Integer.parseInt(gridRow[col]);
        if (row % 2 == 0) {
          if (col % 2 == 0) {
            setCellWithType(row, col, cellState, Neighbors.TRIANGLE_MOORE_DOWN);
          } else {
            setCellWithType(row, col, cellState, Neighbors.TRIANGLE_MOORE_UP);
          }
        } else {
          if (col % 2 == 0) {
            setCellWithType(row, col, cellState, Neighbors.TRIANGLE_MOORE_UP);
          } else {
            setCellWithType(row, col, cellState, Neighbors.TRIANGLE_MOORE_DOWN);
          }
        }
      }
      row++;
    }
  }
}
