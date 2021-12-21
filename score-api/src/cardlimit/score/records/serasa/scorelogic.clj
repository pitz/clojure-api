(ns cardlimit.score.records.serasa.scorelogic
  (:use clojure.pprint)
  (:require [cardlimit.model                            :as c.model]
            [cardlimit.score.protocols.score            :as c.score]
            [cardlimit.utils.utils                      :as utils]
            [cardlimit.integration.kafka.producer.logic :as c.producer]
            [datomic.api                                :as d]
            [schema.core                                :as s]
            [clojure.data.json                          :as json]))

(def gold-user-minimum-score     10)
(def platinum-user-minimum-score 50)
(def upmarket-user-minimum-score 90)

(s/defn get-score-band [score :- s/Int]
  (cond
    (< score gold-user-minimum-score)     (assoc c.model/starter-user-values  :userscore/score score :userscore/calculator :serasa)
    (< score platinum-user-minimum-score) (assoc c.model/gold-user-values     :userscore/score score :userscore/calculator :serasa)
    (< score upmarket-user-minimum-score) (assoc c.model/platinum-user-values :userscore/score score :userscore/calculator :serasa)
    :else                                 (assoc c.model/upmarket-user-values :userscore/score score :userscore/calculator :serasa)))

(s/defn calculate-score-index []
  (rand-int 100))

(defrecord SerasaScoreCalculator [] c.score/ScoreCalculator

  (calculate! [this conn user-id user-cpf]
    (let [score-index (calculate-score-index)
          score       (get-score-band score-index)
          score       (assoc score :userscore/user-id (utils/uuid-from-string user-id))
          score       (assoc score :userscore/user-cpf user-cpf)
          score       (assoc score :userscore/id (c.model/new-uuid))]

      (println @(d/transact conn [score]))
      (c.producer/send-message "calculated-score" (str (json/write-str score)))

      score)))