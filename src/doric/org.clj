(ns doric.org
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [aligned-th aligned-td]]))

(def th aligned-th)

(def td aligned-td)

(defn render [table]
  (let [spacer (str "|-"
                    (join "-+-"
                          (map #(apply str (repeat (.length %) "-"))
                               (first table)))
                    "-|")]
    (concat [spacer
             (str "| " (join " | " (first table)) " |")
             spacer]
            (for [tr (rest table)]
              (str "| " (join " | " tr) " |"))
            [spacer])))
