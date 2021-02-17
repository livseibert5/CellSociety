package cellsociety.grid;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class StyleXMLParser extends XMLReader {

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
}
