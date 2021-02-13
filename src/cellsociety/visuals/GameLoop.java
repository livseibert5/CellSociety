package cellsociety.visuals;

import cellsociety.controller.Controller;
import cellsociety.grid.Grid;
import cellsociety.grid.XMLParser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ResourceBundle;


public class GameLoop extends Application {

    private Graphics visuals = new Graphics();
    private Scene myScene;
    private static final String TITLE = "Cellular Automata";
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public Stage myStage;
    private ResourceBundle currentResourceBundle;
    private Controller currentControllerType;

    private void step(double elapsedTime) throws IOException, SAXException, ParserConfigurationException {
    }

    public Scene creatingLandingScreen(){
        Group root = new Group();
        Text welcome = visuals.constructText(20, 30, "Simulation Menu", FontWeight.BOLD, visuals.FONT);
        Text instructions = visuals.constructText(40, 15,
                "click on any simulation to start", FontWeight.NORMAL, visuals.FONT);

        visuals.createButton("Game of Life", 100, root, event -> {
            try {
                setGrid("gameoflifepenta.xml", visuals.myGameOfLifeSimulationResources);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton("Percolation", 140, root, event -> {
            try {
                setGrid("percolation1.xml", visuals.myPercolationSimulationResources);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton("Segregation", 180, root, event -> {
            try {
                setGrid("segregation.xml", visuals.mySegregationSimulationResources);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton("Wa-Tor", 220, root, event -> {
            try {
                setGrid("predatorprey1.xml", visuals.myWaTorSimulationResources);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton("Fire", 260, root, event -> {
            try {
                setGrid("firestandard.xml", visuals.myFireSimulationResources);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        root.getChildren().add(welcome);
        root.getChildren().add(instructions);

        Scene scene = new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT, visuals.BACKGROUND);

        return scene;
    }

    public void setExitButtonToLandingScreen(){
        System.out.println("button is hit");
        myStage.setScene(creatingLandingScreen());
    }

    private void setGrid(String filename, ResourceBundle resourceBundle) throws IOException, SAXException, ParserConfigurationException {
        XMLParser parse = new XMLParser(filename);
        parse.readFile();
        Grid grid = parse.getGrid();
        Scene scene = visuals.createGrid(grid, resourceBundle, event -> setExitButtonToLandingScreen());
        myStage.setScene(scene);
    }

    private void setNewGrid(ResourceBundle resourceBundle, Controller controller, EventHandler<ActionEvent> event) throws IOException, SAXException, ParserConfigurationException {
        Scene scene = visuals.updateGrid(controller, resourceBundle, event);
        myStage.setScene(scene);
    }

    private void keyOrMouseInput(){

    }

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
