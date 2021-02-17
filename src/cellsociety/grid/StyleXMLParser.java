package cellsociety.grid;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.util.List;

public class StyleXMLParser extends XMLReader {

  ResourceBundle gridCellPairs = ResourceBundle.getBundle("GridCellPairs");

  public StyleXMLParser(String fileName) {
    super(fileName);
  }

  public void readFile() throws IOException, SAXException, ParserConfigurationException {
    buildParser();
    simulationData.put("Language", retrieveTextContent("Language"));
    simulationData.put("CellShape", retrieveTextContent("CellShape"));
    simulationData.put("NeighborLayout", retrieveTextContent("NeighborLayout"));
    simulationData.put("CellSize", retrieveTextContent("CellSize"));
    simulationData.put("CellOutline", retrieveTextContent("CellOutline"));
    simulationData.put("CellColor", retrieveTextContent("CellColor"));
  }

  private void parseGridCellPairs() {
    if (!gridCellPairs.containsKey(simulationData.get("CellShape"))) {
      simulationData.put("CellShape", "Square");
    } else {
      if (!((List) gridCellPairs.getObject(simulationData.get("CellShape"))).contains(simulationData.get("NeighborLayout"))) {
        simulationData.put("CellShape", ((List) gridCellPairs.getObject(simulationData.get("CellShape"))).get(0).toString());
      }
    }
    gridCellPairs.getObject(simulationData.get("CellShape"));
  }
}
