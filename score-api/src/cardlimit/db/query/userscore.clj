(ns cardlimit.db.query.userscore
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

; QUERIES PRECISAM SER REVISADAS.

;(defn query [conn]
;  (let [database (d/db conn)]
;    (d/q '[:find   ?id ?user-id ?band ?score ?initial-limit ?calculator
;           :keys  userscore/id userscore/user-id userscore/band userscore/score userscore/initial-limit userscore/calculator
;           :where [?userscore :userscore/id            ?id]
;                  [?userscore :userscore/user-id       ?user-id]
;                  [?userscore :userscore/band          ?band]
;                  [?userscore :userscore/score         ?score]
;                  [?userscore :userscore/initial-limit ?initial-limit]
;                  [?userscore :userscore/calculator    ?calculator]] database)))
;
;(defn get-score [conn id]
;  (let [users (let [database (d/db conn)]
;               (d/q '[:find   ?id ?user-id ?band ?score ?initial-limit ?calculator
;                      :keys  userscore/id userscore/user-id userscore/band userscore/score userscore/initial-limit userscore/calculator
;                      :in    $ ?id
;                      :where [?userscore :userscore/id            ?id]
;                             [?userscore :userscore/user-id       ?user-id]
;                             [?userscore :userscore/band          ?band]
;                             [?userscore :userscore/score         ?score]
;                             [?userscore :userscore/initial-limit ?initial-limit]
;                             [?userscore :userscore/calculator    ?calculator]] database id))
;    (first users)))