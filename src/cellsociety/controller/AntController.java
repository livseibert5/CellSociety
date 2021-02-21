package cellsociety.controller;

import cellsociety.cells.ForagerCell;
import cellsociety.cells.InsectCell;
import cellsociety.grid.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AntController extends Controller{

  private List<InsectCell> insectCellToMove = new ArrayList();
  public AntController()  {
    super();
  }

  @Override
  public void setInitialGrid(Grid grid) {
    super.setInitialGrid(grid);
    placeAnts();
  }

  private void placeAnts() {
    Grid grid = super.getOldGrid();
    for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
      for (int j = 0; j < grid.getSizeOfGrid()[1]; j++) {
        if (grid.getCellAtLocation(i, j).getState() == ForagerCell.NEST || grid.getCellAtLocation(i, j).getState() == ForagerCell.FOOD_SOURCE) {
          for (int k = 0; k < grid.getSizeOfGrid()[0]; k++) {
            InsectCell ant = new InsectCell(0, i, j);
            grid.setNeighbors(i, j, ant);
            ((ForagerCell) grid.getCellAtLocation(i, j)).addAnt(ant);
          }
          ((ForagerCell) grid.getCellAtLocation(i, j)).setPheromones("Food", Math.random() * i / 2);
          ((ForagerCell) grid.getCellAtLocation(i, j)).setPheromones("Home", Math.random() * i / 2);
        }
      }
    }
  }

  @Override
  public void updateState() {
    Grid newGrid = super.getNewGrid();
    determineNextStates(newGrid);
    updatePheromones(newGrid);
    clearAntLocationsFromNewGridAndAddToList(newGrid);
    moveAntCells(newGrid);
    super.resetController();
    insectCellToMove.clear();
  }

  private void clearAntLocationsFromNewGridAndAddToList(Grid newGrid) {
    int[] dims = newGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        ForagerCell foragerCell = (ForagerCell) newGrid.getCellAtLocation(i,j);
        insectCellToMove.addAll(foragerCell.getAnts());
        foragerCell.getAnts().clear();
      }
    }
  }

  private void moveAntCells(Grid newGrid) {
    while (insectCellToMove.size() > 0) {
      InsectCell insectCell = insectCellToMove.remove(0);
      int[] curLoc = insectCell.getNextLocation();
      System.out.println(Arrays.toString(curLoc));
      ForagerCell foragerCell = (ForagerCell) newGrid.getCellAtLocation(curLoc[0],curLoc[1]);
      foragerCell.addAnt(insectCell);
    }
  }

  private void updatePheromones(Grid newGrid) {
    int[] dims = newGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        ForagerCell foragerCell = (ForagerCell) newGrid.getCellAtLocation(i,j);
        for (InsectCell insectCell: foragerCell.getAnts())  {
          if (insectCell.getNextAction() == InsectCell.DROP_FOOD_PHEROMONES)  {
            //Ask Livia about what to update the pheromones level to
             foragerCell.setPheromones("Food", foragerCell.getPheromones("Food")+0.5);
            insectCell.returnToNest(foragerCell);
          }
          else if (insectCell.getNextAction() == InsectCell.DROP_HOME_PHEROMONES) {
            foragerCell.setPheromones("Home", foragerCell.getPheromones("Home")+0.5);
            insectCell.findFoodSource(foragerCell);
          }
        }
      }
    }
  }

  private void determineNextStates( Grid newGrid) {
    int[] dims = newGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        ForagerCell foragerCell = (ForagerCell) newGrid.getCellAtLocation(i,j);
        foragerCell.determineNextAction();
      }
    }
  }

  @Override
  public boolean simulationEnded() {
    return false;
  }

}
