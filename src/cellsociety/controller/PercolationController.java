package cellsociety.controller;

import cellsociety.grid.Grid;

public class PercolationController extends Controller{

  private int startingEdge;
  //0 for top, 1 for right, 2 for bottom, 3 for left

  public PercolationController(Grid oldGrid) {
    super(oldGrid);
    findStartingEdge();
  }

  private void findStartingEdge() {
    Grid grid = super.getOldGrid();
    int[] dims = grid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      if (grid.getCellAtLocation(i, 0).getState() == 2) {
        startingEdge = 3;
        return;
      }
      else if (grid.getCellAtLocation(i, dims[1]-1).getState() == 2)  {
        startingEdge = 1;
        return;
      }
    }
    for (int j = 0; j < dims[1]; j++) {
      if (grid.getCellAtLocation(0, j).getState() == 2){
        startingEdge = 0;
        return;
      }
      else if (grid.getCellAtLocation(dims[0]-1, j).getState() == 2)  {
        startingEdge = 2;
        return;
      }
    }

  }

  @Override
  public boolean simulationEnded() {
    Grid grid = super.getNewGrid();
    int[] dims = grid.getSizeOfGrid();
    if (super.simulationEnded()) return true;
      switch (startingEdge) {
        case 0 -> {
          return checkBottomEdge(grid, dims);
        }
        case 1 ->  {
          return checkLeftEdge(grid, dims);
        }
        case 2 ->   {
          return checkTopEdge(grid, dims);
        }
        case 3->  {
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
      if (grid.getCellAtLocation(dims[0]-1, i).getState() == 2) {
        return true;
      }
    }
    return false;
  }

  private boolean checkRightEdge(Grid grid, int[] dims) {
    for (int i = 0; i < dims[0]; i++) {
      if (grid.getCellAtLocation(i, dims[1]-1).getState() == 2) {
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
