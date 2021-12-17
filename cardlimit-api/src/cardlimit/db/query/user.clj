(ns cardlimit.db.query.user
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

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
                             [?user :user/cpf  ?cpf]] database id))]
    (first users)))