(ns cardissuer.integration.kafka.consumer.protocols.manager-protocol (:gen-class))

(defprotocol KafkaConsumerManagerProtocol
  (create-topics!     [this server-url topics partitions replication])
  (build-consumer     [this server-url])
  (consumer-subscribe [this consumer topic])
  (run-consumer       [this server-url]))