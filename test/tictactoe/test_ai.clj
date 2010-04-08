(ns tictactoe.test_ai
  (:use
    [clojure.test]
    [tictactoe.ai]
    [tictactoe.core :only [make-board]]))

(deftest test-computer-takes-immediate-win
  (are [move board] (= move (computer-mover board "X"))
    0 [" " "O" " "
       " " "X" " "
       " " "O" "X"]
    2 ["X" "X" " "
       " " "O" " "
       " " "O" " "]
    8 ["O" "X" " "
       " " "O" " "
       "X" "X" " "]))

(deftest test-computer-blocks-opponent-win
  (are [move board] (= move (computer-mover board "X"))
    1 [" " " " " "
       "X" "O" "X"
       " " "O" " "]
    1 ["O" " " " "
       " " "O" " "
       "X" "O" "X"]))

(deftest test-computer-prevents-fork
  (are [move board] (= move (computer-mover board "O"))
    4 [" " " " "X"
       " " " " " "
       " " " " " "]
    4 ["X" " " " "
       " " " " " "
       " " " " " "]
    4 [" " " " " "
       " " " " " "
       " " " " "X"]
    4 [" " " " " "
       " " " " " "
       "X" " " " "]))

(deftest test-starting-move
  (is (= 0 (computer-mover (make-board) "X"))))
