# Simulation Lab Discussion

## Rock Paper Scissors

## Names and NetIDs

Billy Luqiu (wyl6)

Lola Maglione (cm524)

Livia Seibert (las120)

### High Level Design Ideas
Player class in which they decide to play
Idea 1: What is played is an object of another class (i.e. Rock/Paper/Scissor object)
Rock/Paper/Scissor/etc inherits from parent class

Idea 2: General weapon class with type of weapon stored as a string (and a special data structure to store data about which weapon in a weapon pair wins)

Judger class that decides who won between two objects
Game class that is main driver for game

### CRC Card Classes

This class's purpose or value is create a player instance which will make moves :
```java
 public abstract class Player {
     public String chooseWeapon()
 }
```

This class's purpose or value is create a player instance which will make moves randomly:
```java
 public class randomPlayer extends Player{
     public String chooseWeapon()
 }
```

This class's purpose or value is create a player instance which will make moves based off user input :
```java
 public class manualPlayer extends Player {
     public String chooseWeapon()
 }
```

This class's purpose or value is create a player instance which will make moves based off some algorithm :
```java
 public class intelligentPlayer extends Player {
     public String chooseWeapon()
 }
```

This class's purpose or value is to be create a weapon of the types that are available from the data file:
```java
 public class Weapon {
     public void readFile(String fileName) //reads in weapons available for that game from data file
    public List<String> getAllWeaponsAvailable()

 }
```


This class's purpose or value is to be create a judger that decides which weapon wins for given weapons
```java
 public class Judger {
     public void parseWeaponPairings(List<String> weapons) //reads in which weapon wins
    public String decideWinner(List<String> weaponsPlayed)

 }
```

This class's purpose or value is to be create a game loop
```java
 public class Game {
     public void play(String type, int players)
     public void resetGame() 
     public void endGame()
 
 }
```



### Use Cases

* A new game is started with five players, their scores are reset to 0.
 ```java
play(“manual”, 5)
reset()
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
player.chooseWeapon()
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
game.play(“manual”, 3)
//inside game class
List<String> weapons = new ArrayList<String>()
//for each player add their weapon choice to weapons
judger.decideWinner(weapons)
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 weapon.readFile();
 judger.parseWeaponPairings(List<String> weapons)

 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
//create new game object 
 weapon.readFile();
 judger.parseWeaponPairings(List<String> weapons)
 ```
