(ns tictactoe.console
  (:use clojure.test tictactoe.console))

(defn fake-println [& args])

(declare ints-to-be-read)
(defn fake-read-int []
  (dosync
    (let [i (first @ints-to-be-read)]
      (alter ints-to-be-read rest)
      i)))

(deftest test-human-mover
  (binding [user-message fake-println
            user-prompt fake-println
            read-int fake-read-int]
    (binding [ints-to-be-read (ref (iterate inc 1))]
      (is (= 0 (human-mover (make-board) "X")))
      (is (= 1 (human-mover (make-board) "X")))
      (is (= 2 (human-mover (make-board) "X"))))
    (binding [ints-to-be-read (ref (filter even? (reverse (range 9))))]
      (is (= 7 (human-mover (make-board) "X")))
      (is (= 5 (human-mover (make-board) "X")))
      (is (= 3 (human-mover (make-board) "X"))))))


(def actual-winner (ref :not-over))
(defn mock-final-message [winner]
  (dosync
    (ref-set actual-winner winner)))

(deftest full-game
  (binding [final-message mock-final-message
            user-prompt fake-println
            user-message fake-println
            read-int fake-read-int
            ints-to-be-read (ref '(1 ; game type
                                   1 9 2 8 3 ; moves
                                  ))]
    (console-play)
    (is (= "X" (dosync @actual-winner)))))

(run-tests)