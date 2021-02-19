package cellsociety.grid;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class StyleXMLParser extends XMLReader {

  public StyleXMLParser(String fileName)
      throws ParserConfigurationException, SAXException, IOException {
    super(fileName);
  }

  public void readFile() throws IOException, SAXException, ParserConfigurationException {
    buildParser();
    getInfo().put("Language", retrieveTextContent("Language"));
    getInfo().put("GridType", retrieveTextContent("GridType"));
    getInfo().put("CellShape", retrieveTextContent("CellShape"));
    getInfo().put("NeighborLayout", retrieveTextContent("NeighborLayout"));
    getInfo().put("CellSize", retrieveTextContent("CellSize"));
    getInfo().put("CellOutline", retrieveTextContent("CellOutline"));
    getInfo().put("CellColor", retrieveTextContent("CellColor"));
    getInfo().put("GridPopulate", retrieveTextContent("GridPopulate"));
  }

  public String getGridType() {
    return getInfo().get("GridType");
  }

  public String getNeighborLayout() {
    return getInfo().get("NeighborLayout");
  }

  public String getPopulateType() {
    return getInfo().get("GridPopulate");
  }
}
