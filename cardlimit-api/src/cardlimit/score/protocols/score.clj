(ns cardlimit.score.protocols.score)

(defprotocol ScoreCalculator
  "Calculdor de Score de usuários por meio de consultas externas"

  ; Calcula Score de usuários em batch
  (calculate!  [this user])

  ; Exibe relatório de scores calculados
  (print-score [this])

  ; Salva os Scores calculados : vou continuar usando o calculate! desta maneira para manter o uso de swaps.
  (save-analysis! [this]))

; disclaimer:
; esse é um tipo de abstração que não faz sentido na vida real, há apenas uma forma de printar
; o relatório e há um pouco de código repetido,
; contudo, fiz dessa maneira apenas para termos mais funções no defprotocol