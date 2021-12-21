(ns cardlimit.integration.kafka.producerlogic
  (:gen-class)
  (:require [cardlimit.integration.kafka.records.kafka :as c.kafkarecords]
            [cardlimit.integration.kafka.protocols.kafka :as c.kafkaprotocols])
  (:import (java.util UUID)))

(defn send-message [topic message]
  (println " ... ")
  (println " ... (criando consumer) ")

  (let [kafka-producer (c.kafkarecords/->KafkaProducerManager)
        producer       (c.kafkaprotocols/build-producer kafka-producer "localhost:9092")
        message-id     (str (UUID/randomUUID))]

    (c.kafkaprotocols/send-message kafka-producer producer topic message-id message)))