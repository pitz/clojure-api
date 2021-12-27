(ns cardlimit.routes.routes
  (:use clojure.pprint)
  (:require [cardlimit.score.scorelogic :as c.scorelogic]
            [cardlimit.utils.utils      :as utils]
            [compojure.core             :refer :all]
            [compojure.route            :as route]
            [ring.middleware.defaults   :refer :all]
            [clojure.data.json          :as json])
  (:gen-class))

(defn render-error-message [e]
  {:status  400
   :headers {"Content-Type" "text/json"}
   :body    (-> (str (json/write-str {:error (.getMessage e)})))})

(defn save-score-handler [req]
  (try
    (let [request-body (utils/get-body req)
          user-id      (get request-body :id)
          user-cpf     (get request-body :cpf)
          score        (c.scorelogic/analyse-user! user-id user-cpf)]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body    (-> (str (json/write-str score)))})
    (catch Exception e
      (render-error-message e))))

(defroutes app-routes
   (POST "/scores" [] save-score-handler)
   (route/not-found "Page not found!"))