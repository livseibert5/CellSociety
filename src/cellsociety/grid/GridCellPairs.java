package cellsociety.grid;

import cellsociety.cells.Neighbors;
import java.util.ListResourceBundle;
import java.util.List;
import java.util.ArrayList;

public class GridCellPairs extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return contents;
  }

  private Object[][] contents = {
      {"Square", new ArrayList<>(List.of(Neighbors.SQUARE_MOORE, Neighbors.SQUARE_NEUMANN))},
      {"Triangular", new ArrayList<>(
          List.of(Neighbors.TRIANGLE_MOORE_DOWN, Neighbors.TRIANGLE_MOORE_UP,
              Neighbors.TRIANGLE_NEUMANN_DOWN, Neighbors.TRIANGLE_MOORE_UP))}
  };
}
