package cellsociety.visuals;

import cellsociety.cells.Cell;
import cellsociety.grid.Grid;
import cellsociety.grid.XMLParser;
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


public class Graphics {

    private static final String FONT = "Verdana";
    private static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final Paint BACKGROUND = Color.AZURE;
    public GridPane gridView;
    private Color[] colorOfCell = {Color.YELLOW, Color.GREEN, Color.RED};
    private int SQUARE_DIMENSIONS = 20;

    public Graphics(){
        gridView = new GridPane();
    }

    public Scene creatingLandingScreen(){
        Group root = new Group();
        Text welcome = constructText(100, 36, "Simulation Menu", FontWeight.BOLD, FONT);
        Text instructions = constructText(125, 15,
                                    "click on any simulation to start", FontWeight.NORMAL, FONT);
        createButton("Game of Life", 100, root);
        createButton("Percolation",140, root);
        createButton("Segregation", 180, root);
        createButton("Wa-tor", 220, root);
        createButton("Fire", 260, root);

        root.getChildren().add(welcome);
        root.getChildren().add(instructions);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);

        return scene;
    }

    public GridPane createFireGrid(Grid grid){
        int[] sizeOfGrid = grid.getSizeOfGrid();
        int width = sizeOfGrid[0];
        int length = sizeOfGrid[1];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
               Cell cell = grid.getCellAtLocation(i, j);
                if(cell.getState() == 0){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, colorOfCell[0]), i, j);
                }else if(cell.getState() == 1){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, colorOfCell[1]), i, j);
                }else if(cell.getState() == 2){
                    gridView.add(new Rectangle(SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, colorOfCell[2]), i, j);
                }
            }
        }
        return gridView;
    }

    private Text constructText(double baseY, int size, String message, FontWeight fontWeight, String font) {
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

    public void createButton(String buttonName, double baseY, Group root){
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
    }

}


