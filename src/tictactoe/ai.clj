(ns tictactoe.ai
  (:use tictactoe.core))

(declare memoized-best-scored-board)

(defn- make-scored-board [score move board]
  {:score score :move move :board board})

(defn- score-board [board move mark depth]
  (let [next-board (assoc board move mark)]
    (cond
      (= mark (winner next-board)) (make-scored-board (- 10 depth) move board)
      (full? next-board) (make-scored-board 0 move board)
      :else (make-scored-board (- (:score (memoized-best-scored-board next-board (next-mark mark) (inc depth))))
                               move
                               board))))

(defn- scored-board-compare [a b]
  (if (> (:score b) (:score a)) b a))

(defn- child-scored-boards [board mark depth]
  (map
    #(score-board board % mark depth)
    (empty-squares board)))

(defn- best-scored-board [board mark depth]
  (reduce
    scored-board-compare
    (child-scored-boards board mark depth)))

(def memoized-best-scored-board (memoize best-scored-board))

;;; public functions

(defn computer-mover [board mark]
  (let [best-board (memoized-best-scored-board board mark 0)]
    (:move best-board)))
