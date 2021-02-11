package cellsociety.controller;
import cellsociety.grid.Grid;

public abstract class Controller {

  private Grid oldGrid;
  private Grid newGrid;

  public Controller(Grid oldGrid) {
    this.oldGrid = oldGrid;
    setNewGrid();
  }

  public void resetController() {
    this.oldGrid = this.newGrid;
    setNewGrid();
  }
  public void updateState() {
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        newGrid.getCellAtLocation(i, j).determineNextState();
      }
    }
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        newGrid.getCellAtLocation(i, j).updateState();
      }
    }
  }

  /**
   * default method for simulationEnded
   * @returns true if state has not changed for any cell, false otherwise
   */
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
