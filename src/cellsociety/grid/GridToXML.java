package cellsociety.grid;

import cellsociety.controller.Controller;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Map;

/**
 * Class that allows the current state of game play to be turned into an XML file.
 *
 * @author Livia Seibert
 */
public class GridToXML {

  private Document doc;
  private Element rootElement;
  private Element paramsElement;
  private final Map<String, String> simulationData;
  private final Controller controller;
  private String fileName;

  /**
   * Constructor for GridToXML class that takes information about the current state of gameplay and
   * turns it into an XML file.
   *
   * @param controller     current controller for simulation
   * @param simulationData map with data about the simulation
   * @throws ParserConfigurationException error for XML parser library
   * @throws TransformerException         error writing to new xml file
   * @throws IOException                  error writing grid to new .txt file
   */
  public GridToXML(Controller controller, Map<String, String> simulationData)
      throws ParserConfigurationException, TransformerException, IOException {
    this.controller = controller;
    this.simulationData = simulationData;
    initializeDocumentBuilder();
    createDocument();
    exportNewFile();
  }

  /**
   * Exports completed XML file so it can be used.
   *
   * @throws TransformerException error writing to new file
   */
  private void exportNewFile() throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File("data/" + fileName + ".xml"));
    transformer.transform(source, result);
  }

  /**
   * Creates DocumentBuilder and DocumentBuilderFactory to create new XML doc.
   *
   * @throws ParserConfigurationException issue with DocumentBuilder creation
   */
  private void initializeDocumentBuilder() throws ParserConfigurationException {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    doc = dBuilder.newDocument();
  }

  /**
   * Builds the new XML file.
   *
   * @throws IOException error writing to new file
   */
  private void createDocument() throws IOException {
    rootElement = doc.createElement("Simulation");
    doc.appendChild(rootElement);
    for (String str : simulationData.keySet()) {
      createNewElementWithText(str);
    }
    paramsElement = doc.createElement("ConfigParams");
    rootElement.appendChild(paramsElement);
    Map<String, Double> params = controller.getNewGrid().getParams();
    for (String str : params.keySet()) {
      createNewParameter(str, params.get(str));
    }
    createWidthAndHeight();
    fileName = createFileName();
    createGridFile();
  }

  /**
   * Creates file name from XML file based on date and time.
   *
   * @return file name for XML file
   */
  private String createFileName() {
    String pattern = "MM_dd_yy_HH_mm";
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    Date today = Calendar.getInstance().getTime();
    return dateFormat.format(today);
  }

  /**
   * Uses the GridFile class to write a new .txt file with the current grid layout, then creates an
   * element in the new XML file that references the .txt file.
   */
  private void createGridFile() throws IOException {
    Element layout = doc.createElement("LayoutFile");
    GridFile gridFile = new GridFile(fileName, controller.getNewGrid());
    gridFile.writeGridToFile();
    layout.appendChild(doc.createTextNode(fileName + ".txt"));
    rootElement.appendChild(layout);
  }

  /**
   * Creates an XML tag with the given name and value from the simulationData map.
   *
   * @param name name of new XML tag
   */
  private void createNewElementWithText(String name) {
    String value = simulationData.get(name);
    Element element = doc.createElement(name);
    element.appendChild(doc.createTextNode(value));
    rootElement.appendChild(element);
  }

  /**
   * Creates a new parameter tag with the proper name and value.
   *
   * @param key   name of parameter
   * @param value value of parameter
   */
  private void createNewParameter(String key, Double value) {
    Element param = doc.createElement("Param");
    paramsElement.appendChild(param);
    Element name = doc.createElement("Name");
    Element val = doc.createElement("Value");
    param.appendChild(name);
    param.appendChild(val);
    name.appendChild(doc.createTextNode(key));
    val.appendChild(doc.createTextNode(Double.toString(value)));
  }

  /**
   * Creates width and height tag for XML file.
   */
  private void createWidthAndHeight() {
    Element width = doc.createElement("Width");
    width.appendChild(
        doc.createTextNode(Integer.toString(controller.getNewGrid().getSizeOfGrid()[1])));
    Element height = doc.createElement("Height");
    height.appendChild(
        doc.createTextNode(Integer.toString(controller.getNewGrid().getSizeOfGrid()[0])));
    rootElement.appendChild(width);
    rootElement.appendChild(height);
  }
}
