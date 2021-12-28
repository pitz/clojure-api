(ns cardissuer.core
  (:use clojure.pprint)
  (:require [cardissuer.integration.kafka.consumer.logic :as c.kafka-consumer]
            [compojure.core                              :refer :all]
            [ring.middleware.defaults                    :refer :all])
  (:gen-class))

(defn -main [& args]
  (println " ... starting Kafka Consumer ")
  (future (c.kafka-consumer/consume))
  (println " ... kafka running (CardIssuer)"))