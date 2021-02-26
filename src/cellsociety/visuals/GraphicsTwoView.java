package cellsociety.visuals;

import cellsociety.controller.AntController;
import cellsociety.controller.Controller;
import cellsociety.controller.SugarController;
import cellsociety.grid.Grid;
import cellsociety.grid.TriangularGrid;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author billyluqiu
 * Purpose, creates graphics class that allows for two side by side views of the same simulation
 * Assumptions: Two valid controllers of the same type will be passed in to the class
 * Dependencies JavaFX and Controller, Grid classes
 */
public class GraphicsTwoView extends Graphics {

  private Controller secondControllerType;
  private Controller firstControllerType;

  /**
   * Constructor for class that sets controllers and calls the super
   * @param controllerType First controller
   * @param secondController Second controller
   * @param currentResourceBundle Bundle for controller type
   * @param language of simulation
   */

  public GraphicsTwoView(Controller controllerType, Controller secondController,
      ResourceBundle currentResourceBundle, String language) {
    super(controllerType, currentResourceBundle, language);
    firstControllerType = controllerType;
    secondControllerType = secondController;
  }

  /**
   * Called once at beginning of run time to add grid
   *
   * @param grid               adds the grid with the specific cells.
   * @param simulationResource chooses the resource bundle specific to the simulation chosen.
   * @param eventExit          action that makes the scene go back to the landing scene.
   * @param color              chooses background color
   * @return
   */
  @Override
  public Scene createVisualGrid(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit, Color color) {
    BorderPane outside = new BorderPane();
    this.setOutside(outside);
    super.setOutsideButtons(eventExit, color);

    return setGridView(grid, simulationResource, eventExit);
  }

  /**
   * Updates the grids of the simuation if they're running
   * @param grid First grid of the object
   * @param secondGrid Second grid to update
   * @param simulationResource Resource bundle of colors
   * @param eventExit EventHandler to decide if simulation stopped or not
   * @param firstSimulation checks if first simulation running or not
   * @param secondSimulation checks to see if second simulation running
   * @return
   */
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
