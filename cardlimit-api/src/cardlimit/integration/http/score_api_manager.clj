(ns cardlimit.integration.score-api-manager
  (:use clojure.pprint)
  (:require [clj-http.client :as client]))

(def score-api-base-url "http://127.0.0.1:3001")
(def score-api-path     "/scores")

(defn calculate-score [user]
  (let [request-body-info {:id (get user :user/id) :cpf (get user :user/cpf)}
        response          (client/post (str score-api-base-url score-api-path) {:form-params request-body-info :content-type :json})]
    (println response)))

