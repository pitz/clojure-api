(ns cardlimit.routes.routes
  (:use clojure.pprint)
  (:require [cardlimit.user.userlogic :as c.userlogic]
            [cardlimit.utils.utils    :as utils]
            [compojure.core           :refer :all]
            [compojure.route          :as route]
            [ring.util.anti-forgery   :refer [anti-forgery-field]]
            [ring.middleware.defaults :refer :all]
            [clojure.data.json        :as json])
  (:gen-class))

(defn render-error-message [e]
  {:status  400
   :headers {"Content-Type" "text/json"}
   :body    (-> (str (json/write-str {:error (.getMessage e)})))})

(defn save-user-handler [req]
  (try
    (let [user-info  (utils/get-body req)
          name       (get user-info :name)
          cpf        (get user-info :cpf)
          saved-user (c.userlogic/save-user name cpf)]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body    (-> (str (json/write-str saved-user)))})
    (catch Exception e
      (render-error-message e))))

(defn list-users-handler [req]
  (let [users  (c.userlogic/list-users)]
    {:status  200
     :headers {"Content-Type" "text/json"}
     :body    (-> (str (json/write-str users)))}))

(defn get-user-handler [req]
  (try
    (let [user-id (utils/get-parameter req :id)
          user    (c.userlogic/get-user user-id)]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body    (-> (str (json/write-str user)))})
    (catch Exception e
      (render-error-message e))))

(defroutes app-routes
           (GET  "/users"      [] list-users-handler)
           (POST "/users/add"  [] save-user-handler)
           (GET  "/users/:id"  [id] get-user-handler)
           (route/not-found "Page not found!"))