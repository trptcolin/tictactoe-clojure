(ns tictactoe.core)

(def win-sets
  [[0 1 2], [3 4 5], [6 7 8], [0 3 6], [1 4 7], [2 5 8], [0 4 8], [2 4 6]])

(defn- winner-exists-on-spaces? [board spaces]
  (and
    (apply = (map #(board %) spaces))
    (not (= " " (board (first spaces))))))

(defn- winner-on-spaces [board spaces]
  (if (winner-exists-on-spaces? board spaces)
    (board (first spaces))
    nil))

(defn- indexed [coll]
  (map vector coll (iterate inc 0)))

(defn- index-filter [pred coll]
  (for [[spot i] (indexed coll) :when (pred spot)] i))

;;; public functions

(defn make-board [] (vec (repeat 9 " ")))

(defn empty-square? [square]
  (= " " square))

(defn full? [board] (not-any? empty-square? board))

(defn winner [board] (some #(winner-on-spaces board %) win-sets))

(defn game-over? [board] (boolean (or (winner board) (full? board))))

(defn place-on-board [board spot mark] (assoc board spot mark))

(defn next-mark [mark] (if (= "X" mark) "O" "X"))

(defn valid-move? [board move]
  (try (= (board move) " ")
    (catch Exception e false)))

(defn final-message [winner]
  (if (nil? winner)
    "Cat's Game!"
    (str winner " Wins!!!")))

(defn empty-squares [board]
  (index-filter empty-square? board))
