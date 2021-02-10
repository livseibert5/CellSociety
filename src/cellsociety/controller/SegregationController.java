package cellsociety.controller;

import cellsociety.grid.Grid;

public class SegregationController extends Controller{


  public SegregationController(Grid grid)  {
    super(grid);
  }

  @Override
  public boolean simulationEnded() {
    return false;
  }
}
