package cellsociety.grid;

import cellsociety.cells.FireCell;
import java.util.Scanner;

/**
 * Creates new Grid object from .txt file specified in XML.
 *
 * @author Livia Seibert
 */
public class GridCreator {

  private int width;
  private int height;
  private Type type;
  private Grid grid;

  public GridCreator(int width, int height, String fileName, Type type) {
    this.width = width;
    this.height = height;
    this.type = type;
    this.grid = new Grid(width, height);
    readFile(fileName);
  }

  private void readFile(String fileName) {
    Scanner reader = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));
    int row = 0;
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] gridRow = line.split("");
      for (int col = 0; col < gridRow.length; col++) {
        if (type == Type.FIRE) {
          grid.setCellAtLocation(row, col, new FireCell());
        }
      }
      row++;
    }
  }

  /**
   * Accesses grid created by this class.
   *
   * @return new grid object
   */
  public Grid getGrid() {
    return grid;
  }

}
