(ns cardlimit.integration.kafka.consumer.logic
  (:gen-class)
  (:require [cardlimit.integration.kafka.consumer.records.manager            :as c.kafkarecords]
            [cardlimit.integration.kafka.consumer.protocols.manager-protocol :as c.kafkaprotocols]))

(defn consume []
  (let [kafka-consumer (c.kafkarecords/->KafkaConsumerManager)]
    (dosync
      (c.kafkaprotocols/run-consumer kafka-consumer "localhost:9092"))))