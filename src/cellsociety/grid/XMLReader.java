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

  private Element root;
  private Map<String, String> simulationData;
  private String fileName;

  /**
   * Constructor for XMLReader class, is never called because class is abstract but it is used by
   * subclasses.
   *
   * @param fileName name of XML file to parse
   * @throws ParserConfigurationException issue with DocumentBuilder creation
   * @throws SAXException                 issue parsing XML file
   * @throws IOException                  issue reading XML file
   */
  public XMLReader(String fileName) throws IOException, SAXException, ParserConfigurationException {
    this.simulationData = new HashMap<>();
    this.fileName = fileName;
  }

  public XMLReader(Map<String, String> simulationData) {
    this.simulationData = simulationData;
  }

  /**
   * Allows XMLReader subclasses to read from their specific file layouts.
   *
   * @throws ParserConfigurationException issue with DocumentBuilder creation
   * @throws SAXException                 issue parsing XML file
   * @throws IOException                  issue reading XML file
   */
  public abstract void readFile() throws ParserConfigurationException, SAXException, IOException;

  /**
   * Creates DocumentBuilder and DocumentBuilderFactory to read XML doc.
   *
   * @throws ParserConfigurationException issue with DocumentBuilder creation
   * @throws SAXException                 issue parsing XML file
   * @throws IOException                  issue reading XML file
   */
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
  protected String retrieveTextContent(String tagName) throws NullPointerException {
    NodeList node = root.getElementsByTagName(tagName);
    return node.item(0).getTextContent();
  }

  /**
   * Accesses simulation info parsed from XML file.
   *
   * @return simulationData HashMap with xml data
   */
  public Map<String, String> getInfo() {
    return simulationData;
  }

  /**
   * Allows XMLReader subclasses to get the root element of the document.
   *
   * @return root element of doc
   */
  protected Element getRoot() {
    return root;
  }
}
