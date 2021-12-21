(ns cardlimit.integration.kafka.producer.logic
  (:gen-class)
  (:require [cardlimit.integration.kafka.producer.records.manager            :as c.kafkarecords]
            [cardlimit.integration.kafka.producer.protocols.manager-protocol :as c.kafkaprotocols])
  (:import (java.util UUID)))

(defn send-message [topic message]
  (let [kafka-producer (c.kafkarecords/->KafkaProducerManager)
        producer       (c.kafkaprotocols/build-producer kafka-producer "localhost:9092")
        message-id     (str (UUID/randomUUID))]

    (c.kafkaprotocols/send-message kafka-producer producer topic message-id message)))