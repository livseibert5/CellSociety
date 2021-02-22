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

public class GraphicsTwoView extends Graphics {

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
      EventHandler<ActionEvent> eventExit, boolean firstSimulation, boolean secondSimulation) {
    if (firstSimulation) {
      super.setGridView(grid, simulationResource, eventExit);
    }
    BorderPane outside = super.getOutside();
    if (secondSimulation) {
      return setPane2(secondGrid, outside);
    }
    return getScene();
  }

  private Scene setPane2(Grid secondGrid, BorderPane outside) {
    Pane pane2;
    if (secondGrid instanceof TriangularGrid) {
      pane2 = addTriangularGrid(secondGrid);
      outside.setRight(pane2);
    } else {
      pane2 = addRectangularGrid(secondGrid);
      outside.setRight(pane2);
      if (firstControllerType instanceof AntController
          || secondControllerType instanceof SugarController) {
        addOverlayedCells(secondGrid, (GridPane) pane2);
      }
    }
    return getScene();
  }

}
