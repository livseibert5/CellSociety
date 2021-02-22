package cellsociety.controller;

import cellsociety.grid.Grid;
import java.util.HashMap;

/**
 * Class that is abstract and creates methods for all controller class Uses the grid class Called
 * from game loop to update game state
 *
 * @author billyluqiu
 */

public abstract class Controller {

  private Grid oldGrid;
  private Grid newGrid;
  private boolean simulationEnded = true;

  /**
   * Creates controller and creates copy of grid for new grid
   */

  public Controller() {

  }

  public void setInitialGrid(Grid oldGrid) {
    this.oldGrid = oldGrid;
    createNewGridAsCopy();
  }

  public Controller(Grid oldGrid) {
    this.oldGrid = oldGrid;
    createNewGridAsCopy();
  }

  /**
   * resets controller by setting dimensions correctly in grid and setting new grid to be a copy of
   * the old grid
   */
  public void resetController() {
    setDims();
    this.oldGrid = this.newGrid;
    createNewGridAsCopy();
  }

  private void setDims() {
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        newGrid.getCellAtLocation(i, j).setLocation(i, j);
      }
    }
  }

  /**
   * updates state of grid based off nextState in cell sees if cell state changed
   */
  public void updateState() {
    simulationEnded = true;
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        newGrid.getCellAtLocation(i, j).determineNextState();
      }
    }
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (newGrid.getCellAtLocation(i, j).getState() != newGrid.getCellAtLocation(i, j)
            .getNextState()) {
          simulationEnded = false;
        }
        newGrid.getCellAtLocation(i, j).updateState();
      }
    }
  }

  /**
   * default method for simulationEnded
   *
   * @return true if state has not changed for any cell, false otherwise
   */
  public boolean simulationEnded() {
    return simulationEnded;
  }

  /**
   * gets newGrid object
   *
   * @return newgrid
   */
  public Grid getNewGrid() {
    return newGrid;
  }

  /**
   * get oldgrid objecct
   *
   * @return oldGrid
   */
  protected Grid getOldGrid() {
    return oldGrid;
  }

  private void createNewGridAsCopy() {
    newGrid = oldGrid.getCopyOfGrid();
  }

  /**
   * Calculates the numbers of each type of cell
   *
   * @return HashMap where the key is the type of cell and the value is the number of cells of that
   * type in the newGrid at the time
   */
  public HashMap<Integer, Integer> calculateNumberOfEachCell() {
    HashMap<Integer, Integer> counts = new HashMap<>();
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        int type = newGrid.getCellAtLocation(i, j).getState();
        counts.putIfAbsent(type, 0);
        counts.put(type, counts.get(type) + 1);

      }
    }
    return counts;

  }
}
