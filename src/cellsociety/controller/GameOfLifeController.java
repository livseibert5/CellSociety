package cellsociety.controller;

import cellsociety.grid.Grid;

public class GameOfLifeController extends Controller{


  public GameOfLifeController(Grid grid)  {
    super(grid);
  }


  @Override
  public boolean simulationEnded() {
    return false;
  }
}
