package cellsociety.visuals;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Graphics {

    Group root;
    public static int height = 800;
    public static int width = 800;
    public static String font = "Verdana";

//    public Group getCurrentDisplay(Grid grid){
//
//    }

    public Group createLandingScreen(){
        Text welcome = new Text(75, 100, "Simulation Menu");
        root.getChildren().add(welcome);
        Text instructions = new Text(75, 150, "click on any simulation to start");

        return root;

    }
}
