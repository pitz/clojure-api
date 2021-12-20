(ns cardlimit.score.scorelogic
  (:use clojure.pprint)
  (:require [cardlimit.score.records.serasa.scorelogic :as c.sr-scorelogic]
            [cardlimit.score.protocols.score           :as c.score]
            [cardlimit.db.config-db                     :as db]
            [schema.core                               :as s]))

(s/defn analyse-user [user-id user-cpf]
  (println " @ analyse-user : user-id  " user-id)
  (println " @ analyse-user : user-cpf " user-cpf)

  (let [serasa-calculator (c.sr-scorelogic/->SerasaScoreCalculator)]
    (dosync
      (c.score/calculate! serasa-calculator (db/connect-to-db) user-id user-cpf))))