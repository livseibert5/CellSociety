package cellsociety.grid;

import cellsociety.controller.Controller;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
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

  public GridToXML(Controller controller, Map<String, String> simulationData) throws ParserConfigurationException {
    this.controller = controller;
    this.simulationData = simulationData;
    initializeDocumentBuilder();
    createDocument();
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

    for (String str: simulationData.keySet()) {
      createNewElementWithText(str);
    }

    paramsElement = doc.createElement("ConfigParams");
    rootElement.appendChild(paramsElement);
    Map<String, Double> params = controller.getNewGrid().getParams();
    for (String str: params.keySet()) {
      createNewParameter(str, params.get(str));
    }

    createWidthAndHeight();
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
    width.appendChild(doc.createTextNode(Integer.toString(controller.getNewGrid().getSizeOfGrid()[1])));
    Element height = doc.createElement("Height");
    height.appendChild(doc.createTextNode(Integer.toString(controller.getNewGrid().getSizeOfGrid()[0])));
    rootElement.appendChild(width);
    rootElement.appendChild(height);
  }
}
