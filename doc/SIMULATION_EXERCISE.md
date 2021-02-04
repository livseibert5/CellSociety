
# Simulation Lab Discussion

## Cell Society

## Names and NetIDs
Livia Seibert (las120)
Billy Luqiu (wyl6)


### High Level Design Ideas

How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update? Create a temporary copy of the array for a given state so that we can just update the array based off the current array


What relationship exists between a Cell and a simulation's rules? A cell is updated based off the simulation rules


What is the grid? Does it have any behaviors? Who needs to know about it? A grid represents the collection of cells. Visualization needs to know about it in order to know what to display. Simulation controller.


What information about a simulation needs to be the configuration file? Starting layout, starting dimensions, possible states, update rules


How is the graphical view of the simulation updated after all the cells have been updated? Update the visuals in a separate step after all simulations have updated (similar to rising/falling edge in CPU design)


Q1:
Cell class
Grid class
Simulations
Visual Graphic

### CRC Card Classes

This class's purpose or value is to manage cell content/data and allow for other Cell classes to inherit from it with different types:
```java
public class Cell {
     public int getType() 
     public int[] getLocation()
    
 }
```

This class's purpose or value is to organize the data all Cell types
```java
public class Grid {
     // updates the information based on new data 
     public Cell[][] getArrayOfCells()	
    
    public void setCell(Cell)

   public Grid getCopy()
 }
```


This class's purpose or value is to update the Cell types
```java
public class Simulation {
     // updates the information based on new data 
     public Cell[][] getArrayOfCells()	
    
    public void setCell(Cell)

   public Grid getCopy()
 }
```

Ran out of time


### Use Cases


* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML file
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Switch simulations: use the GUI to change the current simulation from Game of Life to Wa-Tor
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```
