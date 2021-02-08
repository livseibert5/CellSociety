package cellsociety.grid;

import java.io.IOException;
import java.util.List;
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

  public void readFile() throws ParserConfigurationException, SAXException, IOException {
    buildParser();
    simulationData.put("Title", retrieveTextContent("Title"));
    simulationData.put("Author", retrieveTextContent("Author"));
    simulationData.put("Description", retrieveTextContent("Description"));

    GridCreator gridCreator = new GridCreator(Integer.parseInt(retrieveTextContent("Width")),
        Integer.parseInt(retrieveTextContent("Height")), retrieveTextContent("LayoutFile"),
        Type.valueOf(retrieveTextContent("Type")));
    grid = gridCreator.getGrid();
  }

  private String retrieveTextContent(String tagName) {
    NodeList node = root.getElementsByTagName(tagName);
    return node.item(0).getTextContent();
  }

  public Grid getGrid() {
    return grid;
  }

  public Map<String, String> getInfo() {
    return simulationData;
  }
}
