(defproject cardlimit "0.1.0-SNAPSHOT"
  :description   "Uma API para calcular limite de cartões usando CLOJURE!!!!!"

  :license       {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
                  :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies  [[org.clojure/clojure     "1.10.1"]
                  [prismatic/schema        "1.2.0"]
                  [com.datomic/datomic-pro "1.0.6269"]
                  [compojure               "1.6.1"]
                  [http-kit                "2.3.0"]
                  [ring/ring-defaults      "0.3.2"]
                  [ring/ring-anti-forgery  "1.3.0"]
                  [org.clojure/data.json   "2.4.0"]]

  :main ^:skip-aot cardlimit.core
  :profiles {:uberjar {:aot :all}}
  :repl-options  {:init-ns cardlimit.core})
