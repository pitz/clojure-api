(ns cardlimit.schemata
  (:use clojure.pprint)
  (:require [schema.core           :as s]
            [cardlimit.utils.utils :as utils]))

(def ScoreBatch {:score-batch/id            s/Uuid
                 :score-batch/user-id       s/Uuid
                 :score-batch/user-cpf      s/Str
                 :score-batch/band          utils/Band
                 :score-batch/initial-limit utils/NonNegativeNumber})

(def PartnerScore {:score-batch-item/id      s/Uuid
                   :score-batch-item/score   utils/PosInt
                   :score-batch-item/partner utils/Partner
                   :score-batch-item/batch   s/->MapEntry})

(def schema [{:db/ident :score-batch/id,            :db/valueType :db.type/uuid,    :db/cardinality :db.cardinality/one, :db/unique :db.unique/identity}
             {:db/ident :score-batch/user-id,       :db/valueType :db.type/uuid,    :db/cardinality :db.cardinality/one}
             {:db/ident :score-batch/user-cpf,      :db/valueType :db.type/string,  :db/cardinality :db.cardinality/one}
             {:db/ident :score-batch/band,          :db/valueType :db.type/keyword, :db/cardinality :db.cardinality/one}
             {:db/ident :score-batch/initial-limit, :db/valueType :db.type/bigdec,  :db/cardinality :db.cardinality/one}

             {:db/ident :score-batch-item/id,      :db/valueType :db.type/uuid,    :db/cardinality :db.cardinality/one, :db/unique :db.unique/identity}
             {:db/ident :score-batch-item/score,   :db/valueType :db.type/long,    :db/cardinality :db.cardinality/one}
             {:db/ident :score-batch-item/partner, :db/valueType :db.type/keyword, :db/cardinality :db.cardinality/one}
             {:db/ident :score-batch-item/batch,   :db/valueType :db.type/ref,     :db/cardinality :db.cardinality/one}])