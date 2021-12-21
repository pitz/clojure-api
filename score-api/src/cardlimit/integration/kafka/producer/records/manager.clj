(ns cardlimit.integration.kafka.producer.records.manager
  (:gen-class)
  (:require [cardlimit.integration.kafka.producer.protocols.manager-protocol :as c.kafkaprotocol])
  (:import  [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]
            [org.apache.kafka.common.serialization StringSerializer]))

(defrecord KafkaProducerManager [] c.kafkaprotocol/KafkaProducerManagerProtocol

  (build-producer [this bootstrap-server]
    (let [producer-props {"value.serializer"  StringSerializer
                          "key.serializer"    StringSerializer
                          "bootstrap.servers" bootstrap-server}]
      (KafkaProducer. producer-props)))

  (send-message [this producer topic message-key message]
    @(.send producer (ProducerRecord. topic message-key message))))