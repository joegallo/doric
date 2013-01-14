(defproject doric "0.8.0"
  :description "Clojure table layout"
  :url "https://github.com/joegallo/doric"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:1.2 {:dependencies [[org.clojure/clojure "1.2.1"]]}
             :1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :dev {:dependencies [[org.apache.poi/poi "3.7"]]}})
