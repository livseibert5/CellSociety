package cellsociety.controller;
import cellsociety.grid.Grid;

public abstract class Controller {

  private Grid oldGrid;
  private Grid newGrid;

  public Controller(Grid oldGrid) {
    this.oldGrid = oldGrid;
  }

  public abstract void updateState();
  public abstract boolean simulationEnded();

  public Grid getNewGrid()  {
    return newGrid;
  }

}
