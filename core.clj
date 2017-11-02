(ns mine.core
  (:gen-class))

;; We define a map of squares,
;; each key corresponding to a vector of its surrounding squares. 
(declare square)

(def squares
  {0 [1 8 9] 1 [0 2 8 9 10] 2 [1 3 9 10 11] 3 [2 4 10 11 12]
   4 [3 5 11 12 13] 5 [4 6 12 13 14] 6 [5 7 13 14 15] 7 [6 14 15]
   8 [0 1 9 16 17] 9 [0 1 2 8 10 16 17 18] 10 [1 2 3 9 11 17 18 19]
   11 [2 3 4 10 12 18 19 20] 12 [3 4 5 11 13 19 20 21]
   13 [4 5 6 12 14 20 21 22] 14 [5 6 7 13 15 21 22 23]
   15 [6 7 14 22 23] 16 [8 9 17 24 25] 17 [8 9 10 16 18 24 25 26]
   18 [9 10 11 17 19 25 26 27] 19 [10 11 12 18 20 26 27 28]
   20 [11 12 13 19 21 27 28 29] 21 [12 13 14 20 22 28 29 30]
   22 [13 14 15 21 23 29 30 31] 23 [14 15 22 30 31] 24 [16 17 25 32 33]   25 [16 17 18 24 26 32 33 34] 26 [17 18 19 25 27 33 34 35]
   27 [18 19 20 26 28 34 35 36] 28 [19 20 21 27 29 35 36 37]
   29 [20 21 22 28 30 36 37 38] 30 [21 22 23 29 30 37 38 39]
   31 [22 23 30 38 39] 32 [24 25 33 40 41] 33 [24 25 26 32 34 40 41 42]   34 [25 26 27 33 35 41 42 43] 35 [26 27 28 34 36 42 43 44]
   36 [27 28 29 35 37 43 44 45] 37 [28 29 30 36 38 44 45 46]
   38 [29 30 31 37 39 45 46 47] 39 [30 31 38 46 47] 40 [32 33 41 48 49]   41 [32 33 34 40 42 48 49 50] 42 [33 34 35 41 43 49 50 51]
   43 [34 35 36 42 44 50 51 52] 44 [35 36 37 43 45 51 52 53]
   45 [36 37 38 44 46 52 53 54] 46 [37 38 39 45 47 53 54 55]
   47 [38 39 46 54 55] 48 [40 41 49 56 57] 49 [40 41 42 48 50 56 57 58]   50 [41 42 43 49 51 57 58 59] 51 [42 43 44 50 52 58 59 60]
   52 [43 44 45 51 53 59 60 61] 53 [44 45 46 52 54 60 61 62]
   54 [45 46 47 53 55 61 62 63] 55 [46 47 54 62 63] 56 [48 49 57]
   57 [48 49 50] 58 [49 50 51 57 59] 59 [50 51 52 58 60] 
   60 [51 52 53 59 61] 61 [52 53 54 60 62] 62 [53 54 55 61 63]
   63 [54 55 62]})

; To retrieve any square's vector:
; user> (squares 0)
; [1 8 9]

;; populate the board with mines

(defn add-mine [set] (conj set (int (rand 64))))

(defn set-mines [] (add-mine (add-mine (add-mine (add-mine (add-mine (add-mine (add-mine (add-mine #{})))))))))

(def mines (set-mines))

;; mine detector calculates how many mines are around each square

(defn mine-detector [square]
  (count ((fn [a b] (set (filter #(contains? b %) a))) mines (set (squares square)))))

;; render rows

(def ansi-styles
  {:red   "[31m"
   :green "[32m"
   :blue  "[34m"
   :reset "[0m"})

(defn ansi
  "Produce a string which will apply an ansi style"
  [style]
  (str \u001b (style ansi-styles)))

(defn colorize
  "Apply ansi color to text"
  [text color]
  (str (ansi color) text (ansi :reset)))

(def stepped #{})

(defn clear [square]
  (do 
(if (= 0 (mine-detector square))
(def stepped (into stepped (squares square))))
(if (= 0 (mine-detector square))
(str "     ")
(str "  " (colorize (str (mine-detector square) ) :blue) "  "))))

(defn full [square]
(if (> 10 square)
(str (colorize "XX" :green) square (colorize "XX" :green))
    (str (colorize "X" :green) square (colorize "XX" :green))))

(defn full-blank [square] (str (colorize "XXXXX" :green)))
(defn clear-blank [square] (str "     "))
(def mined (colorize "  !!!  " :red))




(defn paint-blank [square]
(if (contains? stepped square)
      (if (contains? mines square) mined (clear-blank square)) (full-blank square)))

(defn paint [square]
  (if (contains? stepped square)
      (if (contains? mines square) mined (clear square)) (full square)))

(defn painter [] (do (println (map paint-blank (range 8))) (println (map paint (range 8))) (println (map paint-blank (range 8))) (println (map paint-blank (range 8 16))) (println (map paint (range 8 16))) (println (map paint-blank (range 8 16))) (println (map paint-blank (range 16 24))) (println (map paint (range 16 24))) (println (map paint-blank (range 16 24))) (println (map paint-blank (range 24 32))) (println (map paint (range 24 32))) (println (map paint-blank (range 24 32))) (println (map paint-blank (range 32 40))) (println (map paint (range 32 40))) (println (map paint-blank (range 32 40))) (println (map paint-blank (range 40 48))) (println (map paint (range 40 48))) (println (map paint-blank (range 40 48))) (println (map paint-blank (range 48 56))) (println (map paint (range 48 56))) (println (map paint-blank (range 48 56))) (println (map paint-blank (range 56 64))) (println (map paint (range 56 64))) (println (map paint-blank (range 56 64)))))

(defn step [square] (do (def stepped (conj stepped square)) (painter)))

;; interaction

(def stepped #{})
(defn step [square] (do (def stepped (conj stepped square)) (painter)))

(defn get-input
  "Waits for user to enter text and hit enter, then cleans the input"
  ([] (get-input ""))
  ([default]
     (let [input (clojure.string/trim (read-line))]
       (if (empty? input)
         default
         (clojure.string/lower-case input)))))


(defn prompt-step [] 
  (if (= 64 (+ (count stepped) (count mines))) 
(do (println "8888888888  88      88  8888888888  88       88")
(println "88          88      88  888         88      88")
(println "88          88      88  88          88     88")
(println "88          88      88  88          88    88")
(println "8888888     88      88  88          88   88")
(println "88          88      88  88          88 88")
(println "88          88      88  88          8888      ")
(println "88          88      88  88          8888")
(println "88          88      88  88          88 88")
(println "88          88      88  88          88  88")
(println "88          88      88  88          88    88")
(println "88           88    88   88          88     88")
(println "88            888888    888888888   88      888")
(println "88      88 888888888      88       88   88   88")
(println " 88    88  88            8888      88   88   88 ")
(println "  88  88   88           88  88     88   88   88")
(println "   8888    8888888     88    88    88   88   88")
(println "    88     88         8888888888   8888888   88")
(println "    88     88        88        88  88   88   88")
(println "    88     88        88        88  88   88   ")
(println "    88     88888888  88        88  88   88   88"))      
      
 (if (< 0 (count ((fn [a b] (set (filter #(contains? b %) a))) stepped mines)))
            (println "You died. Fucking asshole!")
(do (println "Tread lightly, if you dare. [0-63]")
                (let [square (Integer. (get-input))] (step square))
                (prompt-step)))))
      
(defn -main []
 (do (painter) (prompt-step)))
