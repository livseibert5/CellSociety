package cellsociety.grid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Allows for basic XML parsing ability so that StyleXMLParser and XMLParser can inherit from it and
 * not duplicate code.
 *
 * @author Livia Seibert
 */
public abstract class XMLReader {

  protected Element root;
  protected Map<String, String> simulationData;
  protected String fileName;

  public XMLReader(String fileName) throws IOException, SAXException, ParserConfigurationException {
    this.simulationData = new HashMap<>();
    this.fileName = fileName;
  }

  public abstract void readFile() throws ParserConfigurationException, SAXException, IOException;

  protected void buildParser() throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream(fileName));
    root = doc.getDocumentElement();
  }

  /**
   * Gets text values from the tag with the given name.
   *
   * @param tagName name of tag
   * @return text value from tag
   */
  protected String retrieveTextContent(String tagName) {
    try {
      NodeList node = root.getElementsByTagName(tagName);
      return node.item(0).getTextContent();
    } catch (NullPointerException e) {
      return tagName;
    }
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
