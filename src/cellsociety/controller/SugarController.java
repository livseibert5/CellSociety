package cellsociety.controller;

import cellsociety.cells.AgentCell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.PredatorCell;
import cellsociety.cells.SugarCell;
import cellsociety.cells.WatorCell;
import cellsociety.grid.Grid;

public class SugarController extends Controller{

  public SugarController()  {
    super();
  }

  @Override
  public void updateState() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    determineNextLocationsForAllCells(oldGrid, dims);
    moveAllAgentCellsToNewGrid(oldGrid, newGrid, dims);
  }

  private void determineNextLocationsForAllCells(Grid oldGrid, int[] dims) {
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        SugarCell currentCell = (SugarCell) oldGrid.getCellAtLocation(i, j);
        currentCell.determineNextState();
        if (currentCell.getHasAgent())  {
          AgentCell agentCell = currentCell.getAgent();
          agentCell.determineNextLocation();
        }
      }
    }
  }

  private void moveAllAgentCellsToNewGrid(Grid oldGrid, Grid newGrid, int[] dims) {
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {

        SugarCell sugarCell = (SugarCell) oldGrid.getCellAtLocation(i, j);
        if (sugarCell.getHasAgent())  {
          AgentCell agentCell = sugarCell.getAgent();
          int[] newLocation = agentCell.getNextLocation();
          SugarCell newCellLocation = (SugarCell) newGrid.getCellAtLocation(newLocation[0], newLocation[1]);
          newCellLocation.setAgent(agentCell);
          if (newLocation[0] != i || newLocation[1] != j) {
            sugarCell.removeAgent();
          }
          agentCell.incrementSugar(newCellLocation.getSugar());
          if (agentCell.getNextState() == AgentCell.DEAD)  {
            newCellLocation.removeAgent();
          }
        }

      }
    }
  }

}
