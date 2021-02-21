package cellsociety.visuals;

import cellsociety.cells.Cell;
import cellsociety.cells.ForagerCell;
import cellsociety.cells.SugarCell;
import cellsociety.controller.AntController;
import cellsociety.controller.Controller;
import cellsociety.controller.SugarController;
import cellsociety.grid.Grid;
import cellsociety.grid.TriangularGrid;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Graphics {

  public static final String FONT = "Verdana";
  public static final int SCREEN_WIDTH = 400;
  public static final int SCREEN_HEIGHT = 500;
  private static final int SQUARE_DIMENSIONS = 30;

  //properties package
  public static final String LANDING_SCREEN_PACKAGE = "cellsociety.visuals.resources.LandingScene";
  public static final String FIRE_SCREEN_PACKAGE = "cellsociety.visuals.resources.FireSimulation";
  public static final String GAME_OF_LIFE_SCREEN_PACKAGE = "cellsociety.visuals.resources.GameOfLifeSimulation";
  public static final String PERCOLATION_PACKAGE = "cellsociety.visuals.resources.PercolationSimulation";
  public static final String SEGREGATION_PACKAGE = "cellsociety.visuals.resources.SegregationSimulation";
  public static final String WATOR_PACKAGE = "cellsociety.visuals.resources.WaTorSimulation";
  public static final String ANT_PACKAGE = "cellsociety.visuals.resources.AntSimulation";
  public static final String SUGAR_SIMULATION = "cellsociety.visuals.resources.SugarSimulation";
  public static final String COLOR_RESOURCE_BUNDLE = "cellsociety.visuals.resources.Color";
  //resource bundles for each simulation
  public static final ResourceBundle myLandingSceneResources = ResourceBundle
      .getBundle(LANDING_SCREEN_PACKAGE);
  public static final ResourceBundle myFireSimulationResources = ResourceBundle
      .getBundle(FIRE_SCREEN_PACKAGE);
  public static final ResourceBundle myGameOfLifeSimulationResources = ResourceBundle
      .getBundle(GAME_OF_LIFE_SCREEN_PACKAGE);
  public static final ResourceBundle myPercolationSimulationResources = ResourceBundle
      .getBundle(PERCOLATION_PACKAGE);
  public static final ResourceBundle mySegregationSimulationResources = ResourceBundle
      .getBundle(SEGREGATION_PACKAGE);
  public static final ResourceBundle myWaTorSimulationResources = ResourceBundle
      .getBundle(WATOR_PACKAGE);
  public static final ResourceBundle myAntSimulation = ResourceBundle
          .getBundle(ANT_PACKAGE);
  public static final ResourceBundle mySugarSimulation = ResourceBundle
          .getBundle(SUGAR_SIMULATION);
  public static final ResourceBundle colorResourceBundle = ResourceBundle.getBundle(COLOR_RESOURCE_BUNDLE);

  private ResourceBundle languageResourceBundle;

  public static final Button exit = new Button();
  public static final Button exitSecondLandingScreen = new Button();
  public static final Button faster = new Button();
  public static final Button slower = new Button();
  public static final Button normal = new Button();
  public static final Button play = new Button();
  public static final Button pause = new Button();
  public static final Button downloadXMLFile = new Button();

  private BorderPane outside;
  private Scene scene;

  private Controller controllerType;
  private HashMap<Integer, String> stateColor;

  public Graphics(Controller controllerType, ResourceBundle currentResourceBundle, String language) {
    this.controllerType = controllerType;
    this.stateColor = new HashMap<>();
    int amountOfStates = Integer.parseInt(currentResourceBundle.getString("amountOfStates"));
    for (int i =0 ; i < amountOfStates; i++){
      String color = currentResourceBundle.getString("" + i);
      stateColor.put(i, color);
    }
    this.languageResourceBundle = ResourceBundle
        .getBundle("cellsociety.visuals.resources."+language + "Buttons");
    setButtonText();
  }

  private void setButtonText()  {
    exit.setText(languageResourceBundle.getString("Exit"));
    exitSecondLandingScreen.setText(languageResourceBundle.getString("Exit"));
    faster.setText(languageResourceBundle.getString("Faster"));
    slower.setText(languageResourceBundle.getString("Slower"));
    normal.setText(languageResourceBundle.getString("Normal"));
    pause.setText(languageResourceBundle.getString("Pause"));
    play.setText(languageResourceBundle.getString("Play"));
    downloadXMLFile.setText(languageResourceBundle.getString("downloadXMLFile"));
  }

  public Scene createVisualGrid(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit) {
    outside = new BorderPane();
    outside.getChildren().clear();

    HBox leftButtons = new HBox(play);
    leftButtons.setAlignment(Pos.BOTTOM_LEFT);
    HBox rightButtons = new HBox(pause);
    rightButtons.setAlignment(Pos.BOTTOM_RIGHT);
    HBox centerButtons = new HBox(exit);
    centerButtons.setAlignment(Pos.BOTTOM_CENTER);
    HBox bottomButtons = new HBox(leftButtons, rightButtons, centerButtons);
    HBox topButtonsLeft = new HBox(downloadXMLFile);
    topButtonsLeft.setAlignment(Pos.TOP_LEFT);
    HBox topButtonsRight = new HBox(faster);
    topButtonsRight.setAlignment(Pos.TOP_RIGHT);
    HBox topButtons = new HBox(topButtonsRight, topButtonsLeft);
    exit.setOnAction(eventExit);
    outside.autosize();
    outside.setBottom(bottomButtons);
    outside.setTop(topButtons);
    outside.setLeft(slower);
    outside.setRight(normal);

    scene = new Scene(outside);

    return setGridView(grid, simulationResource, eventExit);
  }

  public Scene setGridView(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit) {
    GridPane currentPane;
    if (grid instanceof TriangularGrid) {
      addTriangularGrid(grid);
    }
    else  {
      currentPane = addRectangularGrid(grid);
      if (controllerType instanceof AntController || controllerType instanceof SugarController)  {
        addOverlayedCells(grid, currentPane);
      }
    }
    return scene;
  }

  private void addOverlayedCells(Grid grid, GridPane pane)  {
    int[] sizeOfGrid = grid.getSizeOfGrid();
    int width = sizeOfGrid[1];
    int length = sizeOfGrid[0];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        if (grid.getCellAtLocation(i, j) instanceof ForagerCell) {
          ForagerCell cell = (ForagerCell) grid.getCellAtLocation(i, j);
          if (cell.getAnts().size() != 0)   {
            int size = cell.getAnts().size();
            Color circleColor = Color.rgb(Math.min(255, 150 + size),Math.min(255, 150 + size),Math.min(255, 150 + size));
            Circle circle = new Circle(SQUARE_DIMENSIONS /5, circleColor);
            pane.add(circle, j, i);
          }
        } else {
          SugarCell cell = (SugarCell) grid.getCellAtLocation(i, j);
          if (cell.getHasAgent()) {
            Color circleColor = Color.RED;
            Circle circle = new Circle(SQUARE_DIMENSIONS /5, circleColor);
            pane.add(circle, j, i);
          }
        }
      }
    }
  }

  private GridPane addRectangularGrid(Grid grid) {
    GridPane gridView = new GridPane();
    outside.setCenter(gridView);
    int[] sizeOfGrid = grid.getSizeOfGrid();
    int width = sizeOfGrid[1];
    int length = sizeOfGrid[0];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        Cell cell = grid.getCellAtLocation(i, j);
        Color cellColor;
        if (cell.determineNewColorOfCell() == null) {
          cellColor = Color.valueOf(stateColor.get(cell.getState()));
        }
        else  {
          Double[] cellColors = cell.determineNewColorOfCell();
          cellColor = Color.rgb(cellColors[0].intValue(), cellColors[1].intValue(), cellColors[2].intValue());
        }
        gridView.add(createRectangleAtLocation(SQUARE_DIMENSIONS,SQUARE_DIMENSIONS,cellColor), j, i);
      }
    }
    return gridView;
  }

  /**
   * https://stackoverflow.com/questions/54165602/create-hexagonal-field-with-javafx
   * @param grid of triangle cells
   */
  private AnchorPane addTriangularGrid(Grid grid)  {
    AnchorPane tileMap = new AnchorPane();
    outside.setCenter(tileMap);
    TriangularGrid triangleGrid = (TriangularGrid) grid;
    int[] sizeOfGrid = grid.getSizeOfGrid();
    int width = sizeOfGrid[1];
    int length = sizeOfGrid[0];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        Cell cell = triangleGrid.getCellAtLocation(i, j);
        String cellColor = stateColor.get(cell.getState());
        List<Double> cellCoordinates = triangleGrid.getCellCoordinatesRelativeToOrigin(i,j,SQUARE_DIMENSIONS,SQUARE_DIMENSIONS);
        Polygon currentTriangle = new Polygon();
        currentTriangle.getPoints().addAll(cellCoordinates);
        currentTriangle.setFill(Color.valueOf(cellColor));
        tileMap.getChildren().add(currentTriangle);
      }
    }
    return tileMap;
  }

  private Rectangle createRectangleAtLocation(int width, int height, Color color)  {
    return new Rectangle(width, height, color);
  }


  public static Text constructText(double baseY, int size, String message, FontWeight fontWeight,
                            String font) {
    Text text = new Text(75, 100, message);
    Font textFont = Font.font(font, fontWeight, size);
    text.setFont(textFont);

    Bounds textBounds = text.getBoundsInParent();
    double ascent = -textBounds.getMinY();
    double width = textBounds.getWidth();

    double leftX = 0;
           // (SCREEN_WIDTH - width) / 2;
    double topY = baseY;
    text.relocate(leftX, topY);

    return text;
  }


  public Grid updateGrid(Controller controllerType) {
    controllerType.updateState();
    return controllerType.getNewGrid();
  }

  public static void createButton(String buttonName, double baseY, VBox root,
                           EventHandler<ActionEvent> event) {
    Button button = new Button(buttonName);

    double xPosition = 50;
    double yPosition = baseY + 100;

    button.setTranslateY(yPosition);
    button.setTranslateX(xPosition);
    root.getChildren().add(button);
    button.setOnAction(event);
  }


}


