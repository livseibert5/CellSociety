package cellsociety.controller;

import cellsociety.cells.Cell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.SegregationCell;
import cellsociety.grid.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class that controls the Segregation game,
 * Uses the grid class and extends basic controller class
 * Called from game loop to update segregation state
 *
 * @author billyluqiu
 */
public class SegregationController extends Controller{

  private List<Integer> statesToAddAtCurrentIteration = new ArrayList();
  private int[] dims;
  private HashMap<String, Double> satisfiedMap = new HashMap<>();
  private ArrayList<ArrayList<Integer>> emptyLocs = new ArrayList<>();
  /**
   * Constructor to create the controller and set instance variables
   *
   */
  public SegregationController()  {
    super();
  }

  @Override
  public void setInitialGrid(Grid oldGrid) {
    super.setInitialGrid(oldGrid);

    dims = oldGrid.getSizeOfGrid();
    SegregationCell cell = (SegregationCell) oldGrid.getCellAtLocation(0,0);
    satisfiedMap.put("satisfied", cell.getSatisfied());
    setEmptyCells();
    super.resetController();
  }

  private void setEmptyCells() {
    Grid grid = super.getOldGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (grid.getCellAtLocation(i,j).getState() == SegregationCell.EMPTY)  {
          grid.setCellAtLocation(i,j, new EmptyCell(2,i,j));
        }
      }
    }
  }

  /**
   * updates state of the grid by putting new state in new grid
   * checks to see if cells should be momved
   */
  @Override
  public void updateState() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    statesToAddAtCurrentIteration.clear();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++)  {
        if (!(oldGrid.getCellAtLocation(i,j) instanceof SegregationCell)) {
          emptyLocs.add(new ArrayList<Integer>(Arrays.asList(i, j)));
          continue;
        }
        SegregationCell oldCell = (SegregationCell) oldGrid.getCellAtLocation(i,j);
        int oldState = oldCell.getState();
        oldCell.determineNextState();
        int newState = oldCell.getNextState();
        if (newState == SegregationCell.MOVE)  {
          statesToAddAtCurrentIteration.add(oldState);
          newGrid.setCellAtLocation(i,j,new EmptyCell(2, i, j));
          emptyLocs.add(new ArrayList<Integer>(Arrays.asList(i, j)));
        }
        else if (newState == SegregationCell.EMPTY){
          newGrid.setCellAtLocation(i,j,new EmptyCell(2, i, j));
          emptyLocs.add(new ArrayList<Integer>(Arrays.asList(i, j)));
        }
      }
    }
    addRemovedCells(newGrid);
    newGrid.initializeCells();
  }
  private void addRemovedCells(Grid newGrid) {
    Collections.shuffle(statesToAddAtCurrentIteration);
    Collections.shuffle(emptyLocs);
    for (ArrayList<Integer> loc: emptyLocs) {
      if (statesToAddAtCurrentIteration.size() == 0)  {
        return;
      }
      newGrid.setCellAtLocation(loc.get(0), loc.get(1), new SegregationCell(
          statesToAddAtCurrentIteration.remove(0).intValue(), loc.get(0), loc.get(1), satisfiedMap));
    }

  }

  /**
   * checks to see if simulation ended by seeing if all cell states are satisifed
   * @return true if simulation is satiisfied
   */
  @Override
  public boolean simulationEnded() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        if (oldGrid.getCellAtLocation(i,j) instanceof EmptyCell && newGrid.getCellAtLocation(i,j) instanceof EmptyCell) {
          continue;
        }
        else if (oldGrid.getCellAtLocation(i,j) instanceof EmptyCell || newGrid.getCellAtLocation(i,j) instanceof EmptyCell) {
          return false;
        }
        SegregationCell oldCell = (SegregationCell) oldGrid.getCellAtLocation(i, j);
        SegregationCell newCell = (SegregationCell) newGrid.getCellAtLocation(i, j);
        if (oldCell.getIsSatisfied() != newCell.getIsSatisfied()) {
          return false;
        }
      }
    }
    return true;
  }
}
