package cellsociety.grid;

import cellsociety.cells.Neighbors;
import org.w3c.dom.Node;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Reads in and handles the data from the XML file.
 *
 * @author Livia Seibert
 */
public class XMLParser extends XMLReader {

  private Grid grid;
  Map<String, Grid> typeGridPairs;

  /**
   * Constructor for XMLParser, sets the fileName that has the information XMLParser needs to set up
   * the simulation.
   *
   * @param fileName xml file to read in
   */
  public XMLParser(String fileName) throws ParserConfigurationException, SAXException, IOException {
    super(fileName);
  }

  /**
   * Parses the data from the XML file into appropriate data structures.
   *
   * @throws ParserConfigurationException error with xml parsing library
   * @throws SAXException error with xml parsing library
   * @throws IOException error reading file
   */
  public void readFile() throws ParserConfigurationException, SAXException, IOException {
    buildParser();
    parseSimulationData();
    Type type;
    try {
      type = Type.valueOf(retrieveTextContent("Type"));
    } catch (IllegalArgumentException e) {
      type = Type.EMPTY;
    }
    Map<String, Double> params = new HashMap<>();
    if (type == Type.FIRE || type == Type.SEGREGATION || type == Type.WATOR) {
      params = getSimulationParameters();
    }
    createGrid(type, params);
  }

  private void createGrid(Type type, Map<String, Double> params)
      throws ParserConfigurationException, SAXException, IOException {
    StyleXMLParser styler = new StyleXMLParser(retrieveTextContent("Style"));
    styler.readFile();
    Neighbors neighborType = Neighbors.valueOf(styler.getNeighborLayout());
    String gridShape = styler.getGridType();
    typeGridPairs(type, params, neighborType);
    try {
      grid = typeGridPairs.get(gridShape);
    } catch (Exception e) {
      grid = new Grid(8, 8, retrieveTextContent("LayoutFile"), type, params, neighborType);
    }
  }

  private void typeGridPairs(Type type, Map<String, Double> params, Neighbors neighborType) {
    typeGridPairs = new HashMap<>();
    typeGridPairs.put("Square", grid = new Grid(Integer.parseInt(retrieveTextContent("Width")),
        Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
        type, params, neighborType));
    typeGridPairs.put("Toroidal", grid = new ToroidalGrid(Integer.parseInt(retrieveTextContent("Width")),
        Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
        type, params, neighborType));
    typeGridPairs.put("Triangle", grid = new TriangularGrid(Integer.parseInt(retrieveTextContent("Width")),
        Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
        type, params, neighborType));
  }

  /**
   * Creates map of simulation data for use in the view.
   */
  private void parseSimulationData() {
    simulationData.put("Type", retrieveTextContent("Type"));
    simulationData.put("Title", retrieveTextContent("Title"));
    simulationData.put("Author", retrieveTextContent("Author"));
    simulationData.put("Description", retrieveTextContent("Description"));
  }

  /**
   * Loops through the list of parameters specified in the XML file and makes a map of their names
   * and values.
   *
   * @return map of simulation parameters
   */
  private Map<String, Double> getSimulationParameters() {
    Map<String, Double> params = new HashMap<>();
    NodeList node = root.getElementsByTagName("Param");
    for (int i = 0; i < node.getLength(); i++) {
      Node currNode = node.item(i);
      Element nodeElement = (Element) currNode;
      try {
        String name = nodeElement.getElementsByTagName("Name").item(0).getTextContent();
        Double val = Double
            .parseDouble(nodeElement.getElementsByTagName("Value").item(0).getTextContent());
        params.put(name, val);
      } catch (Exception e) {
        return params;
      }
    }
    return params;
  }

  /**
   * Accesses grid created by this class.
   *
   * @return new grid object
   */
  public Grid getGrid() {
    return grid;
  }
}
