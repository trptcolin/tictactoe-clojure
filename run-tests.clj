(ns tictactoe.run-tests
  (:use
    [clojure.test :only (run-tests)]
    [clojure.contrib.str-utils :only [re-gsub]])
  (:import (java.io File)))

(defn remove-dir-and-extension [filename]
  (re-gsub #"test/" ""
    (re-gsub #".clj$" "" filename)))

(defn separators-to-dots [s]
  (re-gsub (re-pattern File/separator) "." s))

(def test-directory "test")

(defn file-to-ns [f]
  (symbol
    (separators-to-dots (remove-dir-and-extension (.getPath f)))))

(def test-files
  (filter #(.isFile %) (file-seq (File. test-directory))))

(def test-namespaces
  (map #(file-to-ns %) test-files))

(println "Loading tests...")
(apply require :reload-all test-namespaces)
(time (apply run-tests test-namespaces))

