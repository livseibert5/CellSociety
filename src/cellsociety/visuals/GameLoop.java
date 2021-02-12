package cellsociety.visuals;

import cellsociety.grid.XMLParser;
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



public class GameLoop extends Application {
    String filename;
    private Graphics visuals = new Graphics();
    //private XMLParser = new XMLParser(fileName);
    private Scene myScene;
    private static final String TITLE = "Cellular Automata";

    private void step(double elapsedTime){

    }

//the controller will be cal
    private void updateGameState(){

    }

    private void keyOrMouseInput(){

    }

    private void ifButtonIsClicked(Scene scene){

    }

//    private void parseFile(){
//        XMLParser parser = new XMLParser();
//    }

    public void setScene(Scene scene){
        myScene = scene;
    }
    //when a button is clicked then
    @Override
    public void start(Stage stage){
        myScene = visuals.creatingLandingScreen();
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
    }
}
