package cellsociety.controller;
import cellsociety.grid.Grid;

public abstract class Controller {

  private Grid oldGrid;
  private Grid newGrid;
  private boolean simulationEnded = true;

  public Controller(Grid oldGrid) {
    this.oldGrid = oldGrid;
    setNewGrid();
  }

  public void resetController() {
    setDims();
    this.oldGrid = this.newGrid;
    setNewGrid();
  }

  public void setDims() {
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        newGrid.getCellAtLocation(i, j).setDims(i, j);
      }
    }
  }
  public void updateState() {

    simulationEnded = true;
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        newGrid.getCellAtLocation(i, j).determineNextState();
      }
    }
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        if (newGrid.getCellAtLocation(i,j).getState() != newGrid.getCellAtLocation(i,j).getNextState()) {
          simulationEnded = false;
        }
        newGrid.getCellAtLocation(i, j).updateState();
      }
    }
  }

  /**
   * default method for simulationEnded
   * @returns true if state has not changed for any cell, false otherwise
   */
  public boolean simulationEnded()  {
    return simulationEnded;
  }

  public Grid getNewGrid()  {
    return newGrid;
  }

  protected Grid getOldGrid()  {
    return oldGrid;
  }

  protected void setNewGrid() {
    newGrid = oldGrid.getCopyOfGrid();
  }
}
