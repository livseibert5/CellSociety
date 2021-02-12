package cellsociety.visuals;

import cellsociety.cells.Cell;
import cellsociety.grid.Grid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ResourceBundle;


public class Graphics {

    public static final String FONT = "Verdana";
    private static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final Paint BACKGROUND = Color.AZURE;
    public GridPane gridView;
    private Color[] colorOfCell = {Color.YELLOW, Color.GREEN, Color.RED};
    private int SQUARE_DIMENSIONS = 30;
    public boolean ifSimulationButtonHit = false;
    public static final String LANDING_SCREEN_PACKAGE = "cellsociety.visuals.resources.LandingScene";
    public static final String FIRE_SCREEN_PACKAGE = "cellsociety.visuals.resources.FireSimulation";

    public static final String DEFAULT_RESOURCE_FOLDER = "/" + LANDING_SCREEN_PACKAGE.replace(".", "/");
    ResourceBundle myLandingSceneResources;
    ResourceBundle myFireSimulationResources;

    public Graphics(){
        gridView = new GridPane();
        myLandingSceneResources = ResourceBundle.getBundle(LANDING_SCREEN_PACKAGE);
        myFireSimulationResources = ResourceBundle.getBundle(FIRE_SCREEN_PACKAGE);
    }

    private String getFileName(String fileName){
        return fileName;
    }

    public Scene createFireGrid(Grid grid){
        int[] sizeOfGrid = grid.getSizeOfGrid();
        int width = sizeOfGrid[0];
        int length = sizeOfGrid[1];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
               Cell cell = grid.getCellAtLocation(i, j);
                if(cell.getState() == 0){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, Color.valueOf(myFireSimulationResources.getString("0"))), i, j);
                }else if(cell.getState() == 1){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS,  Color.valueOf(myFireSimulationResources.getString("1"))), i, j);
                }else if(cell.getState() == 2){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, Color.valueOf(myFireSimulationResources.getString("2"))), i, j);
                }
            }
        }

        Scene scene = new Scene(gridView);
        return scene;
    }

    public void setIfSimulationButtonHit(){
        ifSimulationButtonHit = !ifSimulationButtonHit;
        System.out.println("simulation" + ifSimulationButtonHit);
    }

    public boolean getSimulationButtonStatus(){
        return ifSimulationButtonHit;
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
        double yPosition = baseY + 150;

        button.setTranslateY(yPosition);
        button.setTranslateX(xPosition);
        root.getChildren().add(button);

        button.setOnAction(event);
    }

}


