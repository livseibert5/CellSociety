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
import java.util.Map;

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

  private Map<String, String> simulationData;

  private void step(double elapsedTime)
      throws IOException, SAXException, ParserConfigurationException {
    time += 1;
    if (simulationStarted && mod != 0 && (time % mod == 0)) {
      setNewGrid(currentResourceBundle, currentControllerType,
          event -> setExitButtonToLandingScreen());
      checkSimulationEnded();
      currentControllerType.resetController();
    }
  }

  private void checkSimulationEnded() {
      if (currentControllerType.simulationEnded()) {
          simulationStarted = false;
      }
  }

  public Scene creatingLandingScreen() {
    simulationStarted = false;
    Group root = new Group();
    Text welcome = visuals.constructText(20, 30, "Simulation Menu", FontWeight.BOLD, Graphics.FONT);
    Text instructions = visuals.constructText(40, 15,
        "click on any simulation to start", FontWeight.NORMAL, Graphics.FONT);

    visuals
        .createButton(Graphics.myLandingSceneResources.getString("GameOfLifeSimulation"), 100, root,
            event -> {
              try {

                currentControllerType = new GameOfLifeController();
                currentResourceBundle = Graphics.myGameOfLifeSimulationResources;
                createSecondLandingScreen();

              } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
              }
            });

    visuals.createButton(Graphics.myLandingSceneResources.getString("PercolationSimulation"), 140,
        root, event -> {
          try {

            currentControllerType = new PercolationController();
            currentResourceBundle = Graphics.myPercolationSimulationResources;
            createSecondLandingScreen();
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("SegregationSimulation"), 180,
        root, event -> {
          try {
            currentControllerType = new SegregationController();
            currentResourceBundle = Graphics.mySegregationSimulationResources;
            createSecondLandingScreen();
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("WaTorSimulation"), 220, root,
        event -> {
          try {
            currentControllerType = new WatorController();
            currentResourceBundle = Graphics.myWaTorSimulationResources;
            createSecondLandingScreen();
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("FireSimulation"), 260, root,
        event -> {
          try {
            currentControllerType = new FireController();
            currentResourceBundle = Graphics.myFireSimulationResources;
            createSecondLandingScreen();

          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    root.getChildren().add(welcome);
    root.getChildren().add(instructions);

    return new Scene(root, Graphics.SCREEN_WIDTH, Graphics.SCREEN_HEIGHT, Graphics.BACKGROUND);

  }

  public void setExitButtonToLandingScreen() {
    myStage.setScene(creatingLandingScreen());
  }

  private void setMod(int mod) {
    this.mod = mod;
  }

  private Grid setGrid(String filename)
      throws IOException, SAXException, ParserConfigurationException {

    XMLParser parse = new XMLParser(filename);
    parse.readFile();
    simulationData = parse.getInfo();
    Grid grid = parse.getGrid();
    myScene = visuals
        .createVisualGrid(grid, currentResourceBundle, event -> setExitButtonToLandingScreen());

    Graphics.faster.setOnAction(event -> setMod(30));
    Graphics.slower.setOnAction(event -> setMod(120));
    Graphics.normal.setOnAction(event -> setMod(60));
    Graphics.play.setOnAction(event -> simulationStarted = true);
    Graphics.pause.setOnAction(event -> simulationStarted = false);

    myStage.setScene(myScene);
    return grid;
  }

  private void setNewGrid(ResourceBundle resourceBundle, Controller controller,
      EventHandler<ActionEvent> event) {
    Grid grid = visuals.updateGrid(controller);
    Scene scene = visuals.setGridView(grid, currentResourceBundle, event);
    myStage.setScene(scene);
  }

  public Grid setSpecifcConfigfile(String fileName)
      throws ParserConfigurationException, SAXException, IOException {
    fileName = "file" + fileName;
    String configFileName = currentResourceBundle.getString(fileName);
    return setGrid(configFileName);
  }


  public void createSecondLandingScreen()
      throws IOException, SAXException, ParserConfigurationException {

    Button one = new Button(currentResourceBundle.getString("one"));
    Button two = new Button(currentResourceBundle.getString("two"));
    Button three = new Button(currentResourceBundle.getString("three"));
    Button four = new Button(currentResourceBundle.getString("four"));
    Button five = new Button(currentResourceBundle.getString("five"));
    Button six = new Button(currentResourceBundle.getString("six"));

    changeButtonLayout(one, two, three, four, five, six);

    Group root = new Group();
    root.getChildren().addAll(one, two, three, four, five, six);
    root.getChildren().add(Graphics.exitSecondLandingScreen);
    Graphics.exitSecondLandingScreen.setTranslateY(350);
    Graphics.exitSecondLandingScreen.setTranslateX(10);
    Graphics.exitSecondLandingScreen.setOnAction(event -> {
      myScene = creatingLandingScreen();
      myStage.setScene(myScene);
    });
    one.setOnAction(event -> {
      try {
        Grid grid = setSpecifcConfigfile("one");
        currentControllerType.setInitialGrid(grid);
        simulationStarted = true;
      } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
      }
    });

    two.setOnAction(event -> {
      try {
        Grid grid = setSpecifcConfigfile("two");
        currentControllerType.setInitialGrid(grid);
        simulationStarted = true;
      } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
      }
    });

    three.setOnAction(event -> {
      try {
        Grid grid = setSpecifcConfigfile("three");
        currentControllerType.setInitialGrid(grid);
        simulationStarted = true;
      } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
      }
    });

    four.setOnAction(event -> {
      try {
        Grid grid = setSpecifcConfigfile("four");
        currentControllerType.setInitialGrid(grid);
        simulationStarted = true;
      } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
      }
    });

    five.setOnAction(event -> {
      try {
        Grid grid = setSpecifcConfigfile("five");
        currentControllerType.setInitialGrid(grid);
        simulationStarted = true;
      } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
      }
    });

    six.setOnAction(event -> {
      try {
        Grid grid = setSpecifcConfigfile("six");
        currentControllerType.setInitialGrid(grid);
        simulationStarted = true;
      } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
      }
    });

    myScene = new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT, visuals.BACKGROUND);
    myStage.setScene(myScene);
  }

  private void changeButtonLayout(Button one, Button two, Button three, Button four, Button five,
      Button six) {
    setButtonLocation(one, 10, 50);
    setButtonLocation(two, 10, 100);
    setButtonLocation(three, 10, 150);
    setButtonLocation(four, 10, 200);
    setButtonLocation(five, 10, 250);
    setButtonLocation(six, 10, 300);
  }

  private void setButtonLocation(Button button, double x, double y) {
    button.setTranslateX(x);
    button.setTranslateY(y);
  }

  @Override
  public void start(Stage stage) throws IOException, SAXException, ParserConfigurationException {

    myScene = creatingLandingScreen();
    myStage = stage;
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();

    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
      try {
        step(SECOND_DELAY);
      } catch (IOException | ParserConfigurationException | SAXException ioException) {
        ioException.printStackTrace();
      }
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }
}
