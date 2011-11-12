(ns doric.html
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [unaligned-th unaligned-td]]))

(def th unaligned-th)

(def td unaligned-td)

(defn render [table]
  (concat ["<table>"
           (str "<tr>" (join (for [c (first table)]
                               (str "<th>" c "</th>"))) "</tr>")]
          (for [tr (rest table)]
            (str "<tr>" (join (for [c tr]
                                (str "<td>" c "</td>"))) "</tr>"))
          ["</table>"]))
