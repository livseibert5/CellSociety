package cellsociety;

import cellsociety.controller.FireController;
import cellsociety.controller.PercolationController;
import cellsociety.grid.XMLParser;
import cellsociety.grid.Grid;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    Grid grid;
    PercolationController fireControl;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private void step(double elapsedTime) {
        fireControl.updateState();
        grid = fireControl.getNewGrid();
        grid.printGrid();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        XMLParser parser = new XMLParser("percolationworstcase.xml");
        try {
            parser.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid = parser.getGrid();
        grid.printGrid();
        fireControl = new PercolationController(grid);
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), event -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    public static void main (String[] args) {
        launch(args);
    }
}

