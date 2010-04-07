(ns tictactoe.console
  (:use clojure.test tictactoe.console))

(defn fake-println [& args])
(defn fake-read-int [] 1)

(deftest test-human-mover
  (binding [user-message fake-println
            user-prompt fake-println]
    (binding [read-int (fn [] 1)]
      (is (= 0 (human-mover (make-board) "X"))))
    (binding [read-int (fn [] 5)]
      (is (= 4 (human-mover (make-board) "X"))))))

(run-tests)