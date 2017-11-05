(ns mine.core
  (:gen-class))

;; create board:
;to be changed by user
(def rows 12)

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


;this will be changed later to be entered by the user
(def num-mines 10)

(def mines (set-mines #{} num-mines (count squares)))

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

;; need a new painter func to show correct num squares.

; It needs to:
; Divide the board into rows.
; paint each row.

; partition board

(def board-rows (partition rows squares))

(defn painter []
  (println (map paint-blank (range rows)))
  (println (map paint (range rows)))
  (println (map paint-blank (range rows)))
(println (map paint-blank (range 12 24)))
  (println (map paint (range 12 24)))
  (println (map paint-blank (range 12 24)))
(println (map paint-blank (range 24 36)))
  (println (map paint (range 24 36)))
  (println (map paint-blank (range 24 36)))
(println (map paint-blank (range 36 48)))
  (println (map paint (range 36 48)))
  (println (map paint-blank (range 36 48)))
(println (map paint-blank (range 48 60)))
  (println (map paint (range 48 60)))
  (println (map paint-blank (range 48 60)))
(println (map paint-blank (range 60 72)))
  (println (map paint (range 60 72)))
  (println (map paint-blank (range 60 72)))
(println (map paint-blank (range 72 84)))
  (println (map paint (range 72 84)))
  (println (map paint-blank (range 72 84)))
(println (map paint-blank (range 84 96)))
  (println (map paint (range 84 96)))
  (println (map paint-blank (range 84 96)))
(println (map paint-blank (range 96 108)))
  (println (map paint (range 96 108)))
  (println (map paint-blank (range 96 108)))
(println (map paint-blank (range 108 120)))
  (println (map paint (range 108 120)))
  (println (map paint-blank (range 108 120)))
(println (map paint-blank (range 120 132)))
  (println (map paint (range 120 132)))
  (println (map paint-blank (range 120 132)))
(println (map paint-blank (range 132 144)))
  (println (map paint (range 132 144)))
  (println (map paint-blank (range 132 144)))
)



;(defn painter []
;  (do
;
;    (println (map paint (range 2)))
;    (println (map paint-blank (range 2)))
;    (println (map paint-blank (range 2 4)))
;    (println (map paint (range 2 4)))
;    (println (map paint-blank (range 2 4)))))



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
  (if (= (count squares) (+ (count stepped) (count mines))) 
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
(do (println "Tread lightly, if you dare...")
                (let [square (Integer. (get-input))] (step square))
                (prompt-step)))))
      
(defn -main []
 (do (painter) (prompt-step)))
