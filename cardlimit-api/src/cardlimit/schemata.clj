(ns cardlimit.schemata
  (:use clojure.pprint)
  (:require [schema.core           :as s]
            [cardlimit.utils.utils :as utils]))

(def User {:user/id   s/Uuid
           :user/name s/Str
           :user/cpf  s/Str})

(def UserScore {:userscore/id            s/Uuid
                :userscore/user-id       utils/PosInt
                :userscore/band          utils/Band
                :userscore/score         utils/PosInt
                :userscore/initial-limit utils/NonNegativeNumber
                :userscore/calculator    s/Keyword})

(def schema [{:db/ident       :user/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one}
             {:db/ident       :user/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :user/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}

             {:db/ident       :userscore/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one}
             {:db/ident       :userscore/user
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}
             {:db/ident       :userscore/band
              :db/valueType   :db.type/keyword
              :db/cardinality :db.cardinality/one}
             {:db/ident       :userscore/score
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one}
             {:db/ident       :userscore/initial-limit
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one}
             {:db/ident       :userscore/calculator
              :db/valueType   :db.type/keyword
              :db/cardinality :db.cardinality/one}])