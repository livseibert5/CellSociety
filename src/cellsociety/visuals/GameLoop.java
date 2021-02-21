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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
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
  private String language;
  private Paint backgroundColor = Color.AZURE;
  private Map<String, String> simulationData;

  private Controller secondController;
  private boolean hasSecondSimulation = false;
  private Map<String, String> secondSimulationData;

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
        if (secondController == null || secondController.simulationEnded())
          simulationStarted = false;
      }
  }

  public Scene creatingLandingScreen() {
    int baseY = 20;
    simulationStarted = false;
    VBox root = new VBox();
    VBox buttonsInVeritcal = new VBox();
    BorderPane sceneLayout = new BorderPane();
    Text welcome = visuals.constructText(baseY, 30, "Simulation Menu", FontWeight.BOLD, Graphics.FONT);
    Text instructions = visuals.constructText(baseY + 20, 15,
        "click on any simulation to start", FontWeight.NORMAL, Graphics.FONT);

    visuals
        .createButton(Graphics.myLandingSceneResources.getString("GameOfLifeSimulation"), 0, buttonsInVeritcal,
            event -> {
              try {
                currentControllerType = new GameOfLifeController();
                secondController = new GameOfLifeController();
                currentResourceBundle = Graphics.myGameOfLifeSimulationResources;
                openFileChooser(currentControllerType, currentResourceBundle);

              } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
              }
            });

    visuals.createButton(Graphics.myLandingSceneResources.getString("PercolationSimulation"), 10,
        buttonsInVeritcal, event -> {
          try {

            currentControllerType = new PercolationController();
            secondController = new GameOfLifeController();
            currentResourceBundle = Graphics.myPercolationSimulationResources;
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("SegregationSimulation"), 20,
        buttonsInVeritcal, event -> {
          try {
            currentControllerType = new SegregationController();
            secondController = new SegregationController();
            currentResourceBundle = Graphics.mySegregationSimulationResources;
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("WaTorSimulation"), 30, buttonsInVeritcal,
        event -> {
          try {
            currentControllerType = new WatorController();
            secondController = new WatorController();
            currentResourceBundle = Graphics.myWaTorSimulationResources;
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals.createButton(Graphics.myLandingSceneResources.getString("FireSimulation"), 40, buttonsInVeritcal,
        event -> {
          try {
            currentControllerType = new FireController();
            secondController = new FireController();
            currentResourceBundle = Graphics.myFireSimulationResources;
            openFileChooser(currentControllerType, currentResourceBundle);

          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });

    visuals
            .createButton(Graphics.myLandingSceneResources.getString("AntSimulation"), 50, buttonsInVeritcal,
                    event -> {
                      try {
                        currentControllerType = new AntController();
                        secondController = new AntController();
                        currentResourceBundle = Graphics.myAntSimulation;
                        openFileChooser(currentControllerType, currentResourceBundle);

                      } catch (IOException | SAXException | ParserConfigurationException e) {
                        e.printStackTrace();
                      }
                    });

    visuals
            .createButton(Graphics.myLandingSceneResources.getString("SugarSimulation"), 60,
                    buttonsInVeritcal,
                    event -> {
                      try {
                        currentControllerType = new SugarController();
                        secondController = new SugarController();
                        currentResourceBundle = Graphics.mySugarSimulation;
                        openFileChooser(currentControllerType, currentResourceBundle);

                      } catch (IOException | SAXException | ParserConfigurationException e) {
                        e.printStackTrace();
                      }
                    });

    visuals
            .createButton(Graphics.myLandingSceneResources.getString("Custom"), 70, buttonsInVeritcal,
                    event -> {
                      try {
                        createSecondLandingScreen(currentControllerType, currentResourceBundle);

                      } catch (IOException | SAXException | ParserConfigurationException e) {
                        e.printStackTrace();
                      }
                    });

    VBox leftOfLandingScreen = createLandingScreenCustom(root);

    TextFlow text = new TextFlow();
    text.getChildren().add(welcome);

    text.setTextAlignment(TextAlignment.CENTER);
    sceneLayout.setTop(text);
    sceneLayout.setCenter(buttonsInVeritcal);
    sceneLayout.setLeft(leftOfLandingScreen);

    return new Scene(sceneLayout, Graphics.SCREEN_WIDTH, Graphics.SCREEN_HEIGHT, backgroundColor);

  }

  public void setExitButtonToLandingScreen() {
    myStage.setScene(creatingLandingScreen());
  }

  private VBox createLandingScreenCustom(VBox root){
    String[] languageOptions = {"english", "french", "spanish"};
    ComboBox typeOfLanguage = getComboBox(languageOptions, root, 1, 0);
    typeOfLanguage.setPromptText("Language");
    typeOfLanguage.setOnAction(event -> {
      System.out.println(typeOfLanguage.getValue());
      language = (String) typeOfLanguage.getValue();
    });

    String[] colorOptions = {"dark", "light", "duke"};
    ComboBox typeOfColor = getComboBox(colorOptions, root, 1, 0);
    typeOfColor.setPromptText("Color");
    typeOfColor.setOnAction(event -> {
      System.out.println(typeOfColor.getValue());
      String color = (String) typeOfColor.getValue();
      if(Graphics.colorResourceBundle.containsKey(color)){
        backgroundColor = Color.valueOf(Graphics.colorResourceBundle.getString(color));
      }
    });

    VBox comboBox = new VBox(typeOfLanguage, typeOfColor);
    return comboBox;
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
    visuals = new Graphics(controllerType, simulationType, language);
    return setGraphicsParameters(controllerType, null, grid, grid);
  }

  private Grid setTwoGrid(String filename, String secondFileName, Controller controllerType, Controller secondController,
      ResourceBundle simulationType)
      throws IOException, SAXException, ParserConfigurationException {
    XMLParser parse = new XMLParser(filename);
    parse.readFile();
    simulationData = parse.getInfo();
    Grid grid = parse.getGrid();
    XMLParser parse2 = new XMLParser(secondFileName);
    parse2.readFile();
    secondSimulationData = parse2.getInfo();
    Grid grid2 = parse2.getGrid();
    visuals = new GraphicsTwoView(controllerType, secondController, simulationType, language);
    return setGraphicsParameters(controllerType, secondController, grid, grid2);
  }

  private Grid setGraphicsParameters(Controller controllerType, Controller secondController,
      Grid grid, Grid grid2) {
    controllerType.setInitialGrid(grid);
    if (secondController != null) secondController.setInitialGrid(grid2);
    myScene = visuals
        .createVisualGrid(grid, currentResourceBundle, event -> setExitButtonToLandingScreen(), (Color) backgroundColor);
    Graphics.faster.setOnAction(event -> setMod(30));
    Graphics.slower.setOnAction(event -> setMod(120));
    Graphics.normal.setOnAction(event -> setMod(60));
    Graphics.play.setOnAction(event -> simulationStarted = true);
    Graphics.pause.setOnAction(event -> simulationStarted = false);
    Button downloadXMLFile = new Button();
    visuals.downloadXMLFile.setOnAction(event -> {
      try {
        new GridToXML(controllerType, simulationData);
      } catch (ParserConfigurationException | TransformerException | IOException e) {
        e.printStackTrace();
      }});
    myStage.setScene(myScene);
    return grid;
  }

  private void updateGrid(ResourceBundle resourceBundle, Controller controller,
                          EventHandler<ActionEvent> event) {
    Grid grid = visuals.updateGrid(controller);
    Scene scene;
    if (hasSecondSimulation)  {
      GraphicsTwoView secondGraphicsController = (GraphicsTwoView) visuals;
      Grid grid2 = secondGraphicsController.updateGrid(secondController);
      scene = secondGraphicsController.setGridView(grid, grid2, currentResourceBundle, event);
    }
    else  {
      System.out.println("here");
       scene = visuals.setGridView(grid, currentResourceBundle, event);
    }
    myStage.setScene(scene);
  }

  private Grid setSpecificConfigFile(String fileName, Controller currentControllerType, ResourceBundle currentResourceBundle)
      throws ParserConfigurationException, SAXException, IOException {
    return setGrid(fileName, currentControllerType, currentResourceBundle);
  }


  private void openFileChooser(Controller currentControllerType, ResourceBundle currentResourceBundle) throws IOException, SAXException, ParserConfigurationException {

    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(myStage);
    if (selectedFile != null){
      String fileName = selectedFile.getName();
      openSecondFileChooser(fileName, secondController, currentResourceBundle);
    }
  }

  private void openSecondFileChooser(String firstFileName, Controller secondController, ResourceBundle currentResourceBundle) throws IOException, SAXException, ParserConfigurationException {

    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(myStage);
    if (selectedFile != null){
      hasSecondSimulation = true;
      String fileName = selectedFile.getName();
      setTwoGrid(firstFileName, fileName, currentControllerType, secondController, currentResourceBundle);
    }
    else  {
      hasSecondSimulation = false;
      setSpecificConfigFile(firstFileName, currentControllerType, currentResourceBundle);
    }
    simulationStarted = true;
  }

  public HashMap<String, String> createCustom(){
    //create a map with the same tags that the style sheets have.
    //random.
    //trinagular cant be tordial
    //languages: english, spanish, and french.
    //color: dark, light, duke mode
    double comboBoxXPosition = visuals.SCREEN_WIDTH/2 - 40;
    HashMap<String, String> readInXML = new HashMap<>();
    VBox root = new VBox();
    String[] simulationOptions = {"Fire", "Wa-Tor", "Percolation", "Segregation", "Game of Life"};
    ComboBox typeOfSimulation = getComboBox(simulationOptions, root, 0, comboBoxXPosition);
    typeOfSimulation.setPromptText("Simulation");
      typeOfSimulation.setOnAction(event ->
        readInXML.put("Type", (String) typeOfSimulation.getValue()));

    String[] shapeOptions = {"Triangle", "Square", "Toroidal"};
    ComboBox typeOfShape = getComboBox(shapeOptions, root, 1, comboBoxXPosition);
    typeOfShape.setPromptText("Grid Type");
      typeOfShape.setOnAction(event ->
        readInXML.put("GridType", (String) typeOfShape.getValue()));

    String[] languageOptions = {"english", "french", "spanish"};
    ComboBox typeOfLanguage = getComboBox(languageOptions, root, 2, comboBoxXPosition);
    typeOfLanguage.setPromptText("Language");
    typeOfLanguage.setOnAction(event -> {
      readInXML.put("Language", ((String) typeOfLanguage.getValue()).toUpperCase(Locale.ROOT));
      language = readInXML.get("Language");
    });

    String[] colorOptions = {"dark", "light", "duke"};
    ComboBox typeOfColor = getComboBox(colorOptions, root, 3, comboBoxXPosition);
    typeOfColor.setPromptText("Color");
    typeOfColor.setOnAction(event -> {
      readInXML.put("Color", (String) typeOfColor.getValue());
        String color = readInXML.get("Color");
      if(Graphics.colorResourceBundle.containsKey(color)){
            backgroundColor = Color.valueOf(Graphics.colorResourceBundle.getString(color));
      }
    });

    String[] startGridOptions = {"normal", "random"};
    ComboBox typeOfStart = getComboBox(startGridOptions, root, 4, comboBoxXPosition);
    typeOfStart.setPromptText("Start Grid Layout");
    typeOfStart.setOnAction(event -> {
      System.out.println(typeOfStart.getValue());
      readInXML.put("Start", (String) typeOfStart.getValue());});

    //root.getChildren().addAll(typeOfColor,typeOfStart,typeOfLanguage, typeOfShape, typeOfSimulation);
    myScene = new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT);
    return readInXML;
  }

//  private static void setGridBackgroundColor(){
//    backgroundColor =
//  }

  private ComboBox getComboBox(String[] listOfOptions, VBox root, int listNumber, double xPosition) {
    ComboBox typeOfComboBox = new ComboBox();
    //typeOfComboBox.setSt
    for(String s : listOfOptions){
      typeOfComboBox.getItems().add(s);
    }
    //typeOfComboBox.setTranslateX(xPosition);
    typeOfComboBox.setTranslateY(10*listNumber);
    root.getChildren().add(typeOfComboBox);
    root.setAlignment(Pos.CENTER);
    return typeOfComboBox;
  }

  public void createSecondLandingScreen(Controller currentControllerType, ResourceBundle currentResourceBundle)
      throws IOException, SAXException, ParserConfigurationException {

    Group root = new Group();
    addExitButton(root);
    createCustom();
           // new Scene(root, visuals.SCREEN_WIDTH, visuals.SCREEN_HEIGHT, visuals.BACKGROUND);
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
    language = "English";
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
