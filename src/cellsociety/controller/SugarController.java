package cellsociety.controller;

import cellsociety.cells.AgentCell;
import cellsociety.cells.EmptyCell;
import cellsociety.cells.Neighbors;
import cellsociety.cells.PredatorCell;
import cellsociety.cells.SugarCell;
import cellsociety.cells.WatorCell;
import cellsociety.grid.Grid;

public class SugarController extends Controller{

  public SugarController()  {
    super();
  }

  @Override
  public void setInitialGrid(Grid grid) {
    super.setInitialGrid(grid);
    loadAgents(super.getOldGrid());
    setSugar(super.getOldGrid());
    super.getOldGrid().initializeCells();
  }

  private void loadAgents(Grid grid) {
    for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
      for (int j = 0; j < grid.getSizeOfGrid()[1]; j++) {
        double probAgent = Math.random();
        if (probAgent > .50) {
          AgentCell agent = new AgentCell(AgentCell.ALIVE, i, j, grid.getParams(),
              Neighbors.SQUARE_NEUMANN.directions());
          grid.setNeighbors(i, j, agent);
          ((SugarCell) grid.getCellAtLocation(i, j)).setAgent(agent);
        }
      }
    }
  }

  private void setSugar(Grid grid) {
    int[] peakOne = {grid.getSizeOfGrid()[0] / 4, grid.getSizeOfGrid()[1] / 4};
    int[] peakTwo = {grid.getSizeOfGrid()[0] * 3 / 4, grid.getSizeOfGrid()[1] * 3 / 4};
    for (int i = 0; i < grid.getSizeOfGrid()[0]; i++) {
      for (int j = 0; j <grid.getSizeOfGrid()[1]; j++) {
        int[] location = {i, j};
        double distance = Math.min(distanceFrom(location, peakOne), distanceFrom(location, peakTwo));
        ((SugarCell) grid.getCellAtLocation(i, j)).setMaxSugarCapacity(10 / distance);
      }
    }
  }

  private double distanceFrom(int[] locationOne, int[] locationTwo) {
    return Math.sqrt(Math.pow(locationOne[0] - locationTwo[0], 2) + Math.pow(locationOne[1] - locationTwo[1], 2));
  }

  @Override
  public void updateState() {
    Grid oldGrid = super.getOldGrid();
    Grid newGrid = super.getNewGrid();
    int[] dims = oldGrid.getSizeOfGrid();

    determineNextLocationsForAllCells(oldGrid, dims);
    moveAllAgentCellsToNewGrid(oldGrid, newGrid, dims);
    newGrid.initializeCells();
  }

  private void determineNextLocationsForAllCells(Grid oldGrid, int[] dims) {
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        SugarCell currentCell = (SugarCell) oldGrid.getCellAtLocation(i, j);
        currentCell.determineNextState();
        if (currentCell.getHasAgent())  {
          AgentCell agentCell = currentCell.getAgent();
          agentCell.determineNextLocation(currentCell);
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

  @Override
  public boolean simulationEnded() {
    return false;
  }

}
