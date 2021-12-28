(ns cardissuer.issuer.records.logic
  (:use clojure.pprint)
  (:require [cardissuer.issuer.protocols.cardissuer :as c.cardissuer]
            [schema.core                            :as s]))

(defrecord CardIssuer [] c.cardissuer/CardIssuerProtocol
  (issue! [this]
    (println "Emitindo cartão...")))

(s/defn issue-card! [ card-info]
  (println "Cartão recebido: " card-info)
  (let [card-issuer (->CardIssuer)]
    (c.cardissuer/issue! card-issuer)))