(ns cardlimit.integration.kafka.protocols.kafka (:gen-class))

(defprotocol KafkaConsumerManagerProtocol
  (create-topics!     [this server-url topics partitions replication])
  (build-consumer     [this server-url])
  (consumer-subscribe [this consumer topic])
  (run-consumer       [this server-url]))

(defprotocol KafkaProducerManagerProtocol
  (build-producer [this server-url])
  (send-message   [this producer topic message-key message]))
