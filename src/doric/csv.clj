(ns doric.csv
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [unaligned-th unaligned-td]]))

(def th unaligned-th)

(def td unaligned-td)

(defn escape [s]
  (let [s (.replaceAll s "\"" "\"\"")]
    (if (re-find #"[,\n\"]" s)
      (str "\"" s "\"")
      s)))

(defn render [table]
  (cons (join "," (map escape (first table)))
        (for [tr (rest table)]
          (join "," (map escape tr)))))
