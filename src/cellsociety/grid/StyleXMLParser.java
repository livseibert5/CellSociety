package cellsociety.grid;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Class that parses XML stylesheets.
 *
 * @author Livia Seibert
 */
public class StyleXMLParser extends XMLReader {

  /**
   * Uses XMLReader superclass constructor.
   *
   * @param fileName name of stylesheet to parse.
   * @throws ParserConfigurationException issue configuring DocumentBuilder
   * @throws SAXException                 issue parsing XML file
   * @throws IOException                  issue reading from XML file
   */
  public StyleXMLParser(String fileName)
      throws ParserConfigurationException, SAXException, IOException {
    super(fileName);
  }

  /**
   * Parses data from the XML file and puts it in a map.
   *
   * @throws ParserConfigurationException issue configuring DocumentBuilder
   * @throws SAXException                 issue parsing XML file
   * @throws IOException                  issue reading from XML file
   */
  public void readFile() throws IOException, SAXException, ParserConfigurationException {
    buildParser();
    getInfo().put("GridType", retrieveTextContent("GridType"));
    getInfo().put("NeighborLayout", retrieveTextContent("NeighborLayout"));
    getInfo().put("CellSize", retrieveTextContent("CellSize"));
    getInfo().put("CellOutline", retrieveTextContent("CellOutline"));
    getInfo().put("GridPopulate", retrieveTextContent("GridPopulate"));
  }

  /**
   * Allows access to whether grid should be Square, Triangular, or Toroidal.
   *
   * @return type of grid
   */
  public String getGridType() {
    return getInfo().get("GridType");
  }

  /**
   * Allows access to the type of neighbors cells should have in the grid.
   *
   * @return neighbor layout type
   */
  public String getNeighborLayout() {
    return getInfo().get("NeighborLayout");
  }

  /**
   * Allows access to whether the grid should populate from a file or populate randomly.
   *
   * @return populate type
   */
  public String getPopulateType() {
    return getInfo().get("GridPopulate");
  }
}
