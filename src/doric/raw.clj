(ns doric.raw
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [align-cell]]))

(defn th [col]
  (align-cell col (:title col) (:title-align col)))

(defn td [col row]
  (align-cell col (row (:name col)) (:align col)))

(defn render [table]
  (str (join " " (first table)) "\n"
       (join "\n"
             (for [tr (rest table)]
               (join " " tr)))))
