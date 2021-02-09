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
        XMLParser parser = new XMLParser("percolationworstcase.xml");
        try {
            parser.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
