(ns cardlimit.utils.utils
  (:use clojure.pprint)
  (:require [schema.core       :as s]
            [clojure.data.json :as json]))

; format-methods
(s/defn formart-brl :- s/Str [value :- s/Num] (str "R$ " (format "%.2f" value)))

; herlper-methods
(defn ge-0? [x] (>= x 0))
(defn valid-band? [band](some #(= band %) [:starter :gold :platinum :upmarket]))

; schema-methods
(def PosInt            (s/pred pos-int? 'inteiro-positivo))
(def Band              (s/constrained s/Keyword valid-band?))
(def NonNegativeNumber (s/constrained s/Num ge-0?))

; InputStream to String
(defn is->str [is]
  (let [rdr (clojure.java.io/reader is)]
    (slurp rdr)))

(defn get-parameter [req pname] (get (:params req) pname))
(defn get-body      [req]       (json/read-str (is->str (:body req)) :key-fn keyword))