package cellsociety.grid;

import cellsociety.cells.GameOfLifeCell;
import cellsociety.cells.PercolationCell;
import cellsociety.cells.SegregationCell;
import cellsociety.cells.WatorCell;
import java.util.ArrayList;
import cellsociety.cells.FireCell;
import java.util.List;

public enum Type {
  FIRE(new ArrayList<>(List.of(FireCell.EMPTY, FireCell.TREE, FireCell.BURNING))),
  SEGREGATION(new ArrayList<>(List.of(SegregationCell.TYPEX, SegregationCell.TYPEO))),
  PERCOLATION(new ArrayList<>(List.of(PercolationCell.BLOCKED, PercolationCell.PERCOLATED, PercolationCell.OPEN))),
  WATOR(new ArrayList<>(List.of(WatorCell.PREDATOR, WatorCell.PREY, WatorCell.DEAD))),
  LIFE(new ArrayList<>(List.of(GameOfLifeCell.ALIVE, GameOfLifeCell.DEAD))),
  EMPTY(new ArrayList<>()),
  ANTS(new ArrayList<>()),
  SUGARSCAPE(new ArrayList<>());

  private List<Integer> states;

  Type(List<Integer> states) {
    this.states = states;
  }

  public List<Integer> getStates() {
    return states;
  }
}
