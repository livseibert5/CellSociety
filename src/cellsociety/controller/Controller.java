package cellsociety.controller;
import cellsociety.grid.Grid;

/**
 * Class that is abstract and creates methods for all contrller class
 * Uses the grid class
 * Called from game loop to update game state
 *
 * @author billyluqiu
 */

public abstract class Controller {

  private Grid oldGrid;
  private Grid newGrid;
  private boolean simulationEnded = true;

  /**
   * Creates controller and creates copy of grid for new grid
   * @param oldGrid initial grid state
   */
  public Controller(Grid oldGrid) {
    this.oldGrid = oldGrid;
    setNewGrid();
  }

  /**
   * resets controller by setting dimensions correctly in grid
   * and setting new grid to be a copy of the old grid
   */
  public void resetController() {
    setDims();
    this.oldGrid = this.newGrid;
    setNewGrid();
  }

  private void setDims() {
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        newGrid.getCellAtLocation(i, j).setDims(i, j);
      }
    }
  }

  /**
   * updates state of grid based off nextState in cell
   * sees if cell state changed
   */
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

  /**
   * gets newGrid object
   * @return newgrid
   */
  public Grid getNewGrid()  {
    return newGrid;
  }

  /**
   * get oldgrid objecct
   * @return oldGrid
   */
  protected Grid getOldGrid()  {
    return oldGrid;
  }

  private void setNewGrid() {
    newGrid = oldGrid.getCopyOfGrid();
  }
}
