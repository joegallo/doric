(ns doric.core
  (:refer-clojure :exclude [format name join split when])
  (:use [clojure.string :only [join split]]))

(defn- title-case-word [w]
  (if (zero? (count w))
    w
    (str (Character/toTitleCase (first w))
         (subs w 1))))

(defn title-case [s]
  (join " " (map title-case-word (split s #"\s"))))

(defn align [col & [data]]
  (or (keyword (:align col))
      :left))

(defn format [col & [data]]
  (or (:format col)
      identity))

(defn title [col & [data]]
  (or (:title col)
      (title-case
       (.replaceAll (clojure.core/name (:name col))
                    "-" " "))))

(defn title-align [col & [data]]
  (keyword (or (:title-align col)
               (:align col)
               :center)))

(defn when [col & [data]]
  (:when col true))

(defn width [col & [data]]
  (or (:width col)
      (apply max (map count (cons (:title col)
                                  (map str data))))))

(defn format-cell [col s]
  (str ((:format col) s)))

(defn align-cell [col s align]
  (let [width (:width col)
        s (subs s 0 (min (count s) width))
        len (count s)
        pad #(apply str (take % (repeat " ")))
        padding (- width len)
        half-padding (/ (- width len) 2)]
    (case align
      :left (str s (pad padding))
      :right (str (pad padding) s)
      :center (str (pad (Math/ceil half-padding))
                   s
                   (pad (Math/floor half-padding))))))

(def ^{:dynamic true} th)
(def ^{:dynamic true} td)
(def ^{:dynamic true} render)

(defn header [cols]
  (for [col cols :when (:when col)]
    (th col)))

(defn body [cols rows]
  (for [row rows]
    (for [col cols :when (:when col)]
      (td col row))))

(defn- col-data [col rows]
  (map #(get % (:name col)) rows))

(defn- column1 [col & [data]]
  {:align (align col data)
   :format (format col data)
   :title (title col data)
   :title-align (title-align col data)
   :when (when col data)})

(defn- column-map [col]
  (if (map? col)
    col
    {:name col}))

(defn- columns1 [cols rows]
  (for [col cols :let [col (column-map col)]]
    (merge col
           (column1 col (col-data col rows)))))

(defn- format-rows [cols rows]
  (for [row rows]
    (into {}
          (for [col cols :let [name (:name col)]]
            [name (format-cell col (row name))]))))

(defn- column2 [col & [data]]
  {:width (width col data)})

(defn- columns2 [cols rows]
  (for [col cols]
    (merge col
           (column2 col (col-data col rows)))))

(def csv 'doric.csv)
(def html 'doric.html)
(def org 'doric.org)
(def raw 'doric.raw)

(defn table
  ([rows]
     (table (vary-meta (keys (first rows))
                       merge (meta rows)) rows))
  ([cols rows]
     (let [meta (meta cols)
           cols (columns1 cols rows)
           rows (format-rows cols rows)
           cols (columns2 cols rows)
           format (or (:format meta) org)]
       (require format)
       (binding [th (ns-resolve format 'th)
                 td (ns-resolve format 'td)
                 render  (ns-resolve format 'render)]
         (render (cons (header cols) (body cols rows)))))))
