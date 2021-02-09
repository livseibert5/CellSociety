package cellsociety.controller;

import cellsociety.grid.Grid;

public class FireController extends Controller{


  public FireController(Grid grid)  {
    super(grid);
  }
  @Override
  public void updateState() {

  }

  @Override
  public boolean simulationEnded() {
    return false;
  }
}
