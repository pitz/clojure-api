(ns cardlimit.core
  (:use clojure.pprint)
  (:require [cardlimit.db.config-db    :as db]
            [cardlimit.routes.routes  :as c.routes]
            [org.httpkit.server       :as server]
            [compojure.core           :refer :all]
            [ring.middleware.defaults :refer :all])
  (:gen-class))

;(db/apagar-bd)
(def conn (db/connect-to-db))
(db/cria-schema conn)

(defn -main [& args]
  (let [con-port     (Integer/parseInt (or (System/getenv "PORT") "3000"))
        con-defaults {:keywords? true :port con-port}
        con-message  (str "API running at http:/127.0.0.1:" con-port "/")]
    (server/run-server (wrap-defaults #'c.routes/app-routes api-defaults) con-defaults)
    (println con-message)))