package cellsociety.grid;

import cellsociety.cells.Cell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.FireCell;
import cellsociety.cells.GameOfLifeCell;
import cellsociety.cells.PercolationCell;
import cellsociety.cells.SegregationCell;
import cellsociety.cells.WatorCell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class that holds cell in proper formation for simulation.
 *
 * @author Livia Seibert
 */
public class Grid {

  protected Cell[][] grid;
  private Type type;
  private int width;
  private int height;
  private Map<String, Double> params;

  /**
   * Constructor for Grid objects, creates a new grid based on the specifications
   * passed in from the XML file.
   *
   * @param width number of columns of cell
   * @param height number of rows of cell
   * @param fileName .txt file with initial layout of grid
   * @param type type of simulation to run
   * @param params map of parameters needed for simulation
   */
  public Grid(int width, int height, String fileName, Type type, Map<String, Double> params) {
    grid = new Cell[height][width];
    this.type = type;
    this.width = width;
    this.height = height;
    this.params = params;
    readFile(fileName);
    initializeCells();
  }

  private void readFile(String fileName) {
    Scanner reader = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));
    int row = 0;
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] gridRow = line.split("");
      for (int col = 0; col < gridRow.length; col++) {
        if (type == Type.FIRE) {
          setCellAtLocation(row, col, new FireCell(Integer.parseInt(gridRow[col]), row, col, params));
        } else if (type == Type.LIFE) {
          setCellAtLocation(row, col, new GameOfLifeCell(Integer.parseInt(gridRow[col]), row, col));
        } else if (type == Type.PERCOLATION) {
          setCellAtLocation(row, col, new PercolationCell(Integer.parseInt(gridRow[col]), row, col));
        } else if (type == Type.WATOR) {
          setCellAtLocation(row, col, new WatorCell(Integer.parseInt(gridRow[col]), row, col, params));
        } else if (type == Type.SEGREGATION) {
          setCellAtLocation(row, col, new SegregationCell(Integer.parseInt(gridRow[col]), row, col, params));
        } else {
          setCellAtLocation(row, col, new EmptyCell(0, row, col));
        }
      }
      row++;
    }
  }

  /**
   * Allows access to cell objects at specific locations.
   *
   * @param i row of cell
   * @param j col of cell
   * @return Cell object located at indicated position in grid
   */
  public Cell getCellAtLocation(int i, int j) {
    if (isInBounds(i, j)) {
      return grid[i][j];
    }
    return null;
  }

  private void setCellAtLocation(int i, int j, Cell cell) {
    if (isInBounds(i, j)) {
      grid[i][j] = cell;
    }
  }

  private void setNeighbors(int row, int col, Cell cell) {
    int[][] directions = cell.getNeighborDirections();
    List<Cell> neighbors = new ArrayList<>();
    for (int i = 0; i < directions.length; i++) {
      Cell neighbor = getCellAtLocation(row + directions[i][0], col + directions[i][1]);
      if (neighbor != null) {
        neighbors.add(neighbor);
      }
    }
    cell.setNeighbors(neighbors);
  }

  private void initializeCells() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == null) {
          setCellAtLocation(i, j, new EmptyCell(0, i, j));
        }
        setNeighbors(i, j, grid[i][j]);
        System.out.println(grid[i][j]);
      }
    }
  }

  protected boolean isInBounds(int i, int j) {
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
  }
}
