# ECS414U 2024/25 Final project
## by Emman Ruiz Cunanan Medina
## Horse Race Simulator program
A horse race simulation program written in Java as part of the final project for ECS414U - Object Oriented Programming.

### Dependencies
* Java 22.0.0.1 or later
* Visual Studio Code (or any suitable IDE)
* A command-line (e.g. Windows PowerShell/Command Prompt for Windows)

### Part 1

This version of the simulation program is more minimalist as it's intended to be run on the command-line. The code for this version is located in the `Part1` folder of this repository.

To run Part 1, go the `Part1` folder, open the command line from that folder, and then enter these commands:

To compile the main file:<br>
```
javac Main.java
```
<br>

To run the main file:<br>
```
java Main
```

This runs the `Main.class` which has the compiled code for starting a new race. It calls the `startRace()`method from the compiled `Race` class to do this.


### Part 2

This version of the horse simulation program has more  features allowing users to customise their horses and several parts of the race including the race length and the race track.<br><br>
Users can also view their horses' stats such as their confidence levels and win ratings, and also view past race results in which they have won.<br><br>
The program also features a betting system where users can place any amount of currency to bet on a horse and win or lose money based on the outcome of the race. The amount of money won from the bet depends on the betting odds of the horse.<br><br>

To run Part 2, go the `Part2` folder, open the command line from that folder, and then enter these 2 commands:

To compile the main file:<br>
```
javac MainV2.java
```
<br>

To run the main file:<br>
```
java MainV2
```


### GUI usage

Upon running, the program should display a "New race" window, prompting you to set up a new race by setting a racetrack length and adding new horses. After setting up a new race, the main window should pop up which shows the race track with all of the horse symbols and all of the option buttons, with one of them to start the race.

### CSV files included in the program

The GUI program also comes with CSV and text files. We use them to store all of the current horses, current race information, previous race results, bets and race track and equipment information.

The files `Race.txt`, `Horses.csv`, `BetHistory.csv` and `PastRaces.csv` in the `Part2` folder are empty by default, however, these files can be written when the user adds a horse, sets up a new race, places a bet and when the program records race results.

The files `EquipmentList.csv` and `TrackTypes.csv` already come pre-loaded with some equipment and track type information. You can add new tracks and your own customised equipment by adding new CSV data values to the files, by following the structure below.

For the `EquipmentList.csv` file:<br>
```
<ID (this can be anything)>,<Name>,<Fast movement probability increase>,<Stability increase (reduces probability of the horse falling)>
```

For the `TrackTypesList.csv` file:<br>
```
<ID (this can be anything)>,<Name>,<Base fast movement probability>,<Base horse fall probability>
```
