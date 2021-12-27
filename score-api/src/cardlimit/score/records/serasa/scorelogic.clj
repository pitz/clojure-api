(ns cardlimit.score.records.serasa.scorelogic
  (:use clojure.pprint)
  (:require [cardlimit.model :as c.model]
            [cardlimit.score.protocols.score :as c.score]
            [cardlimit.utils.utils :as utils]
            [cardlimit.integration.kafka.producer.logic :as c.producer]
            [cardlimit.integration.http.serasa.manager :as c.serasamanager]
            [datomic.api :as d]
            [schema.core :as s]
            [clojure.data.json :as json])
  (:import (java.util UUID)))

(s/defn calculate-score-index [user-id user-cpf]
  (c.serasamanager/calculate-score user-id user-cpf))

(defrecord SerasaScoreCalculator [] c.score/ScoreCalculator

  (calculate! [this conn batch user-id user-cpf]
    (let [score-batch-item {:score-batch-item/id      (UUID/randomUUID)
                            :score-batch-item/score   (calculate-score-index user-id user-cpf)
                            :score-batch-item/partner :serasa
                            :score-batch-item/batch   batch}]
      (println @(d/transact conn [score-batch-item]))
      score-batch-item)))