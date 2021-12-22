(ns welcomeapi.core
  (:use clojure.pprint)
  (:require [welcomeapi.integration.kafka.consumer.logic :as c.kafka-consumer]
            [compojure.core                             :refer :all])
  (:gen-class))

(defn -main [& args]
  (println " ... starting Kafka Consumer ")
  (future (c.kafka-consumer/consume)))