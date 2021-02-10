package cellsociety.visuals;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameLoop extends Application {
    private Graphics visuals = new Graphics();

    private void step(double elapsedTime){


    }

    private void updateGameState(){

    }

    private void keyOrMouseInput(){

    }

//    private void parseFile(){
//        XMLParser parser = new XMLParser();
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = visuals.createLandingScreen();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
