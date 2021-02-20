package cellsociety.visuals;

import cellsociety.controller.*;
import cellsociety.grid.Grid;
import cellsociety.grid.GridToXML;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ResourceBundle;


public class GameLoop extends Application {

  private Graphics visuals;
  private Scene myScene;
  private static final String TITLE = "Cellular Automata";
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private Stage myStage;
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
      updateGrid(currentResourceBundle, currentControllerType,
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
    int baseY = 0;
    simulationStarted = false;
    Group root = new Group();
    Text welcome = visuals.constructText(baseY, 30, "Simulation Menu", FontWeight.BOLD, Graphics.FONT);
    Text instructions = visuals.constructText(baseY + 20, 15,
        "click on any simulation to start", FontWeight.NORMAL, Graphics.FONT);

    visuals
        .createButton(Graphics.myLandingSceneResources.getString("GameOfLifeSimulation"), 25, root,
            event -> {
              try {
                currentControllerType = new GameOfLifeController();
                currentResourceBundle = Graphics.myGameOfLifeSimulationResources;
                createSecondLandingScreen(currentControllerType, currentResourceBundle);

              } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
              }
            });

    visuals.createButton(Graphics.myLandingSceneResources.getString("PercolationSimulation"), 65,
        root, event -> {
          try {

            currentControllerType = new PercolationController();
            currentResourceBundle = Graphics.myPercolationSimulationResources;
            createSecondLandingScreen(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("SegregationSimulation"), 105,
        root, event -> {
          try {
            currentControllerType = new SegregationController();
            currentResourceBundle = Graphics.mySegregationSimulationResources;
            createSecondLandingScreen(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("WaTorSimulation"), 145, root,
        event -> {
          try {
            currentControllerType = new WatorController();
            currentResourceBundle = Graphics.myWaTorSimulationResources;
            createSecondLandingScreen(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("FireSimulation"), 185, root,
        event -> {
          try {
            currentControllerType = new FireController();
            currentResourceBundle = Graphics.myFireSimulationResources;
            createSecondLandingScreen(currentControllerType, currentResourceBundle);

          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals
            .createButton(Graphics.myLandingSceneResources.getString("AntSimulation"), 225, root,
                    event -> {
                      try {
                        currentControllerType = new AntController();
                        currentResourceBundle = Graphics.myAntSimulation;
                        createSecondLandingScreen(currentControllerType, currentResourceBundle);

                      } catch (IOException | SAXException | ParserConfigurationException e) {
                        e.printStackTrace();
                      }
                    });

    visuals
            .createButton(Graphics.myLandingSceneResources.getString("SugarSimulation"), 265, root,
                    event -> {
                      try {
                        currentControllerType = new AntController();
                        currentResourceBundle = Graphics.mySugarSimulation;
                        createSecondLandingScreen(currentControllerType, currentResourceBundle);

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

  private Grid setGrid(String filename, Controller controllerType, ResourceBundle simulationType)
      throws IOException, SAXException, ParserConfigurationException {
    XMLParser parse = new XMLParser(filename);
    parse.readFile();
    simulationData = parse.getInfo();
    Grid grid = parse.getGrid();
    visuals = new Graphics(controllerType, simulationType);
    controllerType.setInitialGrid(grid);
    myScene = visuals
        .createVisualGrid(grid, currentResourceBundle, event -> setExitButtonToLandingScreen());

    Graphics.faster.setOnAction(event -> setMod(30));
    Graphics.slower.setOnAction(event -> setMod(120));
    Graphics.normal.setOnAction(event -> setMod(60));
    Graphics.play.setOnAction(event -> simulationStarted = true);
    Graphics.pause.setOnAction(event -> simulationStarted = false);
    Button downloadXMLFile = new Button();
    visuals.downloadXMLFile.setOnAction(event -> {
      try {
        new GridToXML(controllerType, simulationData);
      } catch (ParserConfigurationException e) {
        e.printStackTrace();
      } catch (TransformerException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    myStage.setScene(myScene);
    return grid;
  }

  private void updateGrid(ResourceBundle resourceBundle, Controller controller,
                          EventHandler<ActionEvent> event) {
    Grid grid = visuals.updateGrid(controller);
    Scene scene = visuals.setGridView(grid, currentResourceBundle, event);
    myStage.setScene(scene);
  }

  public Grid setSpecificConfigFile(String fileName, Controller currentControllerType, ResourceBundle currentResourceBundle)
      throws ParserConfigurationException, SAXException, IOException {
    return setGrid(fileName, currentControllerType, currentResourceBundle);
  }


  public void createSecondLandingScreen(Controller currentControllerType, ResourceBundle currentResourceBundle)
      throws IOException, SAXException, ParserConfigurationException {

    Group root = new Group();
    addExitButton(root);
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(myStage);
    if (selectedFile != null){
      simulationStarted = true;
      String fileName = selectedFile.getName();
      setSpecificConfigFile(fileName, currentControllerType, currentResourceBundle);
    }

    myScene = new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT, visuals.BACKGROUND);
    myStage.setScene(myScene);
  }

  private void addExitButton(Group root) {
    root.getChildren().add(Graphics.exitSecondLandingScreen);
    Graphics.exitSecondLandingScreen.setTranslateY(350);
    Graphics.exitSecondLandingScreen.setTranslateX(10);
    Graphics.exitSecondLandingScreen.setOnAction(event -> {
      myScene = creatingLandingScreen();
      myStage.setScene(myScene);
    });
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
