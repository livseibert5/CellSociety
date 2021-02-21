package cellsociety.grid;

import cellsociety.cells.ForagerCell;
import cellsociety.cells.GameOfLifeCell;
import cellsociety.cells.PercolationCell;
import cellsociety.cells.SegregationCell;
import cellsociety.cells.SugarCell;
import cellsociety.cells.WatorCell;
import java.util.ArrayList;
import cellsociety.cells.FireCell;
import java.util.List;

/**
 * Associates Type of simulation with potential states.
 *
 * @author Livia Seibert
 */
public enum Type {
  FIRE(new ArrayList<>(List.of(FireCell.EMPTY, FireCell.TREE, FireCell.BURNING))),
  SEGREGATION(new ArrayList<>(List.of(SegregationCell.TYPEX, SegregationCell.TYPEO))),
  PERCOLATION(new ArrayList<>(List.of(PercolationCell.BLOCKED, PercolationCell.PERCOLATED, PercolationCell.OPEN))),
  WATOR(new ArrayList<>(List.of(WatorCell.PREDATOR, WatorCell.PREY, WatorCell.DEAD))),
  LIFE(new ArrayList<>(List.of(GameOfLifeCell.ALIVE, GameOfLifeCell.DEAD))),
  EMPTY(new ArrayList<>()),
  ANTS(new ArrayList<>(List.of(ForagerCell.NEST, ForagerCell.FOOD_SOURCE, ForagerCell.EMPTY, ForagerCell.OBSTACLE))),
  SUGARSCAPE(new ArrayList<>(List.of(SugarCell.EMPTY)));

  private List<Integer> states;

  /**
   * Constructor for type enum, associates a simulation type with a list of its states.
   *
   * @param states list of states
   */
  Type(List<Integer> states) {
    this.states = states;
  }

  /**
   * Lets Grid generate random layouts by accessing the states associated with a simulation type.
   *
   * @return list of states
   */
  public List<Integer> getStates() {
    return states;
  }
}
