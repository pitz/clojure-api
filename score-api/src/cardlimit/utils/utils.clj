(ns cardlimit.utils.utils
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [clojure.data.json :as json])
  (:import (java.util UUID)))

; format-methods
(s/defn formart-brl :- s/Str [value :- s/Num] (str "R$ " (format "%.2f" value)))

(s/defn remove-non-numerics [strig-to-convert]
  (apply str (filter #(#{\0,\1,\2,\3,\4,\5,\6,\7,\8,\9} %) strig-to-convert)))

(defn uuid-from-string [data]
  (UUID/fromString (clojure.string/replace data #"(\w{8})(\w{4})(\w{4})(\w{4})(\w{12})" "$1-$2-$3-$4-$5")))

; constraint-methods
(defn ge-0?          [x]   (>= x 0))
(defn valid-band?    [band](some #(= band %) [:starter :gold :platinum :upmarket]))
(defn valid-partner? [band](some #(= band %) [:serasa :serpro]))

; schema-methods
(def PosInt            (s/pred pos-int? 'inteiro-positivo))
(def Band              (s/constrained s/Keyword valid-band?))
(def NonNegativeNumber (s/constrained s/Num ge-0?))
(def Partner           (s/constrained s/Keyword valid-partner?))

; InputStream to String
(defn is->str [is]
  (let [rdr (clojure.java.io/reader is)]
    (slurp rdr)))

(defn get-parameter [req pname] (get (:params req) pname))
(defn get-body      [req] (json/read-str (is->str (:body req)) :key-fn keyword))

(defn now [] (new java.util.Date))

(defn log-analysis [user-cpf]  (println "[>] Calculando score para o CPF" user-cpf "."))
(defn log-error    [exception message] (println "[ERROR]" (now) "-" message exception))
