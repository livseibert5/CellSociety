package cellsociety.controller;

import cellsociety.cells.Cell;
import cellsociety.cells.PredatorCell;
import cellsociety.cells.PreyCell;
import cellsociety.cells.WatorCell;
import cellsociety.grid.Grid;

public class WatorController extends Controller{

  public WatorController(Grid oldGrid) {
    super(oldGrid);
  }


  @Override
  public void updateState() {
    //if spawn do something special
    //if same state as last just move it
    //if cell has dead cell
    //if prey next to shark we kill prey
    moveAllCells();
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
          newGrid.setCellAtLocation(i, j, new PreyCell(2, i, j, null));
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

  private void moveAllCells() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();
    newGrid.setCellAtLocation(dims[0]-1, dims[1]-1, oldGrid.getCellAtLocation(0,0));
    for (int i = 1; i < dims[0]-1; i++) {
      for (int j = 1; j < dims[1] - 1; j++ )    {
        newGrid.setCellAtLocation(i-1, j-1, oldGrid.getCellAtLocation(i,j));
      }
    }
    for (int i = 0; i < dims[0]-1; i++) {
      newGrid.setCellAtLocation(i-1, dims[1]-1, oldGrid.getCellAtLocation(i,0));
    }
    for (int j = 0; j < dims[1]-1; j++) {
      newGrid.setCellAtLocation(dims[0]-1, j, oldGrid.getCellAtLocation(0,j));
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
