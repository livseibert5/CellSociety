package cellsociety.grid;

import cellsociety.controller.Controller;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import org.xml.sax.SAXException;
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
  private Map<String, String> simulationData;
  private Controller controller;
  private String fileName;

  /**
   * Constructor for GridToXML class that takes information about the current state of gameplay and
   * turns it into an XML file.
   *
   * @param controller     current controller for simulation
   * @param simulationData map with data about the simulation
   * @throws ParserConfigurationException
   */
  public GridToXML(Controller controller, Map<String, String> simulationData)
      throws ParserConfigurationException, TransformerException {
    this.controller = controller;
    this.simulationData = simulationData;
    initializeDocumentBuilder();
    createDocument();
    exportNewFile();
  }

  /**
   * Exports completed XML file so it can be used.
   *
   * @throws TransformerException
   */
  private void exportNewFile() throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(fileName + ".xml"));
    transformer.transform(source, result);
  }

  private void initializeDocumentBuilder() throws ParserConfigurationException {
    DocumentBuilderFactory dbFactory =
        DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    doc = dBuilder.newDocument();
  }

  private void createDocument() {
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
    fileName = java.time.LocalDate.now().toString();
    createGridFile(fileName);
  }

  /**
   * Uses the GridFile class to write a new .txt file with the current grid layout, then creates an
   * element in the new XML file that references the .txt file.
   *
   * @param fileName name for new .txt file
   */
  private void createGridFile(String fileName) {
    Element layout = doc.createElement("LayoutFile");
    GridFile gridFile = new GridFile(fileName, controller.getNewGrid());
    layout.appendChild(doc.createTextNode(fileName + ".txt"));
    rootElement.appendChild(layout);
  }

  private void createNewElementWithText(String name) {
    String value = simulationData.get(name);
    Element element = doc.createElement(name);
    element.appendChild(doc.createTextNode(value));
    rootElement.appendChild(element);
  }

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
