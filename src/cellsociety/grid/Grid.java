package cellsociety.grid;

import cellsociety.cells.Cell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.FireCell;
import cellsociety.cells.GameOfLifeCell;
import cellsociety.cells.Neighbors;
import cellsociety.cells.PercolationCell;
import cellsociety.cells.PredatorCell;
import cellsociety.cells.PreyCell;
import cellsociety.cells.SegregationCell;
import java.util.HashMap;
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
  protected Type type;
  protected int width;
  protected int height;
  protected String fileName;
  protected Map<String, Double> params;

  /**
   * Constructor for Grid objects, creates a new grid based on the specifications passed in from the
   * XML file.
   *
   * @param width    number of columns of cell
   * @param height   number of rows of cell
   * @param fileName .txt file with initial layout of grid
   * @param type     type of simulation to run
   * @param params   map of parameters needed for simulation
   */
  public Grid(int width, int height, String fileName, Type type, Map<String, Double> params) {
    grid = new Cell[height][width];
    this.type = type;
    this.width = width;
    this.height = height;
    this.params = params;
    this.fileName = fileName;
    readFile(fileName);
    initializeCells();
  }

  /**
   * Reads the .txt file passed in from the XML parser and creates the starting layout based on it.
   *
   * @param fileName .txt file with initial layout
   */
  private void readFile(String fileName) {
    Scanner reader = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));
    int row = 0;
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] gridRow = line.split("");
      for (int col = 0; col < gridRow.length; col++) {
        int cellState = Integer.parseInt(gridRow[col]);
        setCellWithType(row, col, cellState);
      }
      row++;
    }
  }

  /**
   * Creates a cell at the indicated position with the indicated state.
   *
   * @param row       row location for the new cell
   * @param col       column location for the new cell
   * @param cellState initial state for the new cell
   */
  private void setCellWithType(int row, int col, int cellState) {
    Map<Type, Cell> cellData = createCellTypeMap(row, col, cellState);
    setCellAtLocation(row, col, cellData.get(type));
  }

  /**
   * Eliminates need for lots of if statements to determine cell type by instead loading the cells
   * and their type into a map to be selected with a get method.
   *
   * @param row       row location for the new cell
   * @param col       column location for the new cell
   * @param cellState initial state of the new cell
   * @return map with types as keys and cells as values
   */
  private Map<Type, Cell> createCellTypeMap(int row, int col, int cellState) {
    Map<Type, Cell> data = new HashMap<>();
    Cell wator = new EmptyCell(2, row, col);
    if (type == Type.WATOR && cellState == 0) {
      wator = new PredatorCell(cellState, row, col, params, Neighbors.SQUARE_NEUMANN);
    } else if (type == Type.WATOR && cellState == 1) {
      wator = new PreyCell(cellState, row, col, params, Neighbors.SQUARE_NEUMANN);
    }
    data.put(Type.FIRE, new FireCell(cellState, row, col, params, Neighbors.SQUARE_NEUMANN));
    data.put(Type.LIFE, new GameOfLifeCell(cellState, row, col, Neighbors.SQUARE_MOORE));
    data.put(Type.PERCOLATION, new PercolationCell(cellState, row, col, Neighbors.SQUARE_MOORE));
    data.put(Type.SEGREGATION, new SegregationCell(cellState, row, col, params, Neighbors.SQUARE_MOORE));
    data.put(Type.EMPTY, new EmptyCell(0, row, col));
    data.put(Type.WATOR, wator);
    return data;
  }

  /**
   * Allows access to cell objects at specific locations.
   *
   * @param row row of cell
   * @param col col of cell
   * @return Cell object located at indicated position in grid
   */
  public Cell getCellAtLocation(int row, int col) {
    if (isInBounds(row, col)) {
      return grid[row][col];
    }
    return null;
  }

  /**
   * Allows a cell object to be places at the specified location.
   *
   * @param row    row of cell
   * @param col    col of cell
   * @param cell Cell object to put at indicated location in  grid
   */
  public void setCellAtLocation(int row, int col, Cell cell) {
    if (isInBounds(row, col)) {
      grid[row][col] = cell;
    }
  }

  /**
   * Creates a list of the cell's neighbors depending on the rules specified in the cell and passes
   * it back to the cell to be used on simulation updates.
   *
   * @param row  row location of the cell
   * @param col  column location of the cell
   * @param cell cell whose neighbors are being determined within the function
   */
  private List<Cell> setNeighbors(int row, int col, Cell cell) {
    int[][] directions = cell.getNeighborDirections();
    List<Cell> neighbors = new ArrayList<>();
    for (int i = 0; i < directions.length; i++) {
      Cell neighbor = getCellAtLocation(row + directions[i][0], col + directions[i][1]);
      if (neighbor != null) {
        neighbors.add(neighbor);
      }
    }
    cell.setNeighbors(neighbors);
    return neighbors;
  }

  /**
   * When the controller moves cell objects around, they need to be re-initialized in order to have
   * the correct neighbors.
   */
  public void initializeCells() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == null) {
          setCellAtLocation(i, j, new EmptyCell(0, i, j));
        }
        setNeighbors(i, j, grid[i][j]);
      }
    }
  }

  protected boolean isInBounds(int i, int j) {
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
  }

  /**
   * Allows access to size of grid so controller can iterate over grid.
   *
   * @return array with width at index 0 and height at index 1
   */
  public int[] getSizeOfGrid() {
    return new int[]{height, width};
  }

  /**
   * Allows controller to make a deep copy of the grid to update the simulation without altering the
   * existing values.
   *
   * @return newGrid deep copy of current grid
   */
  public Grid getCopyOfGrid() {
    Grid newGrid = copySelf();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        newGrid.setCellAtLocation(i, j, this.getCellAtLocation(i, j));
        newGrid.getCellAtLocation(i, j).setNeighbors(this.getCellAtLocation(i, j).getNeighbors());
      }
    }
    return newGrid;
  }

  protected Grid copySelf() {
    Grid newGrid = new Grid(this.width, this.height, this.fileName, this.type, this.params);
    return newGrid;
  }

  /**
   * Allows access to simulation parameters so GridToXML can put them in the new XML file.
   *
   * @return map of simulation parameters
   */
  public Map<String, Double> getParams() {
    return params;
  }
}
