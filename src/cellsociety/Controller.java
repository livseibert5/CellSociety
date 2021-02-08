package cellsociety;

public abstract class Controller {

  public Grid oldGrid;
  public Grid newGrid;
  public abstract void updateState();
  public abstract boolean simulationEnded();

  public Grid getNewGrid()  {
    return newGrid;
  }

}
