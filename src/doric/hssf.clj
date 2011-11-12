(ns doric.hssf
  (:import (org.apache.poi.hssf.usermodel HSSFSheet HSSFWorkbook)
           (org.apache.poi.ss.usermodel CellStyle Font)))

(defn th [col]
  [(:title col) col])

(defn td [col row]
  [(row (:name col)) col])

(defn cell-style [wb align & [font]]
  (let [cs (.createCellStyle wb)]
    (.setAlignment cs (condp = align
                        :left CellStyle/ALIGN_LEFT
                        :center CellStyle/ALIGN_CENTER
                        :right CellStyle/ALIGN_RIGHT))
    (when font
      (.setFont cs font))
    cs))

(defn render [table]
  (let [wb (HSSFWorkbook.)
        ^HSSFSheet sh (.createSheet wb "Sheet1")
        ^Font bold (.createFont wb)
        _ (.setBoldweight bold Font/BOLDWEIGHT_BOLD)]
    (let [row (.createRow sh 0)]
      (doseq [[j [s m]] (map-indexed vector (first table))]
        (let [c (.createCell row j)]
          (.setCellValue c s)
          (.setCellStyle c (cell-style wb (:title-align m) bold)))))
    (doseq [[i r] (map-indexed vector (rest table))]
      (let [row (.createRow sh (inc i))]
        (doseq [[j [s m]] (map-indexed vector r)]
          (let [c (.createCell row j)]
            (.setCellValue c (if (number? s)
                               (double s)
                               s))
            (.setCellStyle c (cell-style wb (:align m)))))))
    wb))

;; this is an undocumented alpha format that i wrote primarily just to
;; see how well doric could handle it -- if you use it, please be
;; aware that this is not supported in any way.  that said, you could
;; use it like this, more or less:

#_
(with-open [f (java.io.FileOutputStream. "out.xls")]
  (.write (table* ^{:format 'doric.hssf} [{:a 1 :b 2}{:a 3 :b 4}])))

;; fun, huh?
