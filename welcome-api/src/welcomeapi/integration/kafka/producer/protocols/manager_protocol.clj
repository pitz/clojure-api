(ns welcomeapi.integration.kafka.producer.protocols.manager-protocol (:gen-class))

(defprotocol KafkaProducerManagerProtocol
  (build-producer [this server-url])
  (send-message   [this producer topic message-key message]))
