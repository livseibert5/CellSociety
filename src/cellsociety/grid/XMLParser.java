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

  public XMLParser(Map<String, String> configData) {
    super(configData);
    createCustomSimulation();
  }

  /**
   * Parses the data from the XML file into appropriate data structures.
   *
   * @throws ParserConfigurationException error with xml parsing library
   * @throws SAXException                 error with xml parsing library
   * @throws IOException                  error reading file
   */
  public void readFile() throws ParserConfigurationException, SAXException, IOException {
    buildParser();
    parseSimulationData();
    Type type;
    type = Type.valueOf(retrieveTextContent("Type"));
    Map<String, Double> params = new HashMap<>();
    if (type == Type.FIRE || type == Type.SEGREGATION || type == Type.WATOR) {
      params = getSimulationParameters();
    }
    createGrid(type, params);
  }

  private void createCustomSimulation() {
    Type type = Type.valueOf(getInfo().get("Type"));
    Neighbors neighborType = Neighbors.SQUARE_MOORE;
    typeGridPairs(type, new HashMap<String, Double>(), neighborType, getInfo().get("PopulateType"));
    grid = typeGridPairs.get(getInfo().get("GridType"));
  }

  /**
   * Creates new grid object depending on XML input.
   *
   * @param type   simulation type for grid
   * @param params parameters to be passed to grid
   * @throws ParserConfigurationException error with xml parsing library
   * @throws SAXException                 error with xml parsing library
   * @throws IOException                  error reading file
   */
  private void createGrid(Type type, Map<String, Double> params)
      throws ParserConfigurationException, SAXException, IOException {
    StyleXMLParser styler = new StyleXMLParser(retrieveTextContent("Style"));
    styler.readFile();
    Neighbors neighborType = Neighbors.valueOf(styler.getNeighborLayout());
    String gridShape = styler.getGridType();
    String populateType = styler.getPopulateType();
    typeGridPairs(type, params, neighborType, populateType);
    grid = typeGridPairs.get(gridShape);
      /*
    } catch (Exception e) {
      grid = new Grid(8, 8, retrieveTextContent("LayoutFile"), type, params, neighborType,
          populateType);
    }*/
  }

  /**
   * Creates map with types of grids and the object they create for easier access.
   *
   * @param type         grid shape
   * @param params       parameters to be passed to new grid
   * @param neighborType neighbor formation for new grid
   * @param populateType population type for new grid
   */
  private void typeGridPairs(Type type, Map<String, Double> params, Neighbors neighborType,
      String populateType) {
    typeGridPairs = new HashMap<>();
    typeGridPairs.put("Square", grid = new Grid(Integer.parseInt(retrieveTextContent("Width")),
        Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
        type, params, neighborType, populateType));
    typeGridPairs
        .put("Toroidal", grid = new ToroidalGrid(Integer.parseInt(retrieveTextContent("Width")),
            Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
            type, params, neighborType, populateType));
    typeGridPairs
        .put("Triangle", grid = new TriangularGrid(Integer.parseInt(retrieveTextContent("Width")),
            Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
            type, params, neighborType, populateType));
  }

  /**
   * Creates map of simulation data for use in the view.
   */
  private void parseSimulationData() {
    getInfo().put("Type", retrieveTextContent("Type"));
    getInfo().put("Title", retrieveTextContent("Title"));
    getInfo().put("Author", retrieveTextContent("Author"));
    getInfo().put("Description", retrieveTextContent("Description"));
    getInfo().put("Style", retrieveTextContent("Style"));
  }

  /**
   * Loops through the list of parameters specified in the XML file and makes a map of their names
   * and values.
   *
   * @return map of simulation parameters
   */
  private Map<String, Double> getSimulationParameters() {
    Map<String, Double> params = new HashMap<>();
    NodeList node = getRoot().getElementsByTagName("Param");
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

  /**
   * Accesses grid created by this class.
   *
   * @return new grid object
   */
  public Grid getGrid() {
    return grid;
  }
}
