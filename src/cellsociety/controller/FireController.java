package cellsociety.controller;

import cellsociety.grid.Grid;
import cellsociety.cells.FireCell;

public class FireController extends Controller{


  public FireController(Grid grid)  {
    super(grid);
  }

  @Override
  public boolean simulationEnded() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        if (oldGrid.getCellAtLocation(i, j).getState() != newGrid.getCellAtLocation(i, j).getState()) {
          return false;
        }
      }
    }
    return true;
  }
}
