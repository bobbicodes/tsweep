Text-based minesweeper written in Clojure.

To play, just download the .jar file and type into your terminal:

java -jar PATH/mine-0.1.0-SNAPSHOT-standalone.jar

where PATH points to the folder it's in.

![](https://github.com/sdfwer124/Terminal-Sweeper/blob/master/mine1.png)

## Or, from source:

Install Leiningen, clone this repo and enter (from its directory):

    $ lein run

## How it works:

A random set of integers is chosen to represent the squares with mines,
to be placed on a square grid made up of a variable number of rows.
Squares are uncovered as they are stepped on to indicate how many mines are surrounding it.
The grid is an indexed vector of sets, each set made up of its square's adjacent squares.
A series of functions populate each square's set with its appropriate surrounding squares,
in respect to where the edges of the board are for the chosen grid size.
