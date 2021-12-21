(ns cardlimit.integration.kafka.consumerlogic
  (:gen-class)
  (:require [cardlimit.integration.kafka.records.kafka   :as c.kafkarecords]
            [cardlimit.integration.kafka.protocols.kafka :as c.kafkaprotocols]))

(defn consume []
  (println " ... ")
  (println " ... (criando consumer) ")

  (let [kafka-consumer (c.kafkarecords/->KafkaConsumerManager)]
    (dosync
      (c.kafkaprotocols/run-consumer kafka-consumer "localhost:9092"))))