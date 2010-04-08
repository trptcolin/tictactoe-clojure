(ns tictactoe.test_core
  (:use
    [clojure.test]
    [tictactoe.core]))

(deftest test-make-board
  (is (= [" " " " " "
          " " " " " "
          " " " " " "]
    (make-board))))

(deftest test-empty-square
  (is (empty-square? " ")))

(deftest test-nonempty-square
  (is (not (empty-square? "X"))))

(deftest test-not-won
  (is (not (winner (make-board)))))

(deftest test-not-full
  (is (not (full? (make-board)))))

(deftest test-full
  (is (full? ["X" "O" "X"
              "O" "X" "O"
              "O" "X" "O"])))

(deftest test-no-winner
  (is (nil? (winner (make-board))))
  (is (nil? (winner ["X" "O" "X"
                     "O" "X" "O"
                     "O" "X" "O"]))))

(deftest test-won
  (are [board] (= "X" (winner board))
    ["X" "X" "X"
     "O" "O" " "
     " " " " " "]
    ["O" "O" " "
     "X" "X" "X"
     " " " " " "]
    ["O" "O" " "
     " " " " " "
     "X" "X" "X"]
    ["X" "O" " "
     "X" "O" " "
     "X" " " " "]
    ["O" "X" " "
     "O" "X" " "
     " " "X" " "]
    ["O" " " "X"
     "O" " " "X"
     " " " " "X"]
    ["X" " " " "
     "O" "X" "O"
     " " " " "X"]
    [" " " " "X"
     "O" "X" "O"
     "X" " " " "]))

(deftest test-game-over?
  (is (game-over? ["X" "X" "X"
                   "X" "X" "X"
                   "X" "X" "X"]))
  (is (not (game-over? (make-board)))))

(deftest test-place-on-board
  (is (= ["X" " " " "
          " " " " " "
          " " " " " "]
    (place-on-board (make-board) 0 "X")))
  (is (= [" " " " "O"
          "O" " " " "
          " " " " " "]
    (place-on-board (place-on-board (make-board) 2 "O")
      3
      "O"))))

(deftest test-next-mark
  (is (= "O" (next-mark "X")))
  (is (= "X" (next-mark "O"))))

(deftest test-valid-move
  (is (valid-move? (make-board) 0))
  (is (not (valid-move? (make-board) 9)))
  (is (not (valid-move? (make-board) "9")))
  (is (not (valid-move? (place-on-board (make-board) 0 "X")
    0))))

(deftest test-final-message
  (is (= "Cat's Game!" (final-message nil)))
  (is (= "X Wins!!!" (final-message "X")))
  (is (= "O Wins!!!" (final-message "O"))))

(deftest test-empty-squares
  (is (= (range 0 9) (empty-squares (make-board))))
  (is (= '(1 3 5 7) (empty-squares ["X" " " "X"
                                    " " "X" " "
                                    "X" " " "X"])))
  (is (empty? (empty-squares ["X" "X" "X"
                              "X" "X" "X"
                              "X" "X" "X"]))))
