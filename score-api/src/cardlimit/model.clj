(ns cardlimit.model
  (:use clojure.pprint)
  (:require [schema.core           :as s]
            [cardlimit.utils.utils :as utils]))

(s/set-fn-validation! true)

(def user-info-list [{:id 1 :cpf "08861995906" :name "Eduardo Pitz"}
                     {:id 2 :cpf "01461995906" :name "Reuardo Pitz"}
                     {:id 3 :cpf "01246995906" :name "Rffuado Pitz"}
                     {:id 4 :cpf "07561995906" :name "Vfrardo Pitz"}
                     {:id 5 :cpf "12861995906" :name "Fruardo Pitz"}])

(def starter-user-values  { :userscore/band :starter  :userscore/initial-limit 50.00M    })
(def gold-user-values     { :userscore/band :gold     :userscore/initial-limit 650.00M   })
(def platinum-user-values { :userscore/band :platinum :userscore/initial-limit 1550.00M  })
(def upmarket-user-values { :userscore/band :upmarket :userscore/initial-limit 12050.50M })

(defn new-uuid [] (java.util.UUID/randomUUID))