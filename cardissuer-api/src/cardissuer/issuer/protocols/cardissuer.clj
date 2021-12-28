(ns cardissuer.issuer.protocols.cardissuer)

(defprotocol CardIssuerProtocol
  (issue! [this]))