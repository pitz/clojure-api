(ns cardlimit.routes
  (:use clojure.pprint)
  (:require [cardlimit.user.userlogic :as c.userlogic]
            [cardlimit.utils.utils    :as utils]
            [compojure.core           :refer :all]
            [compojure.route          :as route]
            [ring.util.anti-forgery   :refer [anti-forgery-field]]
            [ring.middleware.defaults :refer :all]
            [clojure.data.json        :as json])
  (:gen-class))

(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})



(defn save-user-handler [req]
  (let [user-info  (utils/get-body req)
        name       (get user-info :first-name)
        last-name  (get user-info :last-name)
        saved-user (c.userlogic/save-user name last-name)]

    (println " @ save-user-handler: " req)

    {:status  200
     :headers {"Content-Type" "text/json"}
     :body    (-> (str (json/write-str saved-user)))}))

(defn list-users-handler [req]
  (let [users  (c.userlogic/list-users)]
    (println " @ list-users-handler: " users)

    {:status  200
     :headers {"Content-Type" "text/json"}
     :body    (-> (str (json/write-str users)))}))

(defn anti-forgery-token [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (-> (str (json/write-str (anti-forgery-field))))})

(defroutes app-routes
  (GET "/token"       [] anti-forgery-token)
  (GET  "/"           [] simple-body-page)
  (GET  "/users"      [] list-users-handler)
  (POST "/users/add"  [] save-user-handler)
  (route/not-found "Page not found!"))