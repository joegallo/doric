(ns doric.swing
  (:import (javax.swing JFrame JScrollPane JTable SwingUtilities)
           (javax.swing.table AbstractTableModel)))

(defn th [col]
  [(:title col) col])

(defn td [col row]
  [(row (:name col)) col])

(defn render [table]
  (let [frame (JFrame.)
        do-it #(let [names (vec (first table))
                     rows (vec (map vec (rest table)))
                     model (proxy [AbstractTableModel] []
                             (getRowCount []
                               (count rows))
                             (getColumnCount []
                               (count names))
                             (getColumnName [column]
                               (first (names column)))
                             (getColumnClass [column]
                               (.getClass (first ((rows 0) column))))
                             (getValueAt [row column]
                               (first ((rows row) column))))
                     table (JTable. model)
                     scroll (JScrollPane. table)]
                 ;; add the table to the frame
                 (.setFillsViewportHeight table true)
                 (.setAutoCreateRowSorter table true)
                 (.add frame scroll)
                 ;; show it
                 (.pack frame)
                 (.setVisible frame true))]
    (SwingUtilities/invokeLater do-it)
    frame))

;; this is an undocumented alpha format that i wrote primarily just to
;; see how well doric could handle it -- if you use it, please be
;; aware that this is not supported in any way.  that said, you could
;; use it like this, more or less:

#_
(table* ^{:format 'doric.swing} [{:a 1 :b 2}{:a 3 :b 4}])

;; fun, huh?
