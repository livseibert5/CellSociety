package cellsociety;

import cellsociety.grid.XMLParser;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        XMLParser parser = new XMLParser("fireline.xml");
        try {
            parser.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
