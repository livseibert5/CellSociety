package cellsociety.controller;

import cellsociety.cells.ForagerCell;
import cellsociety.cells.InsectCell;
import cellsociety.cells.SugarCell;
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
    setPheromones(super.getOldGrid());
    super.getOldGrid().initializeCells();
  }

  private void placeAnts() {
    Grid grid = super.getOldGrid();
    for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
      for (int j = 0; j < grid.getSizeOfGrid()[1]; j++) {
        double probAnt = .1;
        if (Math.random() < probAnt) {
          InsectCell ant = new InsectCell(0, i, j);
          grid.setNeighbors(i, j, ant);
          ((ForagerCell) grid.getCellAtLocation(i, j)).addAnt(ant);
        }
      }
    }
  }

  private void setPheromones(Grid grid) {
    int[] nest = findSource(grid, ForagerCell.NEST);
    int[] food = findSource(grid, ForagerCell.FOOD_SOURCE);
    for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
      for (int j = 0; j <grid.getSizeOfGrid()[1]; j++) {
        int[] location = {i, j};
        ((ForagerCell) grid.getCellAtLocation(i, j)).setPheromones(ForagerCell.HOME, ForagerCell.MAX_PHEROMONES / distanceFrom(location, nest));
        ((ForagerCell) grid.getCellAtLocation(i, j)).setPheromones(ForagerCell.FOOD, ForagerCell.MAX_PHEROMONES / distanceFrom(location, food));
      }
    }
  }

  private int[] findSource(Grid grid, int type) {
    for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
      for (int j = 0; j <grid.getSizeOfGrid()[1]; j++) {
        if (grid.getCellAtLocation(i, j).getState() == type) {
          return new int[]{i, j};
        }
      }
    }
    return new int[]{0, 0};
  }

  private double distanceFrom(int[] locationOne, int[] locationTwo) {
    return Math.sqrt(Math.pow(locationOne[0] - locationTwo[0], 2) + Math.pow(locationOne[1] - locationTwo[1], 2));
  }

  @Override
  public void updateState() {
    super.getOldGrid().initializeCells();
    Grid newGrid = super.getNewGrid();
    determineNextStates(newGrid);
    clearAntLocationsFromNewGridAndAddToList(newGrid);
    moveAntCells(newGrid);
    super.getNewGrid().initializeCells();
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
    for (InsectCell cell: insectCellToMove) {
      ForagerCell foragerCell = (ForagerCell) newGrid.getCellAtLocation(cell.getNextLocation()[0],cell.getNextLocation()[1]);
      cell.setLocation(cell.getNextLocation()[0],cell.getNextLocation()[1]);
      foragerCell.addAnt(cell);
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
