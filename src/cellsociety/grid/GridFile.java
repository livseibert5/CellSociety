package cellsociety.grid;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

/**
 * Takes in a filename and a grid and writes that grid to a .txt file with the given filename.
 *
 * @author Livia Seibert
 */
public class GridFile {

  private final String fileName;
  private final File newFile;
  private final Grid grid;
  private final String PATH = "data/";
  private final String TXT = ".txt";

  /**
   * Constructor for a GridFile object that takes a name and a grid and creates
   * a new .txt file.
   *
   * @param name filename for new .txt file
   * @param grid grid state to be written to the file
   */
  public GridFile(String name, Grid grid) {
    this.grid = grid;
    fileName = name;
    newFile = new File(PATH + fileName + TXT);
  }

  /**
   * Writes grid layout to a .txt file
   *
   * @throws IOException error writing to new .txt file
   */
  public void writeGridToFile() throws IOException {
    newFile.createNewFile();
    try (FileWriter writer = new FileWriter(PATH + fileName + TXT)) {
      for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
        StringBuilder gridRow = new StringBuilder();
        for (int j = 0; j < grid.getSizeOfGrid()[1]; j++) {
          gridRow.append(grid.getCellAtLocation(i, j).getState());
        }
        writer.write(gridRow.toString());
        writer.write(System.getProperty("line.separator"));
      }
    }
  }

  /**
   * Allows GridToXML to access the filename so it can be written to the XML file.
   *
   * @return name of new file
   */
  public String getFileName() {
    return fileName;
  }
}
