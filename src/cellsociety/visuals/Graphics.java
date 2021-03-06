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
import javafx.geometry.Insets;
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

  //static file components
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
  public static final ResourceBundle colorResourceBundle = ResourceBundle
      .getBundle(COLOR_RESOURCE_BUNDLE);

  private ResourceBundle languageResourceBundle;

  //buttons in the grid scene
  public static final Button exit = new Button();
  public static final Button exitSecondLandingScreen = new Button();
  public static final Button faster = new Button();
  public static final Button slower = new Button();
  public static final Button normal = new Button();
  public static final Button play = new Button();
  public static final Button pause = new Button();
  public static final Button downloadXMLFile = new Button();
  //scene layout
  private BorderPane outside;
  private Scene scene;
  //chooseController type
  private Controller controllerType;
  //sets the cell color depending on what simulation is running
  private HashMap<Integer, String> stateColor;

  /**
   * Graphics class contructor. initializes the controller type and the state color Hashmap. also
   * initialized the amount of states in a cell depending on the simulation that is chosen.
   *
   * @param controllerType        chooses what controller depending on the simulation.
   * @param currentResourceBundle choose the resource bundle depending on the simulation.
   * @param language              chooses the language depending on what the user chooses (english,
   *                              spanish, or french).
   */
  public Graphics(Controller controllerType, ResourceBundle currentResourceBundle,
      String language) {
    this.controllerType = controllerType;
    this.stateColor = new HashMap<>();
    int amountOfStates = Integer.parseInt(currentResourceBundle.getString("amountOfStates"));
    for (int i = 0; i < amountOfStates; i++) {
      String color = currentResourceBundle.getString("" + i);
      stateColor.put(i, color);
    }
    this.languageResourceBundle = ResourceBundle
        .getBundle("cellsociety.visuals.resources." + language + "Buttons");
    setButtonText();
  }

  //set button titles
  private void setButtonText() {
    exit.setText(languageResourceBundle.getString("Exit"));
    exitSecondLandingScreen.setText(languageResourceBundle.getString("Exit"));
    faster.setText(languageResourceBundle.getString("Faster"));
    slower.setText(languageResourceBundle.getString("Slower"));
    normal.setText(languageResourceBundle.getString("Normal"));
    pause.setText(languageResourceBundle.getString("Pause"));
    play.setText(languageResourceBundle.getString("Play"));
    downloadXMLFile.setText(languageResourceBundle.getString("downloadXMLFile"));
  }

  /**
   * creates the visual grid of every simulation
   *
   * @param grid               adds the grid with the specific cells.
   * @param simulationResource chooses the resource bundle specific to the simulation chosen.
   * @param eventExit          action that makes the scene go back to the landing scene.
   * @param color              chooses background color
   * @return returns the scene of the simulation chosen.
   */
  public Scene createVisualGrid(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit, Color color) {
    outside = new BorderPane();
    setOutsideButtons(eventExit, color);

    return setGridView(grid, simulationResource, eventExit);
  }

  /**
   * sets the buttons outside of the simulation grid.
   *
   * @param eventExit event that makes the scene go back to the landing scene when the exit button
   *                  is clicked.
   * @param color     chooses the background color
   */
  protected void setOutsideButtons(EventHandler<ActionEvent> eventExit, Color color) {
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
    HBox leftButtonsTop = new HBox(slower);
    leftButtonsTop.setAlignment(Pos.TOP_LEFT);
    HBox leftButtonsBottom = new HBox(normal);
    leftButtonsBottom.setAlignment(Pos.BOTTOM_LEFT);
    HBox leftButtons2 = new HBox(leftButtonsBottom, leftButtonsTop);
    exit.setOnAction(eventExit);
    outside.autosize();
    outside.setBottom(bottomButtons);
    outside.setTop(topButtons);
    outside.setLeft(leftButtons2);
    outside
        .setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    scene = new Scene(outside);
  }

  /**
   * set the grid depending on which simulation is chosen.
   * @param grid               grid created when a simulation is chosen.
   * @param simulationResource resource bundle that correlates with the simulation chosen.
   * @param eventExit          event that makes the scene go back to the landing screen.
   * @return return the scene that is going to be displayed.
   */
  public Scene setGridView(Grid grid, ResourceBundle simulationResource,
      EventHandler<ActionEvent> eventExit) {
    Pane currentPane;
    if (grid instanceof TriangularGrid) {
      currentPane = addTriangularGrid(grid);
      outside.setCenter(currentPane);
    } else {
      currentPane = addRectangularGrid(grid);
      outside.setCenter(currentPane);
      if (controllerType instanceof AntController || controllerType instanceof SugarController) {
        addOverlayedCells(grid, (GridPane) currentPane);
      }
    }
    return scene;
  }

  /**
   * for ant and sugar cells to chaneg the colors depending on the changing states
   *
   * @param grid current grid that is already created
   * @param pane create a new grid pane with different colors
   */
  protected void addOverlayedCells(Grid grid, GridPane pane) {
    int[] sizeOfGrid = grid.getSizeOfGrid();
    int width = sizeOfGrid[1];
    int length = sizeOfGrid[0];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        if (grid.getCellAtLocation(i, j) instanceof ForagerCell) {
          ForagerCell cell = (ForagerCell) grid.getCellAtLocation(i, j);
          if (cell.getAnts().size() != 0) {
            int size = cell.getAnts().size();
            Color circleColor = Color.rgb(Math.min(255, 150 + size), Math.min(255, 150 + size),
                Math.min(255, 150 + size));
            Circle circle = new Circle(SQUARE_DIMENSIONS / 5, circleColor);
            pane.add(circle, j, i);
          }
        } else {
          SugarCell cell = (SugarCell) grid.getCellAtLocation(i, j);
          if (cell.getHasAgent()) {
            Color circleColor = Color.RED;
            Circle circle = new Circle(SQUARE_DIMENSIONS / 5, circleColor);
            pane.add(circle, j, i);
          }
        }
      }
    }
  }

  /**
   * create a rectangular grid if the user chooses rectangular grid.
   *
   * @param grid grid already created depending on the simulation and cell color types.
   * @return return a grid pane with the changing color and new cells.
   */
  protected GridPane addRectangularGrid(Grid grid) {
    GridPane gridView = new GridPane();
    int[] sizeOfGrid = grid.getSizeOfGrid();
    int width = sizeOfGrid[1];
    int length = sizeOfGrid[0];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        Cell cell = grid.getCellAtLocation(i, j);
        Color cellColor;
        if (cell.determineNewColorOfCell() == null) {
          cellColor = Color.valueOf(stateColor.get(cell.getState()));
        } else {
          Double[] cellColors = cell.determineNewColorOfCell();
          cellColor = Color
              .rgb(cellColors[0].intValue(), cellColors[1].intValue(), cellColors[2].intValue());
        }
        gridView
            .add(createRectangleAtLocation(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, cellColor), j, i);
      }
    }
    return gridView;
  }

  /**
   * https://stackoverflow.com/questions/54165602/create-hexagonal-field-with-javafx
   *
   * @param grid of triangle cells
   */
  protected AnchorPane addTriangularGrid(Grid grid) {
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
        List<Double> cellCoordinates = triangleGrid
            .getCellCoordinatesRelativeToOrigin(i, j, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS);
        Polygon currentTriangle = new Polygon();
        currentTriangle.getPoints().addAll(cellCoordinates);
        currentTriangle.setFill(Color.valueOf(cellColor));
        tileMap.getChildren().add(currentTriangle);
      }
    }
    return tileMap;
  }

  /**
   * creates a new rectangle, for rectangular cells.
   *
   * @param width  width of rectangle created
   * @param height height of rectangle created
   * @param color  color of rectangular fill
   * @return return a rectangular shape with specific dimensions and color.
   */
  private Rectangle createRectangleAtLocation(int width, int height, Color color) {
    return new Rectangle(width, height, color);
  }

  /**
   * create a text file
   *
   * @param baseY      how far away from the top of the screen is the text
   * @param size       how big the text is
   * @param message    what the text says
   * @param fontWeight what font weight the text is
   * @param font       what font the text is
   * @return return a text feature with the specific parameters.
   */
  public static Text constructText(double baseY, int size, String message, FontWeight fontWeight,
      String font) {
    Text text = new Text(75, 100, message);
    Font textFont = Font.font(font, fontWeight, size);
    text.setFont(textFont);

    double leftX = 0;
    double topY = baseY;
    text.relocate(leftX, topY);

    return text;
  }

  /**
   * update the cell state, and therefore update the grid
   *
   * @param controllerType tells you what state the cells are in, updatin gbased on the simulation
   *                       parameters
   * @return a new grid with the new cells and states.
   */
  public Grid updateGrid(Controller controllerType) {
    controllerType.updateState();
    return controllerType.getNewGrid();
  }

  /**
   * creates a button
   *
   * @param buttonName the title of the button
   * @param baseY      how far away the button is from the top of the screen
   * @param root       where the button is added to the scene
   * @param event      what the button does when it is clicked on
   */
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

  /**
   * get the borderPane
   *
   * @return borderPane instance variable
   */
  protected BorderPane getOutside() {
    return this.outside;
  }

  /**
   * set borderPane
   *
   * @param outside borderPane
   */
  protected void setOutside(BorderPane outside) {
    this.outside = outside;
  }

  /**
   * get Scene type
   *
   * @return scene instance variable
   */
  protected Scene getScene() {
    return this.scene;
  }
}


