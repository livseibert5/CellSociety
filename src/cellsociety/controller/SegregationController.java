package cellsociety.controller;

import cellsociety.cells.Cell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.Neighbors;
import cellsociety.cells.SegregationCell;
import cellsociety.grid.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class that controls the Segregation game, Uses the grid class and extends basic controller class
 * Called from game loop to update segregation state
 *
 * @author billyluqiu
 */
public class SegregationController extends Controller {

  private List<Integer> statesToAddAtCurrentIteration = new ArrayList<>();
  private int[] dims;
  private HashMap<String, Double> satisfiedMap = new HashMap<>();
  private ArrayList<ArrayList<Integer>> emptyLocs = new ArrayList<>();

  /**
   * Constructor to create the controller and set instance variables
   */
  public SegregationController() {
    super();
  }

  /**
   * method to set intial grid of segregation cell and make sure that the map is properly set
   * as well as any empty cells
   * @param oldGrid initial grid of the simulation
   */
  @Override
  public void setInitialGrid(Grid oldGrid) {
    super.setInitialGrid(oldGrid);

    dims = oldGrid.getSizeOfGrid();
    SegregationCell cell = (SegregationCell) oldGrid.getCellAtLocation(0, 0);
    satisfiedMap.put("satisfied", cell.getSatisfied());
    setEmptyCells();
    super.resetController();
  }

  private void setEmptyCells() {
    Grid grid = super.getOldGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (grid.getCellAtLocation(i, j).getState() == SegregationCell.EMPTY) {
          grid.setCellAtLocation(i, j, new EmptyCell(SegregationCell.EMPTY, i, j));
        }
      }
    }
  }

  /**
   * updates state of the grid by putting new state in new grid checks to see if cells should be
   * momved
   */
  @Override
  public void updateState() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    statesToAddAtCurrentIteration.clear();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        Cell oldCell = oldGrid.getCellAtLocation(i, j);
        if (oldCell.getState() == SegregationCell.EMPTY) {
          emptyLocs.add(new ArrayList<>(Arrays.asList(i, j)));
          continue;
        }
        int oldState = oldCell.getState();
        oldCell.determineNextState();
        int newState = oldCell.getNextState();
        setNewCellStateAndAddToEmptyLocations(newGrid, i, j, oldState, newState);
      }
    }
    addRemovedCells(newGrid);
    newGrid.initializeCells();
  }

  private void setNewCellStateAndAddToEmptyLocations(Grid newGrid, int i, int j, int oldState, int newState) {
    if (newState == SegregationCell.MOVE) {
      statesToAddAtCurrentIteration.add(oldState);
      newGrid.setCellAtLocation(i, j, new EmptyCell(SegregationCell.EMPTY, i, j));
      emptyLocs.add(new ArrayList<>(Arrays.asList(i, j)));
    } else if (newState == SegregationCell.EMPTY) {
      newGrid.setCellAtLocation(i, j, new EmptyCell(SegregationCell.EMPTY, i, j));
      emptyLocs.add(new ArrayList<>(Arrays.asList(i, j)));
    }
  }

  private void addRemovedCells(Grid newGrid) {
    Collections.shuffle(statesToAddAtCurrentIteration);
    Collections.shuffle(emptyLocs);
    for (ArrayList<Integer> loc : emptyLocs) {
      if (statesToAddAtCurrentIteration.isEmpty()) {
        return;
      }
      newGrid.setCellAtLocation(loc.get(0), loc.get(1), new SegregationCell(
          statesToAddAtCurrentIteration.remove(0), loc.get(0), loc.get(1),
          satisfiedMap, Neighbors.SQUARE_MOORE));
    }

  }

  /**
   * checks to see if simulation ended by seeing if all cell states are satisifed
   *
   * @return true if simulation is satiisfied
   */
  @Override
  public boolean simulationEnded() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (oldGrid.getCellAtLocation(i, j).getState() == SegregationCell.EMPTY && newGrid
            .getCellAtLocation(i, j).getState() == SegregationCell.EMPTY ) {
          continue;
        } else if (oldGrid.getCellAtLocation(i, j).getState() == SegregationCell.EMPTY  || newGrid
            .getCellAtLocation(i, j).getState() == SegregationCell.EMPTY ) {
          return false;
        }
        if (checkIfCellStateHasChanged(oldGrid, newGrid, i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkIfCellStateHasChanged(Grid oldGrid, Grid newGrid, int i, int j) {
    SegregationCell oldCell = (SegregationCell) oldGrid.getCellAtLocation(i, j);
    SegregationCell newCell = (SegregationCell) newGrid.getCellAtLocation(i, j);
    if (oldCell.getIsSatisfied() != newCell.getIsSatisfied()) {
      return true;
    }
    return false;
  }
}
