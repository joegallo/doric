(ns doric.test.core
  (:refer-clojure :exclude [format name])
  (:use [doric.core] :reload)
  (:use [clojure.test]))

(deftest test-title-case
  (is (= "Foo" (title-case "foo")))
  (is (= "Foo-bar" (title-case "foo-bar")))
  (is (= "Foo Bar" (title-case "foo bar")))
  (is (= "Foo  Bar" (title-case "foo  bar"))))

(deftest test-align
  (is (= :left (align {})))
  (is (= :right (align {:align :right}))))

(deftest test-format
  (is (= identity (format {})))
  (is (= str (format {:format str}))))

(deftest test-title
  (is (= "foo" (title {:title "foo"})))
  (is (= "Foo" (title {:name "foo"}))))

(deftest test-title-align
  (is (= :center (title-align {})))
  (is (= :left (title-align {:align :left})))
  (is (= :left (title-align {:align 'left})))
  (is (= :left (title-align {:align "left"})))
  (is (= :right (title-align {:align :left :title-align :right})))
  (is (= :right (title-align {:align :left :title-align :right}))))

(deftest test-width
  (is (= 5 (width {:width 5})))
  (is (= 5 (width {:width 5 :name :foobar})))
  (is (= 7 (width {:name :foobar} ["foobar2"]))))

(deftest test-format-cell
  (is (= "2" (format-cell {:format inc} 1))))

(deftest test-align-cell
  (is (= "." (align-cell {:width 1} "." :left)))
  (is (= "." (align-cell {:width 1} "." :center)))
  (is (= "." (align-cell {:width 1} "." :right)))
  (is (= ".  " (align-cell {:width 3} "." :left)))
  (is (= " . " (align-cell {:width 3} "." :center)))
  (is (= "  ." (align-cell {:width 3} "." :right)))
  (is (= ".   " (align-cell {:width 4} "." :left)))
  (is (= "  . " (align-cell {:width 4} "." :center)))
  (is (= "   ." (align-cell {:width 4} "." :right))))
