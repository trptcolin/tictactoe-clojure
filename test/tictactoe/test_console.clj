(ns tictactoe.test_console
  (:use
    [clojure.test]
    [tictactoe.console]
    [tictactoe.core :only [make-board final-message]]))

(defn fake-println [& args])

(use-fixtures :each
  (fn [f] (binding [user-message fake-println
                    user-prompt fake-println] (f))))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))

(deftest test-human-mover
  (with-in-str (make-input '(1 2 3))
    (is (= 0 (human-mover (make-board) "X")))
    (is (= 1 (human-mover (make-board) "X")))
    (is (= 2 (human-mover (make-board) "X"))))
  (with-in-str (make-input '(8 6 4))
    (is (= 7 (human-mover (make-board) "X")))
    (is (= 5 (human-mover (make-board) "X")))
    (is (= 3 (human-mover (make-board) "X")))))

(def actual-winner (ref :not-over))
(defn mock-final-message [winner]
  (dosync (ref-set actual-winner winner)))

(deftest full-game
  (binding [final-message mock-final-message]
    (with-in-str (make-input '(1 1 9 2 8 3))
      (console-play)
      (is (= "X" @actual-winner)))))

(run-tests)
