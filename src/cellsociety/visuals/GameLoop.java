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

  private static final String TITLE = "Cellular Automata";
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private Graphics visuals;
  private Scene myScene;
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
  private boolean secondSimulationStarted = false;
  private Map<String, String> secondSimulationData;

  /**
   * continuously runs when the project is running. Starts the simulation.
   * updates grid every time it runs.
   * @param elapsedTime
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  private void step(double elapsedTime)
      throws IOException, SAXException, ParserConfigurationException {
    time += 1;
    if ((simulationStarted || secondSimulationStarted) && mod != 0 && (time % mod == 0)) {
      updateGrid(currentResourceBundle, currentControllerType,
          event -> setExitButtonToLandingScreen());
      checkSimulationEnded();
      currentControllerType.resetController();
    }
  }

  /**
   * checks if simulation ended, stops simulation if it did.
   */
  private void checkSimulationEnded() {
    if (currentControllerType.simulationEnded()) {
      simulationStarted = false;
    }
    if (secondController == null || secondController.simulationEnded()) {
      secondSimulationStarted = false;
    }
  }

  /**
   * returns the landing scene with the buttons of each simulation.
   * Also has the option to change language and background color.
   * @return Scene with buttons and the title of the project.
   */
  public Scene creatingLandingScreen() {

    //where the Simulation Menu text starts.
    int baseY = 20;
    simulationStarted = false;
    secondSimulationStarted = false;
    //vertical button layout.
    VBox buttonsInVertical = new VBox();
    BorderPane sceneLayout = new BorderPane();
    //create the simulation title
    Text welcome = Graphics.constructText(baseY, 30, "Simulation Menu", FontWeight.BOLD, Graphics.FONT);

    //create a button for each simulation, set controller type and resourceBundle based on what simulation is chosen.
    //Game of Life
    Graphics.createButton(Graphics.myLandingSceneResources.getString("GameOfLifeSimulation"), 0, buttonsInVertical,
            event -> {
              try {
                currentControllerType = new GameOfLifeController();
                secondController = new GameOfLifeController();
                currentResourceBundle = Graphics.myGameOfLifeSimulationResources;
                //choose the XML file through the data folder on intelliJ
                openFileChooser(currentControllerType, currentResourceBundle);
              } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
              }
            });
    //Percolation
    Graphics.createButton(Graphics.myLandingSceneResources.getString("PercolationSimulation"), 10,
        buttonsInVertical, event -> {
          try {
            currentControllerType = new PercolationController();
            secondController = new GameOfLifeController();
            currentResourceBundle = Graphics.myPercolationSimulationResources;
            //choose the XML file through the data folder on intelliJ
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });
    //Segregation
    Graphics.createButton(Graphics.myLandingSceneResources.getString("SegregationSimulation"), 20,
        buttonsInVertical, event -> {
          try {
            currentControllerType = new SegregationController();
            secondController = new SegregationController();
            currentResourceBundle = Graphics.mySegregationSimulationResources;
            //choose the XML file through the data folder on intelliJ
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });
    //Wa-Tor
    Graphics.createButton(Graphics.myLandingSceneResources.getString("WaTorSimulation"), 30, buttonsInVertical,
        event -> {
          try {
            currentControllerType = new WatorController();
            secondController = new WatorController();
            currentResourceBundle = Graphics.myWaTorSimulationResources;
            //choose the XML file through the data folder on intelliJ
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });
    //Fire
    Graphics.createButton(Graphics.myLandingSceneResources.getString("FireSimulation"), 40, buttonsInVertical,
        event -> {
          try {
            currentControllerType = new FireController();
            secondController = new FireController();
            currentResourceBundle = Graphics.myFireSimulationResources;
            //choose the XML file through the data folder on intelliJ
            openFileChooser(currentControllerType, currentResourceBundle);
          } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
          }
        });
    //Ant Forager
    Graphics.createButton(Graphics.myLandingSceneResources.getString("AntSimulation"), 50, buttonsInVertical,
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
    //Sugar
    Graphics.createButton(Graphics.myLandingSceneResources.getString("SugarSimulation"), 60,
                    buttonsInVertical,
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
    //create a custom simulation
    Graphics.createButton(Graphics.myLandingSceneResources.getString("Custom"), 70, buttonsInVertical,
                    event -> {
                      try {
                        //goes to a second landing screen where you can choose all the parameters.
                        createSecondLandingScreen(currentControllerType, currentResourceBundle);
                      } catch (IOException | SAXException | ParserConfigurationException e) {
                        e.printStackTrace();
                      }
                    });

    //vertical button layout second landing screen.
    VBox root = new VBox();
    VBox leftOfLandingScreen = createLandingScreenCustom(root);
    //add the title
    TextFlow text = new TextFlow();
    text.getChildren().add(welcome);
    text.setTextAlignment(TextAlignment.CENTER);
    //set up the landing scene
    sceneLayout.setTop(text);
    sceneLayout.setCenter(buttonsInVertical);
    sceneLayout.setLeft(leftOfLandingScreen);
    return new Scene(sceneLayout, Graphics.SCREEN_WIDTH, Graphics.SCREEN_HEIGHT, backgroundColor);
  }

  /**
   * set this method as an action to go back to landing screen when the exit button is clicked on.
   */
  public void setExitButtonToLandingScreen() {
    myStage.setScene(creatingLandingScreen());
  }

  /**
   * returns elements that need ot be set up in the lading screen. Controls colors and language.
   * @param root need to put it in as a parameter because it is used in the getComboBox method to add the ComboBoxes
   * @return a VBOX with the buttons that are needed in the landing screen to custom color and language.
   */
  private VBox createLandingScreenCustom(VBox root){
    String[] languageOptions = {"english", "french", "spanish"};
    ComboBox typeOfLanguage = getComboBox(languageOptions, root, 1, 0);
    typeOfLanguage.setPromptText("Language");
    typeOfLanguage.setOnAction(event -> {
      //change language value to whatever value is chosen in typeOfLanguage.
      language = (String) typeOfLanguage.getValue();
    });

    String[] colorOptions = {"dark", "light", "duke"};
    ComboBox typeOfColor = getComboBox(colorOptions, root, 1, 0);
    typeOfColor.setPromptText("Color");
    typeOfColor.setOnAction(event -> {
      //set background color to whatever color is chosen from the list
      String color = (String) typeOfColor.getValue();
      if(Graphics.colorResourceBundle.containsKey(color)){
        backgroundColor = Color.valueOf(Graphics.colorResourceBundle.getString(color));
      }
    });

    return new VBox(typeOfLanguage, typeOfColor);
  }

  //change the speed of the simulation
  private void setMod(int mod) {
    this.mod = mod;
  }

  //read a specific xml file to get the start of the simulation layout
  private Grid setGrid(String filename, Controller controllerType, ResourceBundle simulationType)
      throws IOException, SAXException, ParserConfigurationException {
    XMLParser parse = new XMLParser(filename);
    parse.readFile();
    simulationData = parse.getInfo();
    Grid grid = parse.getGrid();
    visuals = new Graphics(controllerType, simulationType, language);
    return setGraphicsParameters(controllerType, null, grid, grid);
  }
  //create the second view grid
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
  //set the parameters for the grid
  private Grid setGraphicsParameters(Controller controllerType, Controller secondController,
      Grid grid, Grid grid2) {
    controllerType.setInitialGrid(grid);
    if (secondController != null) secondController.setInitialGrid(grid2);
    myScene = visuals
        .createVisualGrid(grid, currentResourceBundle, event -> setExitButtonToLandingScreen(), (Color) backgroundColor);
    Graphics.faster.setOnAction(event -> setMod(30));
    Graphics.slower.setOnAction(event -> setMod(120));
    Graphics.normal.setOnAction(event -> setMod(60));
    Graphics.play.setOnAction(event -> startSimulation());
    Graphics.pause.setOnAction(event -> pauseSimulation());
    Button downloadXMLFile = new Button();
    Graphics.downloadXMLFile.setOnAction(event -> {
      try {
        new GridToXML(controllerType, simulationData);
      } catch (ParserConfigurationException | TransformerException | IOException e) {
        e.printStackTrace();
      }});
    myStage.setScene(myScene);
    return grid;
  }

  private void pauseSimulation()  {
    simulationStarted = false;
    secondSimulationStarted = false;
  }
  private void startSimulation()  {
    simulationStarted = true;
    if (secondController != null) secondSimulationStarted = true;
  }

  private void updateGrid(ResourceBundle resourceBundle, Controller controller,
                          EventHandler<ActionEvent> event) {
    Grid grid = visuals.updateGrid(controller);
    Scene scene;
    if (secondSimulationStarted)  {
      GraphicsTwoView secondGraphicsController = (GraphicsTwoView) visuals;
      Grid grid2 = secondGraphicsController.updateGrid(secondController);
      scene = secondGraphicsController.setGridView(grid, grid2, currentResourceBundle, event, simulationStarted, secondSimulationStarted);
    }
    else  {
       scene = visuals.setGridView(grid, currentResourceBundle, event);
    }
    myStage.setScene(scene);
  }

  private void setSpecificConfigFile(String fileName, Controller currentControllerType, ResourceBundle currentResourceBundle)
      throws ParserConfigurationException, SAXException, IOException {
      setGrid(fileName, currentControllerType, currentResourceBundle);
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
      secondSimulationStarted = true;
      String fileName = selectedFile.getName();
      setTwoGrid(firstFileName, fileName, currentControllerType, secondController, currentResourceBundle);
    }
    else  {
      secondSimulationStarted = false;
      setSpecificConfigFile(firstFileName, currentControllerType, currentResourceBundle);
    }
    simulationStarted = true;
  }

  private HashMap<String, String> createCustom(){

    double comboBoxXPosition = visuals.SCREEN_WIDTH/2 - 40;
    HashMap<String, String> readInXML = new HashMap<>();
    VBox root = new VBox();
    String[] simulationOptions = {"FIRE", "WATOR", "PERCOLATION", "SEGREGATION", "LIFE"};
    ComboBox typeOfSimulation = getComboBox(simulationOptions, root, 0, comboBoxXPosition);
    typeOfSimulation.setPromptText("Simulation");
      typeOfSimulation.setOnAction(event ->
        readInXML.put("Type", ((String) typeOfSimulation.getValue()).toUpperCase(Locale.ROOT)));

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

    String[] startGridOptions = {"MANUAL", "RANDOM"};
    ComboBox typeOfStart = getComboBox(startGridOptions, root, 4, comboBoxXPosition);
    typeOfStart.setPromptText("Start Grid Layout");
    typeOfStart.setOnAction(event -> {
      readInXML.put("PopulateType", (String) typeOfStart.getValue());});

    Button createXMLInstance = new Button("Start");
    createXMLInstance.setTranslateY(5*10);
    root.getChildren().add(createXMLInstance);
    createXMLInstance.setOnAction(event -> {
              try {
                if(readInXML.get("Type").equals("FIRE")){
                  currentControllerType = new FireController();
                  currentResourceBundle = Graphics.myFireSimulationResources;
                }else if(readInXML.get("Type").equals("WATOR")){
                  currentControllerType = new WatorController();
                  currentResourceBundle = Graphics.myWaTorSimulationResources;
                }else if(readInXML.get("Type").equals("PERCOLATION")){
                  currentControllerType = new PercolationController();
                  currentResourceBundle = Graphics.myPercolationSimulationResources;
                }else if(readInXML.get("Type").equals("SEGREGATION")){
                  currentControllerType = new SegregationController();
                  currentResourceBundle = Graphics.mySegregationSimulationResources;
                }else if(readInXML.get("Type").equals("LIFE")){
                  currentControllerType = new GameOfLifeController();
                  currentResourceBundle = Graphics.myGameOfLifeSimulationResources;
                }
                setCustomGrid(new XMLParser(readInXML), currentControllerType, currentResourceBundle);

              } catch (IOException e) {
                e.printStackTrace();
              } catch (SAXException e) {
                e.printStackTrace();
              } catch (ParserConfigurationException e) {
                e.printStackTrace();
              }
            }
    );
    myScene = new Scene(root, Graphics.SCREEN_WIDTH, Graphics.SCREEN_HEIGHT);
    return readInXML;
  }

  private Grid setCustomGrid(XMLParser parse, Controller controllerType, ResourceBundle simulationType)
          throws IOException, SAXException, ParserConfigurationException {
    parse.readFile();
    simulationData = parse.getInfo();
    Grid grid = parse.getGrid();
    visuals = new Graphics(controllerType, simulationType, language);
    return setGraphicsParameters(controllerType, null, grid, grid);
  }

  private ComboBox getComboBox(String[] listOfOptions, VBox root, int listNumber, double xPosition) {
    ComboBox<String> typeOfComboBox = new ComboBox<String>();
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

  private void createSecondLandingScreen(Controller currentControllerType, ResourceBundle currentResourceBundle)
      throws IOException, SAXException, ParserConfigurationException {

    Group root = new Group();
    addExitButton(root);
    createCustom();
    myStage.setScene(myScene);
  }

  private void addExitButton(Group root) {
    root.getChildren().add(Graphics.exitSecondLandingScreen);
    Graphics.exitSecondLandingScreen.setTranslateY(350);
    Graphics.exitSecondLandingScreen.setTranslateX(10);
    Graphics.exitSecondLandingScreen.setOnAction(event -> {
      myScene = creatingLandingScreen();
      myStage.setScene(myScene);
      pauseSimulation();
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
