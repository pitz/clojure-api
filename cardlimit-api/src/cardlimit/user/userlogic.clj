(ns cardlimit.user.userlogic
  (:use clojure.pprint)
  (:require [cardlimit.db.config-db                     :as db]
            [cardlimit.utils.utils                     :as utils]
            [cardlimit.schemata                        :as c.schenata]
            [cardlimit.db.query.user                   :as user-db]
            [cardlimit.integration.kafka.producerlogic :as c.kafka-producer]
            [datomic.api                               :as d]
            [schema.core                               :as s])
  (:import (java.util UUID)))

(s/defn create-user :- c.schenata/User [name :- s/Str, cpf :- s/Str]
  {:user/id   (UUID/randomUUID)
   :user/name name
   :user/cpf  cpf})

(defn validate-user [name, cpf]
  (let [has-invalid-name (or (not (string? name)) (nil? name) (clojure.string/blank? name))]
    (if has-invalid-name
      (throw (Exception. "O nome do usuário precisa ser informado."))))

  (let [has-invalid-cpf (or (nil? cpf)
                            (not (string? cpf))
                            (clojure.string/blank? cpf)
                            (not (= 11 (count (utils/remove-non-numerics cpf)))))]
    (if has-invalid-cpf
      (throw (Exception. "O CPF do usuário precisa ser informado."))))

  (let [cpf-already-used (user-db/is-cpf-already-in-use? (db/connect-to-db) cpf)]
    (if cpf-already-used
      (throw (Exception. "O CPF informado já está em uso.")))))

(s/defn send-user-created-event [user]
  (c.kafka-producer/send-message "cardlimit.created.user" (str user)))

(s/defn save-user :- c.schenata/User [name, cpf]
  (validate-user name cpf)

  (let [user (create-user name cpf)]
    @(d/transact (db/connect-to-db) [user])
    (send-user-created-event user)
    user))

(defn list-users []        (user-db/query    (db/connect-to-db)))
(defn get-user   [user-id] (user-db/get-user (db/connect-to-db) user-id))