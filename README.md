To play, just download the .jar file and launch with:

    $ java -jar PATH/mine-0.1.0-SNAPSHOT-standalone.jar

where PATH points to the folder it's in.

[![asciicast](https://asciinema.org/a/177672.png)](https://asciinema.org/a/177672)

## Or, from source:

Install Leiningen, clone this repo and enter (from its directory):

    $ lein run

## How it works:

The grid size is configurable, and is represented as an indexed vector of hashsets which are
dynamically assembled by a series of functions that determine where the edges of the board are for that configuration.

A random set of integers is chosen to represent the squares with mines, which are uncovered as they are stepped on to indicate how many mines are surrounding them.

