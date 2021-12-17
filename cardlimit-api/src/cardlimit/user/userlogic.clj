(ns cardlimit.user.userlogic
  (:use clojure.pprint)
  (:require [cardlimit.db.config-db   :as db]
            [cardlimit.schemata      :as c.schenata]
            [cardlimit.db.query.user :as user-db]
            [datomic.api             :as d]
            [schema.core             :as s])
  (:import (java.util UUID)))

(def conn (db/connect-to-db))

(s/defn create-user :- c.schenata/User [name :- s/Str, cpf :- s/Str]
  {:user/id   (UUID/randomUUID)
   :user/name name
   :user/cpf  cpf})

(s/defn save-user [name :- s/Str, cpf :- s/Str]
  (let [user (create-user name cpf)]
    @(d/transact conn [user])
    user))

(defn list-users []        (user-db/query    conn))
(defn get-user   [user-id] (user-db/get-user conn user-id))