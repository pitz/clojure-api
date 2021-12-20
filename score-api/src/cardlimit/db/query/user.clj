(ns cardlimit.db.query.user
  (:use clojure.pprint)
  (:require [cardlimit.utils.utils :as utils]
            [datomic.api           :as d]))

(defn query [conn]
  (let [database (d/db conn)]
    (d/q '[:find   ?id ?name ?cpf
           :keys  user/id user/name user/cpf
           :where [?user :user/id   ?id]
                  [?user :user/name ?name]
                  [?user :user/cpf  ?cpf]] database)))

(defn get-user [conn id]
  (let [users (let [database (d/db conn)]
               (d/q '[:find   ?id ?name ?cpf
                      :keys  user/id user/name user/cpf
                      :in    $ ?id
                      :where [?user :user/id   ?id]
                             [?user :user/name ?name]
                             [?user :user/cpf  ?cpf]] database (utils/uuid-from-string id)))]
    (first users)))

(defn is-cpf-already-in-use? [conn cpf]
  (let [count-id (let [database (d/db conn)]
                (d/q '[:find   (count ?id)
                       :in    $ ?cpf
                       :where [?user :user/id   ?id]
                              [?user :user/name ?name]
                              [?user :user/cpf  ?cpf]] database cpf))]
    (or (nil? (first count-id)) (> (first count-id) 0))))