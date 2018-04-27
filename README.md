# To run:

Install [Clojure CLI tools](https://clojure.org/guides/getting_started) and enter (from the project directory):

    $ clj -m tsweep
    
Or run it with Java:
 
     $ java -jar tsweep.jar

## How it works:

A random set of integers is chosen to represent the mines, which are uncovered as they are stepped on to reveal the number of mines contained in the adjacent squares. The board's internal representation is dynamically generated based on a variable number of rows.

[![asciicast](https://asciinema.org/a/177672.png)](https://asciinema.org/a/177672)
