package cellsociety.controller;

import cellsociety.grid.Grid;

public class WatorController extends Controller{

  public WatorController(Grid oldGrid) {
    super(oldGrid);
  }

  @Override
  public boolean simulationEnded() {
    return false;
  }
}
