package cellsociety.grid;

import java.util.Map;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Reads in and handles the data from the XML file.
 *
 * @author Livia Seibert
 */
public class XMLParser {

  private String fileName;
  private Map<String, String> simulationData;

  public XMLParser(String fileName) {
    this.fileName = fileName;
    this.simulationData = new HashMap<>();
  }

  public void readFile(String fileName) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream(fileName));
    Element root = doc.getDocumentElement();

    simulationData.put("Title", root.getAttribute("Title"));
    simulationData.put("Author", root.getAttribute("Author"));
    simulationData.put("Description", root.getAttribute("Description"));

    GridCreator gridCreator = new GridCreator(Integer.parseInt(root.getAttribute("width")),
        Integer.parseInt(root.getAttribute("height")), root.getAttribute("LayoutFile"),
        Type.valueOf(root.getAttribute("Type")));
  }
}
