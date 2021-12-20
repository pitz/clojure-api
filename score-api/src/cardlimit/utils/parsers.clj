(ns cardlimit.utils.parsers (:use clojure.pprint))

(defmulti  parse-band-name identity)
(defmethod parse-band-name :starter  [n] "Cesta Básica")
(defmethod parse-band-name :gold     [n] "Gold")
(defmethod parse-band-name :platinum [n] "Platinum")
(defmethod parse-band-name :upmarket [n] "UV")
(defmethod parse-band-name :default  [n] "Cesta de benefícios é inválida")