package cellsociety.controller;

import cellsociety.cells.Cell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.SegregationCell;
import cellsociety.grid.Grid;
import java.util.ArrayList;
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
  private boolean[][] updated;
  private int[] dims;
  private HashMap<String, Double> satisfiedMap = new HashMap<>();

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
    updated = new boolean[dims[0]][dims[1]];
    SegregationCell cell = (SegregationCell) oldGrid.getCellAtLocation(0,0);
    satisfiedMap.put("satisfied", cell.getSatisfied());
  }

  /**
   * updates state of the grid by putting new state in new grid
   * checks to see if cells should be momved
   */
  @Override
  public void updateState() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    updated = new boolean[dims[0]][dims[1]];
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++)  {
        SegregationCell oldCell = (SegregationCell) oldGrid.getCellAtLocation(i,j);
        int oldState = oldCell.getState();
        oldCell.determineNextState();
        int newState = oldCell.getNextState();
        if (newState == SegregationCell.MOVE)  {
          statesToAddAtCurrentIteration.add(oldState);
          newGrid.setCellAtLocation(i,j,new EmptyCell(0, i, j));
        }
        else  {
          newGrid.setCellAtLocation(i, j, new SegregationCell(oldState, i, j, satisfiedMap));
          updated[i][j] = true;
        }
      }
    }
    addRemovedCells(newGrid);
    newGrid.initializeCells();
  }
  private void addRemovedCells(Grid newGrid) {
    Collections.shuffle(statesToAddAtCurrentIteration);
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        if (statesToAddAtCurrentIteration.size() == 0) {
          return;
        }
        if (!updated[i][j]) {
          newGrid.setCellAtLocation(i, j, new SegregationCell(
              statesToAddAtCurrentIteration.remove(0).intValue(), i, j, satisfiedMap));
          updated[i][j] = true;
        }
      }
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
