package cellsociety.visuals;

import cellsociety.grid.Grid;
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
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class GameLoop extends Application {
    String filename;
    private Graphics visuals = new Graphics();
    //private XMLParser = new XMLParser(fileName);
    private Scene myScene;
    private static final String TITLE = "Cellular Automata";
    public XMLParser parse = new XMLParser("firestandard.xml");

    private void step(double elapsedTime){

    }

//the controller will be cal
    private void updateGameState(){

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
        parse.readFile();
        Grid grid = parse.getGrid();
        Scene scene = new Scene(visuals.createFireGrid(grid));
        myScene = scene;
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
    }
}
