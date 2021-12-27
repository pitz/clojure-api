(ns cardlimit.model
  (:use clojure.pprint)
  (:require [schema.core           :as s]
            [cardlimit.utils.utils :as utils]))

(s/set-fn-validation! true)

(def starter-user-values  { :band :starter  :initial-limit 50.00M    })
(def gold-user-values     { :band :gold     :initial-limit 650.00M   })
(def platinum-user-values { :band :platinum :initial-limit 1550.00M  })
(def upmarket-user-values { :band :upmarket :initial-limit 12050.50M })

(def gold-user-minimum-score     10)
(def platinum-user-minimum-score 50)
(def upmarket-user-minimum-score 90)