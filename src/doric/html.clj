(ns doric.html
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [align-cell]]))

(defn th [col]
  (str "<th>" (:title col) "</th>"))

(defn td [col row]
  (str "<td>" (row (:name col)) "</td>"))

(defn render [table]
  (str "<table>"
       (str "<tr>" (join (first table)) "</tr>")
       (join (for [tr (rest table)]
               (str "<tr>" (join tr) "</tr>")))
       "</table>"))
