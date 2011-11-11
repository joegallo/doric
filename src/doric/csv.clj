(ns doric.csv
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [align-cell]]))

(defn th [col]
  (:title col))

(defn td [col row]
  (row (:name col)))

(defn escape [s]
  (let [s (.replaceAll s "\"" "\"\"")]
    (if (re-find #"[,\n\"]" s)
      (str "\"" s "\"")
      s)))

(defn render [table]
  (str (join "," (map escape (first table))) "\n"
       (join "\n"
             (concat
              (for [tr (rest table)]
                (join "," (map escape tr)))))))
