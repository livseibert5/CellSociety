# Cell Society Design Final
### Names Livia Seibert, Billy Luqiu, Lola Maglione

## Team Roles and Responsibilities

 * Team Member #1: Livia Seibert

    * My role was to create the model for the project. I created the XML config file layout
    and the XML stylesheet layout, as well as the classes to parse this XML. I also created
      the Cell and grid classes.

 * Team Member #2

 * Team Member #3


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

## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

