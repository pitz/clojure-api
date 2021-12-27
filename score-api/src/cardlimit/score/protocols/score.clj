(ns cardlimit.score.protocols.score)

(defprotocol ScoreCalculator
  "Calculdor de Score de usuários por meio de consultas externas"
  (calculate! [this conn batch user-id user-cpf]))