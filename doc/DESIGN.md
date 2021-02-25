# Cell Society Design Final
### Names: Livia Seibert, Billy Luqiu, Lola Maglione

## Team Roles and Responsibilities

 * Team Member #1: Livia Seibert

    * My role was to create the model for the project. I created the XML config file layout
    and the XML stylesheet layout, as well as the classes to parse this XML. I also created
      the Cell and grid classes.

 * Team Member #2

 * Team Member #3: Lola Maglione
    
    * My role was to create the front-end. I was in charge of creating the GUI and implementing all the 
    visualization features. I wrote the GameLoop and Graphics classes. 


## Design goals

#### What Features are Easy to Add

## High-level Design

#### Core Classes

One of the core classes for the backend is the abstract Cell file. Cell contains the core
functionality for a basic cell type, and all the more specific cell types for different
simulations extend from it. Cell is also important because the Grid is composed of a 2-D
array of Cell objects, and can be used to create a grid filled with any cell type. Grid
is also a core class because it is the object that is updated to make the simulation run,
and it has two subclasses, ToroidalGrid and TriangularGrid, that extend its functionality
to create different types of grids. XMLReader, XMLParser, and StyleXMLParser are also important
because they read in the config files and setup the initial state of game play, passing the
needed parameters to each simulation type.

## Assumptions that Affect the Design

#### Features Affected by Assumptions


## Significant differences from Original Plan

The XML configuration files ended up looking a bit different than originally planned. In
the proposed XML style format, states had been declared in the XML and read in. However,
when we started implementing the code, we realized it made more sense to associate states
directly with the cells that they belong to because the list of states available in a given
simulation is something inherent to the game, and therefore unchangeable. We also chose to
put the starting grid layout in a .txt file instead of putting it directly in the XML because
we were familiar with .txt parsing from the Breakout project.

Another major difference from the plan is that cell classes do not have a reference to or
depend on the Grid class at all. We initially though that Cell would need to have a reference
to the grid it belonged to in order to determine its neighboring cells, but we quickly realized
that that choice led to a two-way dependency between Grid and Cell and was not a great design
choice to maximize encapsulation. In order to remedy this, we had Grid compile a list of the
neighbors of each cell and pass it back to the cell, as Grid already had access to all the cells
on the board and could easily determine the neighbors of any given cell.

Another difference from the plan is that the Graphics and the GameLoop classes are not divided equally
into all the visuals in the Graphics and only what need to run in the GameLoop (which was the original
plan). Instead, the GameLoop also contains a lot of things that affect the visual. This was mainly because
the GameLoop and Graphics class did not have a well defined job description, so it made it hard for me (lola)
to understand specifically where to draw the line. 

## New Features HowTo

#### Easy to Add Features

It's easy to add new simulation types to the game, as already-written classes do not have to be added
to in order to create these new simulations. The abstract Controller and abstract Cell class makes it
so that adding a new simulation type is as easy as creating a new Controller and Cell type for the new
simulation. Once XML config files are created for the new simulation, the new types of Cells can be used
to populate the Grid, and the new Controller can be used to update the Grid, which makes the simulation run.

#### Other Features not yet Done

The custom simulation screen is not completely done. While you can create a random layout for some basic
simulation types with it, it does not let you specify the starting layout manually or specify the neighbor
types for the custom simulation. Additionally, it does not yet have robust error handling to ensure that
the parameters selected will create a functional simulation.

