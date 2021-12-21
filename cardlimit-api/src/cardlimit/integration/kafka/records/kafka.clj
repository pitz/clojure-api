(ns cardlimit.integration.kafka.records.kafka
  (:gen-class)
  (:require [cardlimit.integration.kafka.protocols.kafka :as c.kafkaprotocol])
  (:import  [org.apache.kafka.clients.admin AdminClientConfig NewTopic KafkaAdminClient]
            org.apache.kafka.clients.consumer.KafkaConsumer
            [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]
            [org.apache.kafka.common.serialization StringDeserializer StringSerializer]
            (java.time Duration)))

(defrecord KafkaConsumerManager [] c.kafkaprotocol/KafkaConsumerManagerProtocol

  (create-topics! [this server-url topics partitions replication]
    (let [config       {AdminClientConfig/BOOTSTRAP_SERVERS_CONFIG server-url}
          adminClient (KafkaAdminClient/create config)
          new-topics  (map (fn [^String topic-name] (NewTopic. topic-name partitions replication)) topics)]
      (.createTopics adminClient new-topics)))

  (build-consumer [this server-url]
    (let [consumer-props {"bootstrap.servers",  server-url
                          "group.id",           "example"
                          "key.deserializer",   StringDeserializer
                          "value.deserializer", StringDeserializer
                          "auto.offset.reset",  "earliest"
                          "enable.auto.commit", "true"}]
      (KafkaConsumer. consumer-props)))

  (consumer-subscribe [this consumer topic]
    (.subscribe consumer [topic]))

  (run-consumer [this server-url]
    (let [consumer-topic   "calculated-score"
          bootstrap-server server-url
          consumer         (c.kafkaprotocol/build-consumer this bootstrap-server)]

      (c.kafkaprotocol/create-topics! this bootstrap-server [consumer-topic] 1 1)
      (c.kafkaprotocol/consumer-subscribe this consumer consumer-topic)

      (while true
        (let [records (.poll consumer (Duration/ofMillis 100))]
          (doseq [record records]
            (println " ... @ processando: " (str (.value record)))
            (.commitAsync consumer)))))))

(defrecord KafkaProducerManager [] c.kafkaprotocol/KafkaProducerManagerProtocol

  (build-producer [this bootstrap-server]
    (let [producer-props {"value.serializer"  StringSerializer
                          "key.serializer"    StringSerializer
                          "bootstrap.servers" bootstrap-server}]
      (KafkaProducer. producer-props)))

  (send-message [this producer topic message-key message]
    @(.send producer (ProducerRecord. topic message-key message))))