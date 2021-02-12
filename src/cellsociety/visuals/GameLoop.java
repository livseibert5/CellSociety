package cellsociety.visuals;

import cellsociety.grid.Grid;
import cellsociety.grid.XMLParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class GameLoop extends Application {

    String filename;
    private Graphics visuals = new Graphics();
    //private XMLParser = new XMLParser(fileName);
    private Scene myScene;
    private static final String TITLE = "Cellular Automata";
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public Stage myStage;
    private void step(double elapsedTime) throws IOException, SAXException, ParserConfigurationException {


    }

    public Scene creatingLandingScreen(){
        Group root = new Group();
        Text welcome = visuals.constructText(100, 36, "Simulation Menu", FontWeight.BOLD, visuals.FONT);
        Text instructions = visuals.constructText(125, 15,
                "click on any simulation to start", FontWeight.NORMAL, visuals.FONT);
//        createButton("Game of Life", 100, root);
//        createButton("Percolation",140, root);
//        createButton("Segregation", 180, root);
//        createButton("Wa-tor", 220, root);
        visuals.createButton("Fire", 260, root, event -> {
            try {
                setFireGrid();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        root.getChildren().add(welcome);
        root.getChildren().add(instructions);

        Scene scene = new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT, visuals.BACKGROUND);

        return scene;
    }

//the controller will be cal
    private void updateGameState(){

    }

    private void setFireGrid() throws IOException, SAXException, ParserConfigurationException {
        XMLParser parse = new XMLParser("firestandard.xml");
        parse.readFile();
        Grid grid = parse.getGrid();
        Scene scene = visuals.createFireGrid(grid);
        myStage.setScene(scene);
    }

    private void keyOrMouseInput(){

    }

    private void ifButtonIsClicked(Scene scene){

    }

    public void setScene(Scene scene){
        myScene = scene;
    }
    //when a button is clicked then
    @Override
    public void start(Stage stage) throws IOException, SAXException, ParserConfigurationException {

        Scene scene = creatingLandingScreen();
        myScene = scene;
        myStage = stage;
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

    }
}
