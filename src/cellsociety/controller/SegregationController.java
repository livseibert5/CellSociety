package cellsociety.controller;

import cellsociety.cells.SegregationCell;
import cellsociety.grid.Grid;

public class SegregationController extends Controller{


  public SegregationController(Grid grid)  {
    super(grid);
  }

  @Override
  public boolean simulationEnded() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        SegregationCell oldCell = (SegregationCell) oldGrid.getCellAtLocation(i, j);
        SegregationCell newCell = (SegregationCell) newGrid.getCellAtLocation(i, j);
        if (oldCell.getIsSatisfied() != newCell.getIsSatisfied()) {
          return false;
        }
      }
    }
    return true;
  }
}
