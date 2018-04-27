(ns tsweep
  (:gen-class))

(def rows 6)

(defn create-board [rows set]
    (if (= (* rows rows) (count set))
        set
        (recur rows (conj set #{}))))

(def board (create-board rows [#{}]))

(defn left [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (not= 0 (rem square rows))
            (recur (assoc board square
			         (conj (get board square)
					       (dec square)))
		    (dec square))
        (recur board (dec square))))))

(defn right [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (not= 0 (rem (inc square) rows))
            (recur (assoc board square
                     (conj (get board square)
                       (inc square)))
            (dec square))
        (recur board (dec square))))))

(defn top [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (< rows (inc square))
            (recur (assoc board square
              (conj (get board square)
                (- square rows)))
            (dec square))
        (recur board (dec square))))))

(defn bottom [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (> (- (* rows rows) rows) square)
          (recur (assoc board square
                   (conj (get board square)
                         (+ rows square)))
          (dec square))
        (recur board (dec square))))))

(defn top-left [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (and (< rows (inc square))
                 (not= 0 (rem square rows)))
            (recur (assoc board square
                     (conj (get board square)
                       (dec (- square rows))))
            (dec square))
    (recur board (dec square))))))

(defn top-right [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (and (< rows (inc square))
                 (not= 0 (rem (inc square) rows)))
            (recur (assoc board square
              (conj (get board square)
                (inc (- square rows))))
            (dec square))
    (recur board (dec square))))))

(defn bottom-left [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (and (> (- (* rows rows) rows) square)
                 (not= 0 (rem square rows)))
            (recur (assoc board square
                     (conj (get board square)
                       (dec (+ rows square))))
            (dec square))
        (recur board (dec square))))))

(defn bottom-right [coll]
  (loop [board coll square (dec (count coll))]
    (if (> 0 square)
         board
        (if (and (> (- (* rows rows) rows) square)
                 (not= 0 (rem (inc square) rows)))
            (recur (assoc board square
                     (conj (get board square)
                       (inc (+ rows square))))
            (dec square))
        (recur board (dec square))))))

(def squares
  (-> board
	(left)
	(right)
	(top)
	(bottom)
	(top-left)
	(top-right)
	(bottom-left)
	(bottom-right)))

(defn set-mines [set n squares] 
  (if (= n (count set))
       set
      (recur (conj set (int (rand squares)))
        n squares)))

(def num-mines 4)

(def mines
  (set-mines #{} num-mines
    (count squares)))

(defn mine-detector [square]
  (count
    ((fn [a b] (set (filter #(contains? b %) a)))
        mines (set (squares square)))))

(def stepped (atom #{}))

(defn clear [square] 
    (if (= 0 (mine-detector square))
        (reset! stepped (into @stepped (squares square))))
        (if (= 0 (mine-detector square))
            (str "     ")
            (str "  " (mine-detector square) "  ")))

(defn full [square]
  (if (> 10 square)
      (str "XX" square "XX")
      (if (< 99 square)
          (str "X" square "X")      
          (str "X" square "XX"))))

(defn full-blank [square] (str"XXXXX"))

(defn clear-blank [square] (str "     "))

(def mined "  !!!  ")

(defn paint-blank [square]
  (if (contains? @stepped square)
      (if (contains? mines square)
           mined
          (clear-blank square))
      (full-blank square)))

(defn paint [square]
  (if (contains? @stepped square)
      (if (contains? mines square)
           mined
          (clear square))
      (full square)))

(def board-rows (partition rows squares))

(defn render [start end]
  (println (map paint-blank (range start end)))
  (println (map paint (range start end)))
  (println (map paint-blank (range start end))))

(defn render-board []
  (render 0 6)
  (render 6 12)
  (render 12 18)
  (render 18 24)
  (render 24 30)
  (render 30 36))

(defn step [square]
  (reset! stepped (conj @stepped square))
  (render-board))

(defn get-input
  "Waits for user to enter text and hit enter, then cleans the input"
  ([] (get-input "0"))
  ([default]
     (let [input (clojure.string/trim (read-line))]
       (if (empty? input)
         default
         (clojure.string/lower-case input)))))

(defn prompt [] 
  (if (= (count squares)
         (+ (count @stepped) (count mines))) 
      (println "GOOD JOB!")      
  (if (< 0 (count ((fn [a b] (set (filter #(contains? b %) a))) @stepped mines)))
            (println "Bam. You blew up.")
      (do (println "Tread lightly, if you dare...")
                (let [square (Integer. (get-input))] (step square))
                (prompt)))))

(defn -main []
  (render-board)
  (prompt))
