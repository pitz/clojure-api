(ns cardissuer.integration.kafka.consumer.logic
  (:gen-class)
  (:require [cardissuer.integration.kafka.consumer.records.manager            :as c.kafkarecords]
            [cardissuer.integration.kafka.consumer.protocols.manager-protocol :as c.kafkaprotocols]))

(defn consume []
  (let [kafka-consumer (c.kafkarecords/->KafkaConsumerManager)]
    (dosync
      (c.kafkaprotocols/run-consumer kafka-consumer "localhost:9092"))))