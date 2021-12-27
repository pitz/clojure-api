(ns cardlimit.score.records.serpro.scorelogic
  (:use clojure.pprint)
  (:require [cardlimit.model                            :as c.model]
            [cardlimit.score.protocols.score            :as c.score]
            [cardlimit.utils.utils                      :as utils]
            [cardlimit.integration.kafka.producer.logic :as c.producer]
            [cardlimit.integration.http.serpro.manager  :as c.serprohttpmanager]
            [datomic.api                                :as d]
            [schema.core                                :as s]
            [clojure.data.json                          :as json])
  (:import (java.util UUID)))

(s/defn calculate-score-index [user-id user-cpf]
  (let [score-index (c.serprohttpmanager/calculate-score user-id user-cpf)]
    (if (> score-index 0)
      (int (/ score-index 100))
      1)))

(defrecord SerproScoreCalculator [] c.score/ScoreCalculator

  (calculate! [this conn batch user-id user-cpf]
    (let [score-batch-item {:score-batch-item/id      (UUID/randomUUID)
                            :score-batch-item/score   (calculate-score-index user-id user-cpf)
                            :score-batch-item/partner :serpro
                            :score-batch-item/batch   batch}]
      (println @(d/transact conn [score-batch-item]))
      score-batch-item)))