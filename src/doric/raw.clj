(ns doric.raw
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [aligned-th aligned-td]]))

(def th aligned-th)

(def td aligned-td)

(defn render [table]
  (cons (join " " (first table))
        (for [tr (rest table)]
          (join " " tr))))
