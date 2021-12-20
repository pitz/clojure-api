(ns cardlimit.schemata
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(def User {:user/id   s/Uuid
           :user/name s/Str
           :user/cpf  s/Str})

(def schema [{:db/ident       :user/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one}
             {:db/ident       :user/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :user/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}])