package cellsociety.grid;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.util.List;

public class StyleXMLParser extends XMLReader {

  //ResourceBundle gridCellPairs = ResourceBundle.getBundle("GridCellPairs");

  public StyleXMLParser(String fileName)
      throws ParserConfigurationException, SAXException, IOException {
    super(fileName);
  }

  public void readFile() throws IOException, SAXException, ParserConfigurationException {
    buildParser();
    simulationData.put("Language", retrieveTextContent("Language"));
    simulationData.put("GridType", retrieveTextContent("GridType"));
    simulationData.put("CellShape", retrieveTextContent("CellShape"));
    simulationData.put("NeighborLayout", retrieveTextContent("NeighborLayout"));
    simulationData.put("CellSize", retrieveTextContent("CellSize"));
    simulationData.put("CellOutline", retrieveTextContent("CellOutline"));
    simulationData.put("CellColor", retrieveTextContent("CellColor"));
  }

  public String getGridType() {
    return simulationData.get("GridType");
  }

  public String getNeighborLayout() {
    return simulationData.get("NeighborLayout");
  }
}
