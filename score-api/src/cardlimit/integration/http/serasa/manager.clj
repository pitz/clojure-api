(ns cardlimit.integration.http.serasa.manager
  (:use clojure.pprint)
  (:require [clj-http.client   :as client]
            [clojure.data.json :as json]
            [schema.core       :as s]))

(def score-api-base-url "http://127.0.0.1:3006")
(def score-api-path     "/scores")

(s/defn calculate-score :- s/Num
  [user-id :- s/Uuid, user-cpf :- s/Str]
  (let [request-body-info {:id user-id :cpf user-cpf}
        response          (client/post (str score-api-base-url score-api-path) {:form-params request-body-info :content-type :json})
        response-body     (get response :body)
        score             (get (json/read-str response-body) "score")]
    score))