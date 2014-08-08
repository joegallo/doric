# Doric

Doric is a library for rendering pretty emacs-style tables from
Clojure data.  Data is passed into Doric as nested collections, and
comes out as a string suitable for printing.

Add this to your project.clj :dependencies list:

```clojure
[doric "0.8.0"]
```

## Usage

```clojure
;; like this for org-mode tables only
(use '[doric.core :only [table]])

;; or this, if you want the alternative formats
(use '[doric.core :only [table csv html org raw]])
```

Rows are maps, columns are entries in the maps.  Column titles are
driven from the keys, by default, :like-this becomes Like This.

```clojure
> (print (table [{:a 1 :b 2}]))
|---+---|
| A | B |
|---+---|
| 1 | 2 |
|---+---|
```

The default formatting is emacs org-mode tables, which are awesome.

```clojure
> (print (table [{:a 1 :b 2 :c 3}{:a 4 :b 5 :c 6}]))
|---+---+---|
| A | B | C |
|---+---+---|
| 1 | 2 | 3 |
| 4 | 5 | 6 |
|---+---+---|
```

But you can also have raw, csv, and html tables pretty easily:

```clojure
> (print (table ^{:format raw} [{:a 1 :b 2 :c 3}{:a 4 :b 5 :c 6}]))
A B C
1 2 3
4 5 6

> (print (table ^{:format csv} [{:a 1 :b 2 :c 3}{:a 4 :b 5 :c 6}]))
A,B,C
1,2,3
4,5,6

> (print (table ^{:format html} [{:a 1 :b 2 :c 3}{:a 4 :b 5 :c 6}]))
;; omg lots of <tr>s and <td>s here
```

You can also use a custom table format by specifying a namespace that
contains the functions th, td, and render.

```clojure
> (print (table ^{:format 'my.sweet.ns} [{:a 1 :b 2 :c 3}{:a 4 :b 5 :c 6}]))
;; the sky's the limit, brah
```

Individual columns are optional, each column automatically sizes
itself to hold the data.

```clojure
> (print (table [{:lang "Clojure" :strength "strong" :safety "safe"}
                 {:lang "Java" :strength "strong" :safety "safe"}
                 {:lang "JavaScript" :strength "weak"}]))
|------------+----------+--------|
|    Lang    | Strength | Safety |
|------------+----------+--------|
| Clojure    | strong   | safe   |
| Java       | strong   | safe   |
| JavaScript | weak     |        |
|------------+----------+--------|
```

An optional first vector lets you reorder your columns.

```clojure
> (print (table [:lang :safety :strength]
                [{:lang "Clojure" :strength "strong" :safety "safe"}
                 {:lang "Java" :strength "strong" :safety "safe"}
                 {:lang "JavaScript" :strength "weak"}]))
|------------+--------+----------|
|    Lang    | Safety | Strength |
|------------+--------+----------|
| Clojure    | safe   | strong   |
| Java       | safe   | strong   |
| JavaScript |        | weak     |
|------------+--------+----------|
```

Or, you can substitute (per column) a map for a keyword, and change
the way the data is displayed.

```clojure
> (print (table [{:name :lang :title "Language" :align :center :width 12}
                 {:name :safety :width 12 :align :left}
                 {:name :strength :width 12 :align :left}]
                 [{:lang "Clojure" :strength "strong" :safety "safe"}
                  {:lang "Java" :strength "strong" :safety "safe"}
                  {:lang "JavaScript" :strength "weak"}]))
|--------------+--------------+--------------|
|   Language   | Safety       | Strength     |
|--------------+--------------+--------------|
|    Clojure   | safe         | strong       |
|     Java     | safe         | strong       |
|  JavaScript  |              | weak         |
|--------------+--------------+--------------|
```

Which probably seems like a lot of syntax, but consider that in actual
use it would probably look more like this, which isn't nearly as bad:

```clojure
> (print (table [{:name :lang :title "Language" :align :center :width 12}
                 {:name :safety :width 12 :align :left}
                 {:name :strength :width 12 :align :left}]
                 (select-languages-from-db)))
|--------------+--------------+--------------|
|   Language   | Safety       | Strength     |
|--------------+--------------+--------------|
|    Clojure   | safe         | strong       |
|     Java     | safe         | strong       |
|  JavaScript  |              | weak         |
|--------------+--------------+--------------|
```

Each column can also take a format function to alter the way the cells
are displayed.  For example, there's an included bar function for
creating text bar charts:

```clojure
> (print (table ^{:format raw} [:a :b {:name :c :format bar}]
                               [{:a 1 :b 2 :c 3}{:a 4 :b 5 :c 6}]))
A B    C  
1 2 ###   
4 5 ######
```

Column level options include:

* :align - :left, :right, :center, defaults to :left
* :title - a string, defaults to your column name, title-cased
* :title-align - like align, and defaults to the same as :align
* :format - a function to call on the values in the column, pre-output
* :when - a boolean, allows you to turn columns on and off
* :width - how wide to make the column, defaults to wide enough
* :ellipsis - a boolean, whether or not to ... truncated values, defaults to false

## License

Copyright (C) 2014 Joe Gallo and Dan Larkin

Distributed under the Eclipse Public License, the same as Clojure.
