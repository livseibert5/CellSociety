package cellsociety.visuals;

import cellsociety.controller.*;
import cellsociety.grid.Grid;
import cellsociety.grid.XMLParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ResourceBundle;


public class GameLoop extends Application {

    private final Graphics visuals = new Graphics();
    private Scene myScene;
    private static final String TITLE = "Cellular Automata";
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public Stage myStage;
    private ResourceBundle currentResourceBundle;
    private Controller currentControllerType;
    private boolean simulationStarted = false;
    private int time = 0;
    private int mod = 60;
    private String configFileName = "";
    private String resourceBundleName = "";
    private void step(double elapsedTime) throws IOException, SAXException, ParserConfigurationException {
        time += 1;
        if(simulationStarted && mod != 0 && (time % mod == 0)) {
            setNewGrid(currentResourceBundle, currentControllerType,
                event -> setExitButtonToLandingScreen());
            checkSimulationEnded();
            currentControllerType.resetController();
            System.out.println("simulation state: " + simulationStarted);
        }
    }

    private void checkSimulationEnded() {
        if (currentControllerType.simulationEnded()) simulationStarted = false;
    }

    public Scene creatingLandingScreen(){
        simulationStarted = false;
        Group root = new Group();
        Text welcome = visuals.constructText(20, 30, "Simulation Menu", FontWeight.BOLD, visuals.FONT);
        Text instructions = visuals.constructText(40, 15,
                "click on any simulation to start", FontWeight.NORMAL, visuals.FONT);

        visuals.createButton(Graphics.myLandingSceneResources.getString("GameOfLifeSimulation"), 100, root, event -> {
            try {
                currentResourceBundle = Graphics.myGameOfLifeSimulationResources;
                createSecondLandingScreen();

            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton(Graphics.myLandingSceneResources.getString("PercolationSimulation"), 140, root, event -> {
            try {
                currentResourceBundle = Graphics.myPercolationSimulationResources;
                Grid grid = setGrid("percolation1.xml");
                currentControllerType = new PercolationController(grid);
                simulationStarted = true;
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton(Graphics.myLandingSceneResources.getString("SegregationSimulation"), 180, root, event -> {
            try {
                currentResourceBundle = Graphics.mySegregationSimulationResources;
                Grid grid = setGrid("segregation2.xml");
                currentControllerType = new SegregationController(grid);
                simulationStarted = true;
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton(Graphics.myLandingSceneResources.getString("WaTorSimulation"), 220, root, event -> {
            try {
                currentResourceBundle = Graphics.myWaTorSimulationResources;
                Grid grid = setGrid("predatorprey4.xml");
                currentControllerType = new WatorController(grid);
                simulationStarted = true;
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        visuals.createButton(Graphics.myLandingSceneResources.getString("FireSimulation"), 260, root, event -> {
            try {
                currentResourceBundle = Graphics.myFireSimulationResources;
                createSecondLandingScreen();

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
        myStage.setScene(creatingLandingScreen());
    }

    public void setModToFaster(){
        mod = 30;
    }

    public void setModToSlower(){
        mod = 120;
    }

    public void setModToNormal(){
        mod = 60;
    }

    public void stopAnimation(){
        simulationStarted = false;
    }

    public void playAnimation(){
        simulationStarted = true;
    }

    private Grid setGrid(String filename) throws IOException, SAXException, ParserConfigurationException {

        XMLParser parse = new XMLParser(filename);
        parse.readFile();
        Grid grid = parse.getGrid();
        myScene = visuals.createVisualGrid(grid, currentResourceBundle, event -> setExitButtonToLandingScreen());

        Graphics.faster.setOnAction(event -> setModToFaster());
        Graphics.slower.setOnAction(event -> setModToSlower());
        Graphics.normal.setOnAction(event -> setModToNormal());
        Graphics.play.setOnAction(event -> playAnimation());
        Graphics.pause.setOnAction(event -> stopAnimation());


        myStage.setScene(myScene);
        return grid;
    }

    private void setNewGrid(ResourceBundle resourceBundle, Controller controller, EventHandler<ActionEvent> event) throws IOException, SAXException, ParserConfigurationException {
        Grid grid = visuals.updateGrid(controller);
        Scene scene = visuals.setGridView(grid, currentResourceBundle, event);
        myStage.setScene(scene);
    }

    public Grid setSpecifcConfigfile(String fileName) throws ParserConfigurationException, SAXException, IOException {
        fileName = "file" + fileName;
        String configFileName = currentResourceBundle.getString(fileName);
        Grid grid = setGrid(configFileName);
        return grid;
    }

    public void createSecondLandingScreen() throws IOException, SAXException, ParserConfigurationException {

        Button one = new Button(currentResourceBundle.getString("one"));
        Button two = new Button(currentResourceBundle.getString("two"));
        Button three = new Button(currentResourceBundle.getString("three"));
        Button four = new Button(currentResourceBundle.getString("four"));
        Button five = new Button(currentResourceBundle.getString("five"));
        Button six = new Button(currentResourceBundle.getString("six"));

        one.setTranslateX(10);
        one.setTranslateY(100);

        two.setTranslateX(10);
        two.setTranslateY(150);

        three.setTranslateX(10);
        three.setTranslateY(200);

        four.setTranslateX(10);
        four.setTranslateY(250);

        five.setTranslateX(10);
        five.setTranslateY(300);

        six.setTranslateX(10);
        six.setTranslateY(350);

        Group root = new Group();
        root.getChildren().addAll(one, two, three, four, five, six);

        one.setOnAction(event -> {
                    try {
                       Grid grid = setSpecifcConfigfile("one");
                        currentControllerType = new GameOfLifeController(grid);
                        simulationStarted = true;
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        two.setOnAction(event -> {
            try {
                Grid grid = setSpecifcConfigfile("two");
                currentControllerType = new GameOfLifeController(grid);
                simulationStarted = true;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        three.setOnAction(event -> {
            try {
                Grid grid = setSpecifcConfigfile("three");
                currentControllerType = new GameOfLifeController(grid);
                simulationStarted = true;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        four.setOnAction(event -> {
            try {
                Grid grid = setSpecifcConfigfile("four");
                currentControllerType = new GameOfLifeController(grid);
                simulationStarted = true;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        five.setOnAction(event -> {
            try {
                Grid grid = setSpecifcConfigfile("five");
                currentControllerType = new GameOfLifeController(grid);
                simulationStarted = true;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        six.setOnAction(event -> {
            try {
                Grid grid = setSpecifcConfigfile("six");
                currentControllerType = new GameOfLifeController(grid);
                simulationStarted = true;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        myScene = new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT, visuals.BACKGROUND);
        myStage.setScene(myScene);
    }

    @Override
    public void start(Stage stage) throws IOException, SAXException, ParserConfigurationException {

        Scene scene = creatingLandingScreen();
        myScene = scene;
        myStage = stage;
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }
}
