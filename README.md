# To run:

Install [Clojure CLI tools](https://clojure.org/guides/getting_started) and enter (from the project directory):

    $ clj -m tsweep
    
Or run it with Java:
 
     $ java -jar mine-0.1.0-SNAPSHOT-standalone.jar

## How it works:

A random set of integers is chosen to represent the mines, which are uncovered as they are stepped on to reveal the number of mines contained in the adjacent squares. The board's internal representation is dynamically generated based on a given number of rows (set here to 6).

[![asciicast](https://asciinema.org/a/177672.png)](https://asciinema.org/a/177672)
