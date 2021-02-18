package cellsociety.controller;

import cellsociety.cells.EmptyCell;
import cellsociety.cells.Neighbors;
import cellsociety.cells.PredatorCell;
import cellsociety.cells.PreyCell;
import cellsociety.cells.WatorCell;
import cellsociety.grid.Grid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that controls the Wator game, Uses the grid class and extends basic controller class Called
 * from game loop to update Wator state
 *
 * @author billyluqiu
 */
public class WatorController extends Controller {

  //boolean[][] updated;
  private int[][] neighbors = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

  /**
   * Constructor to create the controller
   */
  public WatorController() {
    super();
  }

  /**
   * updates state of the grid by putting new state in new grid Uses assumed rules of the Wator game
   * from this website
   * <p>
   * https://beltoforion.de/en/wator/
   */
  @Override
  public void updateState() {
    //if spawn do something special
    //if same state as last just move it
    //if cell has dead cell
    //if prey next to shark we kill prey
    updateCellStates();
    moveCells();
    super.resetController();
    super.updateState();
  }

  private void updateCellStates() {
    Grid oldGrid = super.getOldGrid();
    int[] dims = oldGrid.getSizeOfGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (oldGrid.getCellAtLocation(i, j) instanceof WatorCell watorCell) {
          watorCell.determineAction();
        }
      }
    }
  }

  private void shuffleNeighbors() {
    List<int[]> neighborList = new ArrayList<>();
    for (int i = 0; i < neighbors.length; i++) {
      neighborList.add(neighbors[i]);
    }
    Collections.shuffle(neighborList);
    for (int i = 0; i < neighbors.length; i++) {
      neighbors[i] = neighborList.get(i);
    }
  }


  private void moveCells() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (oldGrid.getCellAtLocation(i, j) instanceof PredatorCell) {
          ((PredatorCell) oldGrid.getCellAtLocation(i, j)).decrementEnergy();
        }
        if (oldGrid.getCellAtLocation(i, j) instanceof WatorCell watorCell) {
          if (watorCell.getNextAction() == WatorCell.MOVE) {
            moveCell(i, j, oldGrid, newGrid);
            newGrid.setCellAtLocation(i, j, new EmptyCell(2, i, j));
          } else if (watorCell.getNextAction() == WatorCell.DEAD) {
            newGrid.setCellAtLocation(i, j, new EmptyCell(2, i, j));
          } else if (watorCell.getNextAction() == WatorCell.SPAWN) {
            moveCell(i, j, oldGrid, newGrid);
            if (watorCell instanceof PredatorCell) {
              HashMap<String, Double> params = new HashMap();
              params.put("startingEnergy", ((PredatorCell) watorCell).getStartingEnergy());
              params.put("offspringEnergy", ((PredatorCell) watorCell).getOffspringEnergy());
              newGrid.setCellAtLocation(i, j, new PredatorCell(WatorCell.PREDATOR, i, j, params,
                  Neighbors.SQUARE_NEUMANN));
            } else if (watorCell instanceof PreyCell) {
              HashMap<String, Double> params = new HashMap();
              params.put("breedTime", ((PreyCell) watorCell).getBreedTime());
              newGrid.setCellAtLocation(i, j, new PreyCell(WatorCell.PREY, i, j, params, Neighbors.SQUARE_NEUMANN));
            }
          }
          newGrid.initializeCells();
        }
      }
    }
  }

  private void moveCell(int i, int j, Grid oldGrid, Grid newGrid) {
    if (oldGrid.getCellAtLocation(i, j) instanceof PredatorCell predatorCell) {
      if (movePredator(i, j, oldGrid, newGrid)) {
        predatorCell.incrementEnergy();
        return;
      }
    }
    shuffleNeighbors();
    for (int index = 0; index < neighbors.length; index++) {
      int row = i + neighbors[index][0];
      int col = j + neighbors[index][1];
      if (newGrid.getCellAtLocation(
          row, col) instanceof EmptyCell) {
        newGrid.setCellAtLocation(row, col, oldGrid.getCellAtLocation(i, j));
        return;
      }
    }
  }

  private boolean movePredator(int i, int j, Grid oldGrid, Grid newGrid) {
    shuffleNeighbors();
    for (int index = 0; index < neighbors.length; index++) {
      int row = i + neighbors[index][0];
      int col = j + neighbors[index][1];
      if (newGrid.getCellAtLocation(
          row, col) instanceof PreyCell) {
        newGrid.setCellAtLocation(row, col, oldGrid.getCellAtLocation(i, j));
        return true;
      }
    }
    return false;
  }

  /**
   * checks to see if simulation ended by seeing if cells are still moving
   *
   * @return true if simulation ended
   */
  @Override
  public boolean simulationEnded() {
    Grid newGrid = super.getNewGrid();
    int[] dims = newGrid.getSizeOfGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (newGrid.getCellAtLocation(i, j) instanceof PreyCell preyCell) {
          if (preyCell.getState() == WatorCell.MOVE) {
            return false;
          }
        }
      }
    }
    return true;
  }
}
