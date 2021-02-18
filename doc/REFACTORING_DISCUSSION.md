## Refactoring Lab Discussion
### Team 09
### Names
Billy Luqiu
Livia Seibert
Lola Maglione

### Issues in Current Code


#### Method or Class GameLoop
* Very long createSecondLandingScreen method, and we decided to 
create new methods to reduce duplication in tasks by creating
  a separate method to set the button layouts.
  

* We currently list out all the XML files, but will create a file
chooser to reduce duplication in the code 

#### Method or Class
* Design issues

* Design issue

#### Other issues
* Final variables were being declared as private, so we changed them to public
static final variables
  
* We removed unnecessary import statements

* We went through the local variables should not be declared and then returned
and provided justification in the javadoc comments for why we felt they were appropriate
  
* Changed methods for isEmpty checking if array list is empty

* Extracted lines of code from method to make shorter methods

* We also refactored our classes to remove all instanceof methods

### Refactoring Plan

* What are the code's biggest issues?

  * We will fix long methods by introducing a custom XML parser and we delegated tasks
  so our methods only have one purpose and don't try to do too many things at once in line with the 
    single responsibility principles
    
  * We removed all usages of instanceof variables by checking the cell states before we acted on them, 
  this removed the need to check if they were instances of specific cells
    
  * We changed private final variables to static variables and are calling them as appropriate, as we 
  removed a lot of hard coded values in our code.
    
  * We fixed the minor issues regarding java syntax and coding best practices.
  
  * the most important part of this week is fixing our game loop and graphics class
  to make it more flexible as they currently are not able to adapte well to different situations

* Which issues are easy to fix and which are hard?

* What are good ways to implement the changes "in place"?


### Refactoring Work

* Issue chosen: Fix and Alternatives
Instance of issues, we decided to change the cells in the controller class
  to check the states. This will ensure that we are able to cast the cells to the correct classes.
  We chose this as the controllers are all designated to a specific cell type, so we only have to check to make sure
  it's not an empty cell, rather than checking instanceof types, which is poor code design.

* Issue chosen: Fix and Alternatives We are refactoring the game loop and graphics class into separate classes,
and decided to do this as we need to create more flexibility for future work this week. We decided to do this
  as it would give us the greatest amount of flexibility, and keeping the classes as is would not allow for easy
  implementation of the requirements for this week. 