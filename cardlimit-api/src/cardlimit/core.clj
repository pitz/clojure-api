(ns cardlimit.core
  (:use clojure.pprint)
  (:require [cardlimit.db.config-db    :as db]
            [cardlimit.routes         :as c.routes]
            [org.httpkit.server       :as server]
            [compojure.core           :refer :all]
            [compojure.route          :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint           :as pp]
            [clojure.string           :as str]
            [clojure.data.json        :as json])
  (:gen-class))

(def conn (db/connect-to-db))
(db/cria-schema conn)

; my people-collection mutable collection vector
(def people-collection (atom []))

;Collection Helper functions to add a new person
(defn addperson [firstname surname]
  (swap! people-collection conj {:firstname (str/capitalize firstname) :surname (str/capitalize surname)}))

; Example JSON objects
(addperson "Functional" "Human")
(addperson "Micky" "Mouse")

; Get the parameter specified by pname from :params object in req
(defn getparameter [req pname] (get (:params req) pname))

; Simple Body Page
(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

; request-example
(defn request-example [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->>
              (pp/pprint req)
              (str "Request Object: " req))})

(defn hello-name [req] ;(3)
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->
              (pp/pprint req)
              (str "Hello " (:name (:params req))))})

; Return List of People
(defn people-handler [req]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (str (json/write-str @people-collection))})

; Add a new person into the people-collection
(defn addperson-handler [req]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (-> (let [p (partial getparameter req)]
                  (str (json/write-str (addperson (p :firstname) (p :surname))))))})

;(defroutes app-routes
;  (GET "/"           [] simple-body-page)
;  (GET "/request"    [] request-example)
;  (GET "/hello"      [] hello-name)
;  (GET "/people"     [] people-handler)
;  (GET "/people/add" [] addperson-handler)
;  (route/not-found "Error, page not found!"))

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server
      (wrap-defaults #'c.routes/app-routes api-defaults) {:keywords? true :port port})
    (println (str "Running at http:/127.0.0.1:" port "/"))))