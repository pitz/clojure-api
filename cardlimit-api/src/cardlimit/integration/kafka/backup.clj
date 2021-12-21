(ns cardlimit.integration.kafka.backup
  (:gen-class)
  (:require [cardlimit.integration.kafka.records.kafka :as c.kafka])
  (:import [org.apache.kafka.clients.admin AdminClientConfig NewTopic KafkaAdminClient]
           org.apache.kafka.clients.consumer.KafkaConsumer
           [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]
           [org.apache.kafka.common.serialization StringDeserializer StringSerializer]
           (java.time Duration)))






(defn create-topics! [bootstrap-server topics partitions replication]
  (let [config       {AdminClientConfig/BOOTSTRAP_SERVERS_CONFIG bootstrap-server}
        adminClient (KafkaAdminClient/create config)
        new-topics  (map (fn [^String topic-name] (NewTopic. topic-name partitions replication)) topics)]
    (.createTopics adminClient new-topics)))

(defn build-consumer [bootstrap-server]
  (let [consumer-props {"bootstrap.servers",  bootstrap-server
                        "group.id",           "example"
                        "key.deserializer",   StringDeserializer
                        "value.deserializer", StringDeserializer
                        "auto.offset.reset",  "earliest"
                        "enable.auto.commit", "true"}]
    (KafkaConsumer. consumer-props)))

(defn consumer-subscribe [consumer topic]
  (.subscribe consumer [topic]))

(defn build-producer [bootstrap-server]
  (let [producer-props {"value.serializer"  StringSerializer
                        "key.serializer"    StringSerializer
                        "bootstrap.servers" bootstrap-server}]
    (KafkaProducer. producer-props)))

(defn run-application [bootstrap-server]
  (let [consumer-topic   "cardlimit.created.user"
        producer-topic   "example-produced-topic"
        bootstrap-server bootstrap-server
        consumer         (build-consumer bootstrap-server)]

    (create-topics!      bootstrap-server [producer-topic consumer-topic] 1 1)
    ;(search-topic-by-key replay-consumer consumer-topic "1")
    (consumer-subscribe  consumer consumer-topic)

    (while true
      (let [records (.poll consumer (Duration/ofMillis 100))]
        (doseq [record records]
          (println " ... @ processando: " (str (.value record)))
      ; (.commitAsync consumer)
          )))))

(defn send-message [producer topic message-key message]
  @(.send producer (ProducerRecord. topic message-key message)))

(defn run-kafka []
  (println " ... ")
  (println " ... (criando consumer) ")
  (future (run-application "localhost:9092"))

  (println " ... ")
  (Thread/sleep 5000)
  (println " ... (publicando) ")

  (let [producer (build-producer "localhost:9092")
        user-info {:user-id "45639411-7d5e-476f-8883-a05e132215d1"
                   :user-cpf "08961996907"}]

    (send-message producer "cardlimit.created.user" "45639411-7d5e-476f-8883-a05e132215d1" (str user-info))))