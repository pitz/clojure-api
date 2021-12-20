(ns cardlimit.score.records.serasa.scorelogic
  (:use clojure.pprint)
  (:require [schema.core               :as s]
            [cardlimit.model           :as c.model]
            [cardlimit.utils.parsers   :as c.parser]
            [cardlimit.score.protocols.score :as c.score]
            [cardlimit.db.query.user   :as db.user]
            [datomic.api               :as d]
            [cardlimit.schemata        :as c.schemata]))

(def gold-user-minimum-score     10)
(def platinum-user-minimum-score 50)
(def upmarket-user-minimum-score 90)

(s/defn get-score-band [score :- s/Int]
  (cond
    (< score gold-user-minimum-score)     (assoc c.model/starter-user-values  :userscore/score score :userscore/calculator :serasa)
    (< score platinum-user-minimum-score) (assoc c.model/gold-user-values     :userscore/score score :userscore/calculator :serasa)
    (< score upmarket-user-minimum-score) (assoc c.model/platinum-user-values :userscore/score score :userscore/calculator :serasa)
    :else                                 (assoc c.model/upmarket-user-values :userscore/score score :userscore/calculator :serasa)))

(s/defn calculate-score :- c.schemata/UserScore
  [user :- c.schemata/User]
  (let [index-score (rand-int 100)
        score       (get-score-band index-score)
        score       (assoc score :userscore/user-id (get user :user/id))
        score       (assoc score :userscore/id (c.model/new-uuid))]
    score))

(defrecord SerasaScoreCalculator [score-list conn] c.score/ScoreCalculator

  (calculate! [this user]
    (let [score (calculate-score user)]
      (swap! score-list conj score)
      (get @score-list user)))

  (save-analysis! [this]
    (doseq [score @(get this :score-list)]
      (println @(d/transact conn [score]))))

  (print-score [this]
    (println "---------------")
    (println "- S E R A S A -")
    (println "---------------")
    (doseq [score @(get this :score-list)]
      (let [user        (db.user/get-user conn (get score :userscore/user-id))
            user-name   (get user  :user/name)
            user-cpf    (get user  :user/cpf)
            score-index (get score :userscore/score)
            card-band   (c.parser/parse-band-name (get score :band))
            card-limit  (get score :userscore/initial-limit)
            user-message  (str "[->] Cliente " user-name " (" user-cpf ").")
            score-message (str "     Score: " score-index ".")
            card-message  (str "     Cartão " card-band " com limite R$ " card-limit)]
        (println user-message)
        (println score-message)
        (println card-message)))
    (println "------------------------------------")))