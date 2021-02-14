package cellsociety.controller;

import cellsociety.cells.Cell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.PredatorCell;
import cellsociety.cells.PreyCell;
import cellsociety.cells.WatorCell;
import cellsociety.grid.Grid;
import java.util.HashMap;

public class WatorController extends Controller{

  boolean[][] updated;
  public WatorController(Grid oldGrid) {
    super(oldGrid);
  }


  @Override
  public void updateState() {
    //if spawn do something special
    //if same state as last just move it
    //if cell has dead cell
    //if prey next to shark we kill prey
    updateCellStates();
    moveCells();
    super.resetController();
    movePredatorAndPrey();
    super.resetController();
    super.updateState();
  }


  private void movePredatorAndPrey() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++)    {
        if (checkPredatorAndPrey(oldGrid, i, j))  {
          newGrid.setCellAtLocation(i, j, new EmptyCell(0, i, j));
        }
      }
    }
  }

  private boolean checkPredatorAndPrey(Grid grid, int i, int j) {
    if (grid.getCellAtLocation(i,j) instanceof PreyCell preyCell)  {
      if (preyCell.getState() != 1) return false;
      for (Cell cell: preyCell.getNeighbors())  {
        if (cell instanceof PredatorCell predatorCell && predatorCell.getState() == 1) return true;
      }
    }
    return false;
  }

  private void updateCellStates() {
    Grid oldGrid = super.getOldGrid();
    int[] dims = oldGrid.getSizeOfGrid();
    updated = new boolean[dims[0]][dims[1]];
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (oldGrid.getCellAtLocation(i, j) instanceof  WatorCell watorCell)  {
          watorCell.determineAction();
          if (watorCell.getNextAction() == 1) {
            continue;
          }
          else  {
            updated[i][j] = true;
          }
        }
      }
    }

  }


  private void moveCells() {

    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (oldGrid.getCellAtLocation(i, j) instanceof  WatorCell watorCell)  {
          if (watorCell.getNextAction() == 1) {
            moveCell(i, j, oldGrid, newGrid);
          }
          else if (watorCell.getNextAction() == 2) {
            newGrid.setCellAtLocation(i,j,new EmptyCell(0, i, j));
          }
          else if (watorCell.getNextAction() == 0)  {
            System.out.println("here");
            spawnCell(i, j,  oldGrid,  newGrid);
          }
        }
      }
    }
  }

  private void spawnCell(int i, int j, Grid oldGrid, Grid newGrid) {
    if (oldGrid.getCellAtLocation(i,j) instanceof PredatorCell predatorCell) {
      spawnPredatorCell(i, j, oldGrid, newGrid, predatorCell);
    }
    else if (oldGrid.getCellAtLocation(i,j) instanceof PreyCell preyCell) {
      spawnPreyCell(i, j, oldGrid, newGrid, preyCell);
    }

  }

  private void spawnPreyCell(int i, int j, Grid oldGrid, Grid newGrid, PreyCell preyCell) {
    HashMap<String, Double> params = new HashMap();
    params.put("breedTime", preyCell.getBreedTime());
    if (oldGrid.getCellAtLocation(i -1, j) == newGrid.getCellAtLocation(i -1, j) && newGrid.getCellAtLocation(
        i -1, j) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i -1, j, new PreyCell(1, i -1, j, params));
    }
    if (oldGrid.getCellAtLocation(i +1, j) == newGrid.getCellAtLocation(i +1, j) && newGrid.getCellAtLocation(
        i +1, j) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i +1, j, new PreyCell(1, i +1, j, params));
    }
    if (oldGrid.getCellAtLocation(i, j -1) == newGrid.getCellAtLocation(i, j -1) && newGrid.getCellAtLocation(
        i, j -1) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i, j -1, new PreyCell(1, i, j -1, params));
    }
    if (oldGrid.getCellAtLocation(i, j +1) == newGrid.getCellAtLocation(i, j +1) && newGrid.getCellAtLocation(
        i, j +1) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i, j +1, new PreyCell(1, i, j +1, params));
    }
  }

  private void spawnPredatorCell(int i, int j, Grid oldGrid, Grid newGrid, PredatorCell predatorCell) {
    HashMap<String, Double> params = new HashMap();
    params.put("startingEnergy", predatorCell.getStartingEnergy());
    params.put("offspringEnergy", predatorCell.getOffspringEnergy());
    if (oldGrid.getCellAtLocation(i -1, j) == newGrid.getCellAtLocation(i -1, j) && newGrid.getCellAtLocation(
        i -1, j) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i -1, j, new PredatorCell(1, i -1, j, params));
    }
    if (oldGrid.getCellAtLocation(i +1, j) == newGrid.getCellAtLocation(i +1, j) && newGrid.getCellAtLocation(
        i +1, j) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i +1, j, new PredatorCell(1, i +1, j, params));
    }
    if (oldGrid.getCellAtLocation(i, j -1) == newGrid.getCellAtLocation(i, j -1) && newGrid.getCellAtLocation(
        i, j -1) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i, j -1, new PredatorCell(1, i, j -1, params));
    }
    if (oldGrid.getCellAtLocation(i, j +1) == newGrid.getCellAtLocation(i, j +1) && newGrid.getCellAtLocation(
        i, j +1) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i, j +1, new PredatorCell(1, i, j +1, params));
    }
  }

  private void moveCell(int i, int j, Grid oldGrid, Grid newGrid) {
    if (oldGrid.getCellAtLocation(i-1, j) == newGrid.getCellAtLocation(i-1, j) && newGrid.getCellAtLocation(
        i-1, j) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i-1,j, oldGrid.getCellAtLocation(i,j));
      return;
    }
    if (oldGrid.getCellAtLocation(i+1, j) == newGrid.getCellAtLocation(i+1, j) && newGrid.getCellAtLocation(
        i+1, j) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i+1,j, oldGrid.getCellAtLocation(i,j));
      return;
    }
    if (oldGrid.getCellAtLocation(i, j-1) == newGrid.getCellAtLocation(i, j-1) && newGrid.getCellAtLocation(
        i, j-1) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i,j-1, oldGrid.getCellAtLocation(i,j));
      return;
    }
    if (oldGrid.getCellAtLocation(i, j+1) == newGrid.getCellAtLocation(i, j+1) && newGrid.getCellAtLocation(
        i, j+1) instanceof EmptyCell) {
      newGrid.setCellAtLocation(i,j+1, oldGrid.getCellAtLocation(i,j));
      return;
    }

  }

  @Override
  public boolean simulationEnded() {
    Grid newGrid = super.getNewGrid();
    int[] dims = newGrid.getSizeOfGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (newGrid.getCellAtLocation(i, j) instanceof PreyCell preyCell) {
          if (preyCell.getState() == 1) return false;
        }
      }
    }
    return true;
  }
}
