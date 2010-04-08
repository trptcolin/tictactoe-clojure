(ns tictactoe.console
  (:use
    [tictactoe.core :only [valid-move? place-on-board make-board final-message game-over? winner]]
    [tictactoe.ai :only [computer-mover]]))


;;; basic utility functions
(defn include? [coll item]
  (some #(= item %) coll))

(defn read-int []
  (try (Integer/parseInt (read-line))
    (catch NumberFormatException e nil)))

(def user-message println)
(defn user-prompt [x] (print x) (flush))

(defn- console-suffix [x]
  (cond (> x 7) nil
    (= 2 (mod x 3)) "\n-----------\n"
    :else "|"))

(defn- board-str [board]
  (apply str
    (map
      #(str " " (board %) " " (console-suffix %))
      (range 9))))

(defn- instructions-str []
  (str "\n"
    "Welcome to Tic-Tac-Toe!\n\n"
    "Use the numbers below for moves:\n\n"
    (board-str (vec (range 1 10)))
    "\n"))

(defn- get-valid-move [board player]
  (let [move ((:mover player) board (:player-mark player))]
    (if (valid-move? board move)
      (place-on-board board move (:player-mark player))
      (recur board player))))

(declare game-types)
(defn- get-console-players []
  (user-message "Game types:")
  (user-message)
  (user-message "  1- Human (X) vs. Human (O)")
  (user-message "  2- Human (X) vs. Computer (O)")
  (user-message "  3- Computer (X) vs. Human (O)")
  (user-message "  4- Computer (X) vs. Computer (O)")
  (user-message)
  (user-prompt "Pick a game type: ")
  (let [input (read-int)]
    (if (and input (include? (range 1 5) input))
      (game-types input)
      (recur))))

;;; public functions

(defn human-mover [board mark]
  (user-message)
  (user-message (board-str board))
  (user-message)
  (user-prompt "Pick your space (1-9): ")
  (flush)
  (let [input (read-int)]
    (if (and input (include? (range 1 10) input))
      (dec input)
      (recur board mark))))

(def game-types
  {1 [{:player-mark "X" :mover human-mover} {:player-mark "O" :mover human-mover}]
   2 [{:player-mark "X" :mover human-mover} {:player-mark "O" :mover computer-mover}]
   3 [{:player-mark "X" :mover computer-mover} {:player-mark "O" :mover human-mover}]
   4 [{:player-mark "X" :mover computer-mover} {:player-mark "O" :mover computer-mover}]})

(defn console-play
  ([]
    (user-message (instructions-str))
    (let [[player1 player2] (get-console-players)]
      (console-play (make-board) player1 player2)))
  ([board current-player next-player]
    (if (game-over? board)
      (do
        (user-message "\n" (final-message (winner board)) "\n")
        (user-message (board-str board)))
      (recur
        (get-valid-move board current-player) next-player current-player))))
