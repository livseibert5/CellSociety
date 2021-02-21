package cellsociety.visuals;

import cellsociety.controller.AntController;
import cellsociety.controller.Controller;
import cellsociety.controller.SugarController;
import cellsociety.grid.Grid;
import cellsociety.grid.TriangularGrid;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GraphicsTwoView extends Graphics{

  private Controller secondControllerType;
  private Controller firstControllerType;

  public GraphicsTwoView(Controller controllerType, Controller secondController,
      ResourceBundle currentResourceBundle, String language) {
    super(controllerType, currentResourceBundle, language);
    firstControllerType = controllerType;
    secondControllerType = secondController;
  }
  @Override
  public Scene createVisualGrid(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit, Color color) {
    BorderPane outside = new BorderPane();
    this.setOutside(outside);
    super.setOutsideButtons(eventExit, color);

    return setGridView(grid, simulationResource, eventExit);
  }

  public Scene setGridView(Grid grid, Grid secondGrid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit) {
    Pane currentPane;
    BorderPane outside = getOutside();
    if (grid instanceof TriangularGrid) {
      currentPane = addTriangularGrid(grid);
      outside.setCenter(currentPane);
      outside.setRight(addTriangularGrid(grid));
    }
    else  {
      currentPane = addRectangularGrid(grid);
      outside.setCenter(currentPane);
      GridPane pane2 = addRectangularGrid(secondGrid);
      outside.setRight(pane2);
      if (firstControllerType instanceof AntController || secondControllerType instanceof SugarController)  {
        addOverlayedCells(grid, (GridPane) currentPane);
        addOverlayedCells(secondGrid, pane2);
      }
    }
    return getScene();
  }
}
