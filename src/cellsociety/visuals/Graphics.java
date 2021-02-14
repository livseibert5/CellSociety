package cellsociety.visuals;

import cellsociety.cells.Cell;
import cellsociety.controller.Controller;
import cellsociety.grid.Grid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ResourceBundle;


public class Graphics {

    public static final String FONT = "Verdana";
    public static final int SCREEN_WIDTH = 400;
    public static final int SCREEN_HEIGHT = 400;
    public static final Paint BACKGROUND = Color.AZURE;
    private int SQUARE_DIMENSIONS = 30;

    //properties package
    public static final String LANDING_SCREEN_PACKAGE = "cellsociety.visuals.resources.LandingScene";
    public static final String FIRE_SCREEN_PACKAGE = "cellsociety.visuals.resources.FireSimulation";
    public static final String GAME_OF_LIFE_SCREEN_PACKAGE = "cellsociety.visuals.resources.GameOfLifeSimulation";
    public static final String PERCOLATION_PACKAGE = "cellsociety.visuals.resources.PercolationSimulation";
    public static final String SEGREGATION_PACKAGE = "cellsociety.visuals.resources.SegregationSimulation";
    public static final String WATOR_PACKAGE = "cellsociety.visuals.resources.WaTorSimulation";

    //resource bundles for each simulation
    public static final ResourceBundle myLandingSceneResources = ResourceBundle.getBundle(LANDING_SCREEN_PACKAGE);
    public static final ResourceBundle myFireSimulationResources = ResourceBundle.getBundle(FIRE_SCREEN_PACKAGE);
    public static final ResourceBundle myGameOfLifeSimulationResources = ResourceBundle.getBundle(GAME_OF_LIFE_SCREEN_PACKAGE);
    public static final ResourceBundle myPercolationSimulationResources = ResourceBundle.getBundle(PERCOLATION_PACKAGE);
    public static final ResourceBundle mySegregationSimulationResources = ResourceBundle.getBundle(SEGREGATION_PACKAGE);
    public static final ResourceBundle myWaTorSimulationResources = ResourceBundle.getBundle(WATOR_PACKAGE);

    private Button exit = new Button("Exit");
    public Button faster = new Button("Faster");
    public Button slower = new Button("Slower");
    public Button normal = new Button("Regular");
    public Button play = new Button("Play");
    public Button pause = new Button("Pause");
    private BorderPane outside;
    private Scene scene;
    public Graphics(){
        exit.setFont(Font.font(FONT, 12));
    }

    public Scene createVisualGrid(Grid grid, ResourceBundle simulationResource, EventHandler<ActionEvent> eventExit){
        outside = new BorderPane();
        outside.getChildren().clear();

        HBox leftButtons = new HBox(play);
        leftButtons.setAlignment(Pos.BOTTOM_LEFT);
        HBox rightButtons = new HBox(pause);
        rightButtons.setAlignment(Pos.BOTTOM_RIGHT);
        HBox centerButtons = new HBox(exit);
        centerButtons.setAlignment(Pos.BOTTOM_CENTER);
        HBox bottomButtons = new HBox(leftButtons, rightButtons, centerButtons);
        exit.setOnAction(eventExit);
        outside.autosize();
        outside.setBottom(bottomButtons);
        outside.setTop(faster);
        outside.setLeft(slower);
        outside.setRight(normal);
        scene = new Scene(outside);
        return setGridView(grid, simulationResource, eventExit);
    }

    public Scene setGridView(Grid grid, ResourceBundle simulationResource, EventHandler<ActionEvent> eventExit) {
        GridPane gridView = new GridPane();
        outside.setCenter(gridView);
        int[] sizeOfGrid = grid.getSizeOfGrid();
        int width = sizeOfGrid[1];
        int length = sizeOfGrid[0];
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
               Cell cell = grid.getCellAtLocation(i, j);
                if(cell.getState() == 0){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, Color.valueOf(
                        simulationResource.getString("0"))), j, i);
                }else if(cell.getState() == 1){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS,  Color.valueOf(
                        simulationResource.getString("1"))), j, i);
                }else if(cell.getState() == 2){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, Color.valueOf(
                        simulationResource.getString("2"))), j, i);
                }
            }
        }
        return scene;
    }

    public Grid updateGrid(Controller controllerType, ResourceBundle simulation, EventHandler<ActionEvent> event){
        controllerType.updateState();
        Grid newGrid = controllerType.getNewGrid();
        return newGrid;
    }

    public Text constructText(double baseY, int size, String message, FontWeight fontWeight, String font) {
        Text text = new Text(75, 100, message);
        Font textFont = Font.font(font, fontWeight, size);
        text.setFont(textFont);

        Bounds textBounds = text.getBoundsInParent();
        double ascent = -textBounds.getMinY();
        double descent = textBounds.getMinY();
        double width = textBounds.getWidth();

        double leftX = (SCREEN_WIDTH-width)/2;
        double topY = baseY - ascent;
        text.relocate(leftX, topY);

        return text;
    }


    public void createButton(String buttonName, double baseY, Group root, EventHandler<ActionEvent> event){
        Button button = new Button(buttonName);

        Bounds buttonBounds = button.getBoundsInParent();
        double ascent = -buttonBounds.getMinY();
        double descent = buttonBounds.getMinY();
        double width = buttonBounds.getWidth();

        double xPosition = ((SCREEN_WIDTH/2)-50);
        double yPosition = baseY + 100;

        button.setTranslateY(yPosition);
        button.setTranslateX(xPosition);
        root.getChildren().add(button);

        button.setOnAction(event);
    }

}


