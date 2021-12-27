(ns cardlimit.score.scorelogic
  (:use clojure.pprint)
  (:require [cardlimit.score.records.serasa.scorelogic :as c.serasa-scorelogic]
            [cardlimit.score.records.serpro.scorelogic :as c.serpro-scorelogic]
            [cardlimit.score.protocols.score           :as c.score]
            [cardlimit.db.config-db                     :as db]
            [cardlimit.model                           :as c.model]
            [cardlimit.schemata                        :as c.schemata]
            [cardlimit.utils.utils                     :as utils]
            [schema.core                               :as s]
            [datomic.api                               :as d])
  (:import (java.util UUID)))

(s/defn save-batch! [user-id :- s/Str, user-cpf :- s/Str]
  (let [batch {:score-batch/id       (UUID/randomUUID)
               :score-batch/user-id  (UUID/fromString user-id)
               :score-batch/user-cpf user-cpf}]
    (println @(d/transact (db/connect-to-db) [batch]))

    batch))

(s/defn save-analysis! [batch, band :- s/Keyword, initial-limit :- s/Num]
  (d/transact (db/connect-to-db) [[:db/add (get batch :score-batch/id) :score-batch/band band]
                                  [:db/add (get batch :score-batch/id) :score-batch/initial-limit initial-limit]]))

(s/defn process-analysis! [batch, calculators]
  (let [user-id            (:score-batch/user-id batch)
        user-cpf           (:score-batch/user-cpf batch)
        serasa-score       (c.score/calculate! (:serasa calculators) (db/connect-to-db) batch user-id user-cpf)
        serpro-score       (c.score/calculate! (:serpro calculators) (db/connect-to-db) batch user-id user-cpf)
        serasa-score-index (:score-batch-item/score serasa-score)
        serpro-score-index (:score-batch-item/score serpro-score)
        total-score-index  (+ (* serasa-score-index 1.5) (* serpro-score-index 0.5))]

    total-score-index))

(s/defn get-band-info [score-index :- s/Num]
  (cond
    (< score-index c.model/gold-user-minimum-score)     c.model/starter-user-values
    (< score-index c.model/platinum-user-minimum-score) c.model/gold-user-values
    (< score-index c.model/upmarket-user-minimum-score) c.model/platinum-user-values
    :else                                               c.model/upmarket-user-values))

(s/defn analyse-user! [user-id :- s/Str, user-cpf :- s/Str]
  (utils/log-analysis user-cpf)

  (let [batch             (save-batch! user-id user-cpf)
        serasa-calculator (c.serasa-scorelogic/->SerasaScoreCalculator)
        serpro-calculator (c.serpro-scorelogic/->SerproScoreCalculator)
        score-index       (process-analysis! batch {:serasa serasa-calculator :serpro serpro-calculator})
        band-info         (get-band-info score-index)]

    (save-analysis! batch (:band band-info) (:initial-limit band-info))))