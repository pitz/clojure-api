(ns cardlimit.db.query
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(defn get-batch [db id]
  (d/pull db '[*] [:score-batch/id id]))