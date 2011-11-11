(ns doric.org
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [align-cell]]))

(defn th [col]
  (align-cell col (:title col) (:title-align col)))

(defn td [col row]
  (align-cell col (row (:name col)) (:align col)))

(defn render [table]
  (let [spacer (str "|-"
                    (join "-+-"
                          (map #(apply str (repeat (.length %) "-"))
                               (first table)))
                    "-|\n")]
    (apply str
           spacer
           (str "| " (join " | " (first table)) " |\n")
           spacer
           (concat
            (for [tr (rest table)]
              (str "| " (join " | " tr) " |\n"))
            [spacer]))))
