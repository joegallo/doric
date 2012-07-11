(ns doric.unicode
  (:refer-clojure :exclude [join])
  (:use [clojure.string :only [join]]
        [doric.core :only [aligned-th aligned-td]]))

(def th aligned-th)

(def td aligned-td)

(defn render [table]
  (let [dashes (map #(apply str (repeat (.length %) "─"))
                    (first table))
        top-spacer (str "┌─"
                        (join "─┬─" dashes)
                        "─┐")
        middle-spacer (str "├─"
                           (join "─┼─" dashes)
                           "─┤")
        bottom-spacer (str "└─"
                           (join "─┴─" dashes)
                           "─┘")]
    (concat [top-spacer
             (str "| " (join " | " (first table)) " |")
             middle-spacer]
            (for [tr (rest table)]
              (str "| " (join " ╎ " tr) " |"))
            [bottom-spacer])))