Cell Society
====

This project implements a cellular automata simulator.

Names: Livia Seibert, Lola Maglione, Billy Luqiu

### Timeline

Start Date: February 7th

Finish Date: February 21st

Hours Spent: 80-100 hours

### Primary Roles
Livia - model
Billy - controller
Lola - view

### Resources Used
[Java Create and Write to Files](https://www.w3schools.com/java/java_files_create.asp)
[Java DOM Parser](https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm)

### Running the Program

Main class: GameLoop

Data files needed: 

* XML Config Files:
  * Antforaging.xml
  * antforaging2.xml
  * antforaging3.xml  
  * Sugarscape.xml
  * Firecorners.xml
  * Fireeverywhere.xml
  * Fireline.xml
  * Firestandard.xml
  * Fireedges.xml
  * Fireedges2.xml
  * Gameoflifebad.xml 
  * Gameoflifebeacon.xml
  * Gameoflifeblinker.xml
  * Gameoflifeglider.xml
  * Gameoflifepenta.xml
  * Gameoflifeedges.xml
  * Gameoflifeedges2.xml
  * Percolation1.xml
  * Percolationbestcase.xml
  * Percolationworstcase.xml
  * Percolationrectangle.xml
  * Percolationedges.xml
  * percolationedges2.xml 
  * Segregation.xml
  * Rectanglesegregation.xml
  * Segregation2.xml
  * Segregation3.xml
  * Segregationedges.xml
  * Segregationedges2.xml
  * Predatorprey1.xml
  * Predatorprey2.xml
  * Predatorprey3.xml
  * Predatorprey4.xml
  * Predatorpreyedges.xml
  * Predatorpreyedges2.xml
* TXT Layout Files:
    * Antforaging.txt
    * antforaging2.txt
    * antforaging3.txt  
    * Sugarscape.txt
    * Firecorners.txt
    * Fireeverywhere.txt
    * Fireline.txt
    * Firestandard.txt
    * Fireedges.txt
    * Fireedges2.txt
    * Gameoflifebad.txt
    * Gameoflifebeacon.txt
    * Gameoflifeblinker.txt
    * Gameoflifeglider.txt
    * Gameoflifepenta.txt
    * Gameoflifeedges.txt
    * Gameoflifeedges2.txt
    * Percolation1.txt
    * Percolationbestcase.txt
    * Percolationworstcase.txt
    * Percolationrectangle.txt
    * Percolationedges.txt
    * percolationedges2.txt
    * Segregation.txt
    * Rectanglesegregation.txt
    * Segregation2.txt
    * Segregation3.txt
    * Segregationedges.txt
    * Segregationedges2.txt
    * Predatorprey1.txt
    * Predatorprey2.txt
    * Predatorprey3.txt
    * Predatorprey4.txt
    * Predatorpreyedges.txt
    * Predatorpreyedges2.txt 
* XML Style Files:
  * trianglemoore.xml
  * toroidalmoore.xml
  * squareneumann.xml
  * squaremoore.xml

Features implemented:

* Game of Life Simulation
* Percolation Simulation
* Segregation Simulation
* WaTor Simulation
* Fire Simulation
* Different arrangements of neighbors
* Reading XML files
* Triangular Grid
* Square Grid
* Finite Grid
* Toroidal Grid
* XML file selector
* Foraging Ant Simulation
* Sugarscape Simulation
* Error Checking
* Saving current configuration state as XML file
* Randomized initial configuration
* Different languages
* Colors (in progress)

### Notes/Assumptions

Assumptions or Simplifications:

Interesting data files:

* empty.xml - completely empty config file, throws SAX error but does not crash program
* typelesssimulation.xml - required value type not specified, throws IllegalArgumentException but does not crash program
* firebad.xml - specifies an unrecognized parameter with an integer value instead of a double, throws no errors
* invalidstate.xml - fire simulation with unrecognized states in the txt file, gets rid of them and executes game play anyways
* celloutofbounds.xml - cells are specified out of bounds, runs normally

XML Layouts:
* XML config files
  * Simulation tags wrap around the entire body of the file
  * Type tags specify which type of simulation should be run, such as Fire Simulation, Percolation, etc.
  * Title specifies the name of the simulation
  * Author specifies the author of the simulation
  * Description gives a short description of the simulation config
  * ConfigParams is the parent element for a bunch of Param tags
    * each Param tag contains a Name and Value tag for the name and value of that parameter
  * Width specifies the width of the grid
  * Height specifies the height of the gri
  * LayoutFile contains the filename of the txt file with the initial layout
  * Style contains the filename of the xml stylesheet to pull from  
* XML stylesheets
  * Style tags wrap around the entire body of the file
  * GridType tags specify what shape the grid should be (Square, Triangle, Toroidal)
  * GridPopulate specifies whether the simulation grid should be populated from a txt file or chosen randomly (MANUAL or RANDOM)
  * NeighborLayout specifies what kind of neighbors each cell in a grid should have (see Neighbors enum)

Known Bugs:

Extra credit:

### Impressions
This project was time-intensive overall, but the second week was especially challenging.
The second week showed where we went wrong in our initial design, and we had to spend some
time refactoring that before adding on new features.
