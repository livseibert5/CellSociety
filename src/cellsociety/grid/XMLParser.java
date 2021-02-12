package cellsociety.grid;

import org.w3c.dom.Node;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Reads in and handles the data from the XML file.
 *
 * @author Livia Seibert
 */
public class XMLParser {

  private Map<String, String> simulationData;
  private String fileName;
  private Element root;
  private Grid grid;

  public XMLParser(String fileName) {
    this.simulationData = new HashMap<>();
    this.fileName = fileName;
  }

  private void buildParser() throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream(fileName));
    root = doc.getDocumentElement();
  }

  /**
   * Parses the data from the XML file into appropriate data structures.
   *
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  public void readFile() throws ParserConfigurationException, SAXException, IOException {
    buildParser();
    simulationData.put("Title", retrieveTextContent("Title"));
    simulationData.put("Author", retrieveTextContent("Author"));
    simulationData.put("Description", retrieveTextContent("Description"));
    Map<String, Double> params = new HashMap<>();
    Type type = Type.valueOf(retrieveTextContent("Type"));
    if (type == Type.FIRE || type == Type.SEGREGATION || type == Type.WATOR) {
      params = getSimulationParameters();
    }
    if (type == Type.WATOR) {
      grid = new ToroidalGrid(Integer.parseInt(retrieveTextContent("Width")),
          Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
          type, params);
    } else {
      grid = new Grid(Integer.parseInt(retrieveTextContent("Width")),
          Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
          type, params);
    }
  }

  private Map<String, Double> getSimulationParameters() {
    Map<String, Double> params = new HashMap<>();
    NodeList node = root.getElementsByTagName("Param");
    for (int i = 0; i < node.getLength(); i++) {
      Node currNode = node.item(i);
      Element nodeElement = (Element) currNode;
      String name = nodeElement.getElementsByTagName("Name").item(0).getTextContent();
      Double val = Double
          .parseDouble(nodeElement.getElementsByTagName("Value").item(0).getTextContent());
      params.put(name, val);
    }
    return params;
  }

  private String retrieveTextContent(String tagName) {
    NodeList node = root.getElementsByTagName(tagName);
    return node.item(0).getTextContent();
  }

  /**
   * Accesses grid created by this class.
   *
   * @return new grid object
   */
  public Grid getGrid() {
    return grid;
  }

  /**
   * Accesses simulation info parsed from XML file.
   *
   * @return simulationData HashMap with xml data
   */
  public Map<String, String> getInfo() {
    return simulationData;
  }
}
