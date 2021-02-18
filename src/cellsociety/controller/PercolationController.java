package cellsociety.controller;

import cellsociety.cells.PercolationCell;
import cellsociety.grid.Grid;

/**
 * Class that controls the percolration game, Uses the grid class and extends basic controller class
 * Called from game loop to update percolation state
 *
 * @author billyluqiu
 */
public class PercolationController extends Controller {

  private int startingEdge;
  //0 for top, 1 for right, 2 for bottom, 3 for left

  /**
   * Constructor to create the controller
   */
  public PercolationController() {
    super();
  }

  @Override
  public void setInitialGrid(Grid oldGrid) {
    super.setInitialGrid(oldGrid);
    findStartingEdge();
  }

  private void findStartingEdge() {
    Grid grid = super.getOldGrid();
    int[] dims = grid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      if (grid.getCellAtLocation(i, 0).getState() == PercolationCell.PERCOLATED) {
        startingEdge = 3;
        return;
      } else if (grid.getCellAtLocation(i, dims[1] - 1).getState() == PercolationCell.PERCOLATED) {
        startingEdge = 1;
        return;
      }
    }
    for (int j = 0; j < dims[1]; j++) {
      if (grid.getCellAtLocation(0, j).getState() == PercolationCell.PERCOLATED) {
        startingEdge = 0;
        return;
      } else if (grid.getCellAtLocation(dims[0] - 1, j).getState() == PercolationCell.PERCOLATED) {
        startingEdge = 2;
        return;
      }
    }

  }

  /**
   * updates state of the grid by putting new state in new grid
   */
  @Override
  public void updateState() {
    if (simulationEndedByEdge(super.getOldGrid(), super.getOldGrid().getSizeOfGrid())) {
      return;
    }
    super.updateState();
  }

  /**
   * checks to see if simulation ended by seeing if cell is percolrated or if state has not
   * changed
   *
   * @return true if simulation ended
   */
  @Override
  public boolean simulationEnded() {
    Grid grid = super.getNewGrid();
    int[] dims = grid.getSizeOfGrid();
    if (super.simulationEnded()) {
      return true;
    }
    return simulationEndedByEdge(grid, dims);
  }

  private boolean simulationEndedByEdge(Grid grid, int[] dims) {
    switch (startingEdge) {
      case 0 -> {
        return checkBottomEdge(grid, dims);
      }
      case 1 -> {
        return checkLeftEdge(grid, dims);
      }
      case 2 -> {
        return checkTopEdge(grid, dims);
      }
      case 3 -> {
        return checkRightEdge(grid, dims);
      }

    }
    return false;
  }

  private boolean checkLeftEdge(Grid grid, int[] dims) {
    for (int i = 0; i < dims[0]; i++) {
      if (grid.getCellAtLocation(i, 0).getState() == 2) {
        return true;
      }
    }
    return false;
  }

  private boolean checkBottomEdge(Grid grid, int[] dims) {
    for (int i = 0; i < dims[1]; i++) {
      if (grid.getCellAtLocation(dims[0] - 1, i).getState() == 2) {
        return true;
      }
    }
    return false;
  }

  private boolean checkRightEdge(Grid grid, int[] dims) {
    for (int i = 0; i < dims[0]; i++) {
      if (grid.getCellAtLocation(i, dims[1] - 1).getState() == 2) {
        return true;
      }
    }
    return false;
  }

  private boolean checkTopEdge(Grid grid, int[] dims) {
    for (int i = 0; i < dims[1]; i++) {
      if (grid.getCellAtLocation(0, i).getState() == 2) {
        return true;
      }
    }
    return false;
  }

}
