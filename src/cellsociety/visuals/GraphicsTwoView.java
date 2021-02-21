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

  @Override
  public Scene setGridView(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit) {
    GridPane currentPane;
    if (grid instanceof TriangularGrid) {
      addTriangularGrid(grid);
    }
    else  {
      currentPane = addRectangularGrid(grid);
      if (firstControllerType instanceof AntController || firstControllerType instanceof SugarController)  {
        addOverlayedCells(grid, currentPane);
      }
    }
    return getScene();
  }
}
