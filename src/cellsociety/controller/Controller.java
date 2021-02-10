package cellsociety.controller;
import cellsociety.grid.Grid;

public abstract class Controller {

  private Grid oldGrid;
  private Grid newGrid;

  public Controller(Grid oldGrid) {
    this.oldGrid = oldGrid;
    setNewGrid();
  }

  public void updateState() {
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        newGrid.getCellAtLocation(i, j).determineNextState();
        newGrid.getCellAtLocation(i, j).updateState();
      }
    }
  }
  public boolean simulationEnded()  {
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

  protected Grid getNewGrid()  {
    return newGrid;
  }

  protected Grid getOldGrid()  {
    return oldGrid;
  }

  protected void setNewGrid() {
    newGrid = oldGrid.getCopyOfGrid();
  }



}
