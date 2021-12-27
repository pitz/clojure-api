(ns cardlimit.integration.kafka.consumer.records.manager
  (:gen-class)
  (:require [cardlimit.integration.kafka.consumer.protocols.manager-protocol :as c.kafkaprotocol]
            [clojure.data.json                                               :as json]
            [cardlimit.score.scorelogic                                      :as c.scorelogic])
  (:import  [org.apache.kafka.clients.admin AdminClientConfig NewTopic KafkaAdminClient]
            org.apache.kafka.clients.consumer.KafkaConsumer
            [org.apache.kafka.common.serialization StringDeserializer]
            (java.time Duration)))

(defrecord KafkaConsumerManager [] c.kafkaprotocol/KafkaConsumerManagerProtocol

  (create-topics! [this server-url topics partitions replication]
    (let [config       {AdminClientConfig/BOOTSTRAP_SERVERS_CONFIG server-url}
          adminClient (KafkaAdminClient/create config)
          new-topics  (map (fn [^String topic-name] (NewTopic. topic-name partitions replication)) topics)]
      (.createTopics adminClient new-topics)))

  (build-consumer [this server-url]
    (let [consumer-props {"bootstrap.servers",  server-url
                          "group.id",           "score-api"
                          "key.deserializer",   StringDeserializer
                          "value.deserializer", StringDeserializer
                          "auto.offset.reset",  "earliest"
                          "enable.auto.commit", "true"}]
      (KafkaConsumer. consumer-props)))

  (consumer-subscribe [this consumer topic]
    (.subscribe consumer [topic]))

  (run-consumer [this server-url]
    (let [consumer-topic   "cardlimit.created.user"
          producer-topic   "example-produced-topic"
          bootstrap-server server-url
          consumer         (c.kafkaprotocol/build-consumer this bootstrap-server)]

      (c.kafkaprotocol/create-topics! this bootstrap-server [producer-topic consumer-topic] 1 1)
      (c.kafkaprotocol/consumer-subscribe this consumer consumer-topic)

      (while true
        (let [records (.poll consumer (Duration/ofMillis 100))]
          (doseq [record records]

            (try (let [user-id  (get (json/read-str (str (.value record))) "id")
                       user-cpf (get (json/read-str (str (.value record))) "cpf")]
                   (c.scorelogic/analyse-user! user-id user-cpf))
                 (catch Exception e
                   (println "expression 3 throws" e)
                   (throw (Exception. "Erro ao processar fila!"))))
            (.commitAsync consumer)))))))