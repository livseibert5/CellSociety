# Simulation Lab Discussion

## Breakout with Inheritance

## Names and NetIDs

Lola Maglione (cm524)

Billy Luqiu (wyl6)

Livia Seibert (las120)

### Block

Lola’s project already used inheritance for the brick class.

Inheritance was used to allow all types of bricks that extended Brick to use the same collision handling. That way, a single-hit class and a stronger brick class can be created easily from it since they function very similarly.

#### Subclasses (the Open part)

The subclasses are StrongBrick and SolidBrick. Both classes have an ifBallHitsBrick method, which is polymorphism. The difference between the two methods is that each handles collisions appropriately for that specific type of brick. Powerups also inherits from Brick and uses the ifBallHitsBrick method to apply the powerup to play when the brick is broken.

#### Affect on Game/Level class (the Closed part)

Allows for different types of bricks to perform different things depending on the type of brick

### Power-up

Discussed above, but also inherited from brick.

#### Subclasses (the Open part)


#### Affect on Game/Level class (the Closed part)



### Level

This superclass's purpose as an abstraction:
```java
 public class Level {
    public String readFile(String fileName) 
   // reads in the file for the given file for the given level 
     public remove bricks }
	// removes bricks
 
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Level {
     public ArrayList<Bricks> getBricksForLevel 
     // returns levels for the given level
     public void createLayout()
    // adds all the nodes for the given class 
    /
 }
```

#### Affect on Game class (the Closed part)

We have specific classes for each level and not a generalized one which could scale better


### Others?


Reroute some of the game logic away from the ball class
Extend existing javafx classes 
