package cellsociety.visuals;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameLoop extends Application {
    private Graphics visuals = new Graphics();

    private Scene myScene;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;
    public static final Paint BACKGROUND = Color.AZURE;
    private static final String TITLE = "Cellular Automata";

    private void step(double elapsedTime){
    }

    private Scene creatingLandingScreen(int width, int height, Paint background){
        Group root = new Group();
        Text welcome = new Text(75, 100, "Simulation Menu");
        Text instructions = new Text(75, 150, "click on any simulation to start");

        root.getChildren().add(welcome);
        root.getChildren().add(instructions);

        Scene scene = new Scene(root, width, height, background);

        return scene;
    }

    private void updateGameState(){

    }

    private void keyOrMouseInput(){

    }

//    private void parseFile(){
//        XMLParser parser = new XMLParser();
//    }

    @Override
    public void start(Stage stage){
        myScene = creatingLandingScreen(WIDTH, HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.show();
    }
}
