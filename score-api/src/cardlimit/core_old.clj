(ns cardlimit.core-old
  (:use clojure.pprint)
  (:require [schema.core                                 :as s]
            [cardlimit.user.userlogic                    :as c.userlogic]
            [cardlimit.score.records.serasa.scorelogic   :as c.sr-scorelogic]
            [cardlimit.score.protocols.score             :as c.score]
            [cardlimit.db.config-db                       :as db]
            [cardlimit.db.query.user                     :as db.user]
            [cardlimit.db.query.userscore                :as db.userscore]
            [datomic.api                                 :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)
;(db/apagar-bd)

;(defn add-user  [users user]      (conj users user))
;(defn add-user! [users user-info] (alter users add-user (c.userlogic/create-user (get user-info :id) (get user-info :name) (get user-info :cpf))))
;
;(s/defn save-user [id :- s/Int, name :- s/Str, cpf :- s/Str]
;  (let [user (c.userlogic/create-user id name cpf)]
;    (println @(d/transact conn [user]))))
;
;(s/defn save-users-in-batch [user-info-list]
;  (let [users (ref [])]
;    (dosync
;      (doseq [user-info user-info-list]
;        (add-user! users user-info))
;      (doseq [user (deref users)]
;        (println @(d/transact conn [user]))))))
;
;(s/defn analyse-users []
;  (let [users                 (db.user/query conn)
;        score-calculator-list [(c.sr-scorelogic/->SerasaScoreCalculator (atom []) conn)]]
;        ;score-calculator-list [(c.sr-scorelogic/->SerasaScoreCalculator (atom []) conn) (c.bv-scorelogic/->BoaVistaScoreCalculator (atom []) conn)]]
;    (doseq [score-calculator score-calculator-list]
;      (dosync
;        (doseq [user users] (c.score/calculate! score-calculator user))
;        (c.score/print-score score-calculator)
;        (c.score/save-analysis! score-calculator)))))
;
;;(save-users-in-batch c.model/user-info-list)
;; (analyse-users)
;
;(println (db.userscore/query conn))
