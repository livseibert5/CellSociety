package cellsociety.visuals;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Graphics {

    private static final String FONT = "Verdana";
    private static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final Paint BACKGROUND = Color.AZURE;

//    public Group getCurrentDisplay(Grid grid){
//
//    }

    public Scene creatingLandingScreen(){
        Group root = new Group();
        Text welcome = constructText(100, 36, "Simulation Menu", FontWeight.BOLD, FONT);
        Text instructions = constructText(125, 15,
                                    "click on any simulation to start", FontWeight.NORMAL, FONT);
//        createButton("Game of Life", 100, root, event -> updateScene());
//        createButton("Percolation",140, root, event -> updateScene());
//        createButton("Segregation", 180, root, event -> updateScene());
//        createButton("Wa-tor", 220, root, event -> updateScene());
//        createButton("Fire", 260, root, event -> updateScene());

        root.getChildren().add(welcome);
        root.getChildren().add(instructions);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);

        return scene;
    }

//    public Scene createFireScene(){
//        return scene;
//    }
//
//    public Scene createWarTorScene(){
//        return n
//    }


//    public void updateScene(Scene scene){
//        stageScene = scene;
//    }
//
//    public Scene getScene(){
//        return stageScene;
//    }

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

    public void createButton(String buttonName, double baseY, Group root, EventHandler<ActionEvent> handler){
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

        button.setOnAction(handler);
    }

//    public Scene eventButton(){
//
//    }

}


