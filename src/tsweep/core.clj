(ns tsweep.core
  (:gen-class))

;; create board:
;to be changed by user
(def rows 6)

(defn create-board [rows set]
    (if (= (* rows rows) (count set))
        set
        (recur rows (conj set #{}))))

(def board (create-board rows [#{}]))

(defn l-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (not= 0 (rem square rows))
            (recur (assoc board square (conj (get board square) (dec square))) (dec square))
(recur board (dec square))))))

(defn b-add [coll]
 (loop [board coll square (dec (count coll))]
    (if (> 0 square)
board
(if (> (- (* rows rows) rows) square)
(recur (assoc board square (conj (get board square) (+ rows square))) (dec square))
(recur board (dec square))))))

(defn bl-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (> (- (* rows rows) rows)  square) (not= 0 (rem square rows)))
          (recur (assoc board square (conj (get board square) (dec (+ rows square)))) (dec square))
(recur board (dec square))))))

(defn br-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (> (- (* rows rows) rows)  square) (not= 0 (rem (inc square) rows)))
          (recur (assoc board square (conj (get board square) (inc (+ rows square)))) (dec square))
(recur board (dec square))))))

(defn r-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (not= 0 (rem (inc square) rows))
            (recur (assoc board square (conj (get board square) (inc square))) (dec square))
(recur board (dec square))))))

(defn t-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (< rows (inc square))
            (recur (assoc board square (conj (get board square) (- square rows))) (dec square))
(recur board (dec square))))))

(defn tl-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (< rows (inc square)) (not= 0 (rem square rows)))
          (recur (assoc board square (conj (get board square) (dec (- square rows)))) (dec square))
(recur board (dec square))))))

(defn tr-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (< rows (inc square)) (not= 0 (rem (inc square) rows)))
          (recur (assoc board square (conj (get board square) (inc (- square rows)))) (dec square))
(recur board (dec square))))))(defn l-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (not= 0 (rem square rows))
            (recur (assoc board square (conj (get board square) (dec square))) (dec square))
(recur board (dec square))))))

(defn b-add [coll]
 (loop [board coll square (dec (count coll))]
    (if (> 0 square)
board
(if (> (- (* rows rows) rows) square)
(recur (assoc board square (conj (get board square) (+ rows square))) (dec square))
(recur board (dec square))))))

(defn bl-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (> (- (* rows rows) rows)  square) (not= 0 (rem square rows)))
          (recur (assoc board square (conj (get board square) (dec (+ rows square)))) (dec square))
(recur board (dec square))))))

(defn br-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (> (- (* rows rows) rows)  square) (not= 0 (rem (inc square) rows)))
          (recur (assoc board square (conj (get board square) (inc (+ rows square)))) (dec square))
(recur board (dec square))))))

(defn r-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (not= 0 (rem (inc square) rows))
            (recur (assoc board square (conj (get board square) (inc square))) (dec square))
(recur board (dec square))))))

(defn t-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (< rows (inc square))
            (recur (assoc board square (conj (get board square) (- square rows))) (dec square))
(recur board (dec square))))))

(defn tl-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (< rows (inc square)) (not= 0 (rem square rows)))
          (recur (assoc board square (conj (get board square) (dec (- square rows)))) (dec square))
(recur board (dec square))))))

(defn tr-add [coll]
        (loop [board coll square (dec (count coll))]
    (if (> 0 square)
        board
        (if (and (< rows (inc square)) (not= 0 (rem (inc square) rows)))
          (recur (assoc board square (conj (get board square) (inc (- square rows)))) (dec square))
(recur board (dec square))))))

(def squares  (tl-add (t-add (tr-add (l-add (r-add (bl-add (b-add (br-add board)))))))))

;; populate the board with mines:

(defn set-mines [set n squares] 
  (if (= n (count set))
    set
        (recur (conj set (int (rand squares))) n squares)))


(def num-mines 6)
(def mines (set-mines #{} num-mines (count squares)))
(defn mine-detector [square]
  (count ((fn [a b] (set (filter #(contains? b %) a))) mines (set (squares square)))))

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
(if (< 99 square)
  (str (colorize "X" :green) square (colorize "X" :green))      
  (str (colorize "X" :green) square (colorize "XX" :green)))))

(defn full-blank [square] (str (colorize "XXXXX" :green)))
(defn clear-blank [square] (str "     "))
(def mined (colorize "  !!!  " :red))

(defn paint-blank [square]
  (if (contains? stepped square)
      (if (contains? mines square) mined (clear-blank square)) (full-blank square)))

(defn paint [square]
  (if (contains? stepped square)
      (if (contains? mines square)
          mined
          (clear square))
    (full square)))

(def board-rows (partition rows squares))

(defn painter []
  (println (map paint-blank (range rows)))
  (println (map paint (range rows)))
  (println (map paint-blank (range rows)))
(println (map paint-blank (range 6 12)))
  (println (map paint (range 6 12)))
  (println (map paint-blank (range 6 12)))
(println (map paint-blank (range 12 18)))
  (println (map paint (range 12 18)))
  (println (map paint-blank (range 12 18)))
(println (map paint-blank (range 18 24)))
  (println (map paint (range 18 24)))
  (println (map paint-blank (range 18 24)))
(println (map paint-blank (range 24 30)))
  (println (map paint (range 24 30)))
  (println (map paint-blank (range 24 30)))
(println (map paint-blank (range 30 36)))
  (println (map paint (range 30 36)))
  (println (map paint-blank (range 30 36))))

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
  (if (= (count squares)
         (+ (count stepped) (count mines))) 
      (println "GOOD JOB!")      
  (if (< 0 (count ((fn [a b] (set (filter #(contains? b %) a))) stepped mines)))
            (println "Boom. You died.")
      (do (println "Tread lightly, if you dare...")
                (let [square (Integer. (get-input))] (step square))
                (prompt-step)))))

(defn -main []
  (painter)
  (prompt-step))