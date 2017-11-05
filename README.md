# mine
Text-based minesweeper written in Clojure.

To play, just download the .jar file and type into your terminal:

java -jar PATH/mine-0.1.0-SNAPSHOT-standalone.jar

where PATH points to the folder it's in.

![](https://github.com/sdfwer124/Terminal-Sweeper/blob/master/mine1.png)

This was a fun problem that turns out to be way more of a challenge than you might think.
A random set of integers is chosen to represent the squares with mines,
to be placed on a square grid made up of a variable number of rows.
Squares are uncovered as they are stepped on, 
and then indicate how many mines are located in its 8 surrounding squares.
That's where it starts to get interesting...

The grid is represented as an indexed vector of sets,
each set made up of its square's adjacent squares.
I began hard-coding this data structure for a fixed-size grid,
but soon realized that it could be assembled using an algorithm
for a map with a variable number of rows.

I wrote a series of functions that populate each square's set with its appropriate surrounding squares,
each having to take into account whether it is indeed connected to it,
a condition that depends on where the edges of the board are for the particular grid size.

Then there is the problem of recursively clearing away the squares that have been stepped on
for which its set of surrounding squares contain no mines.
I'm still trying to figure out a way to do this,
because as it is, not all the squares are cleared that should be,
and while I think I understand why,
nothing that I do seems to fix it.
However, it still works and manages to be quite enjoyable to play.
