package cellsociety.controller;

import cellsociety.cells.Cell;
import cellsociety.cells.SegregationCell;
import cellsociety.grid.Grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SegregationController extends Controller{

  private List<Integer> statesToAddAtCurrentIteration = new ArrayList();
  private boolean[][] updated;
  private int[] dims;
  public SegregationController(Grid grid)  {
    super(grid);
    Grid oldGrid = super.getOldGrid();
    dims = oldGrid.getSizeOfGrid();
    updated = new boolean[dims[0]][dims[1]];
  }

  @Override
  public void updateState() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j< dims[1]; j++)  {
        SegregationCell oldCell = (SegregationCell) oldGrid.getCellAtLocation(i,j);
        int oldState = oldCell.getState();
        oldCell.determineNextState();
        int newState = oldCell.getNextState();
        if (newState == 3)  {
          statesToAddAtCurrentIteration.add(oldState);
        }
        else  {
          newGrid.setCellAtLocation(i, j, new SegregationCell(oldState, i, j, null));
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
        if (statesToAddAtCurrentIteration.size() == 0) return;
        if (!updated[i][j]) {
          newGrid.setCellAtLocation(i, j, new SegregationCell(
              statesToAddAtCurrentIteration.remove(0), i, j, null));
          updated[i][j] = true;
        }
      }
    }

  }

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
