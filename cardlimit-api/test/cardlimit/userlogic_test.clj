(ns cardlimit.userlogic-test
  (:require [clojure.test :refer :all]
            [cardlimit.userlogic :refer :all]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/set-fn-validation! true)

;(deftest create-user-test
;  (let [valid-id     1
;        valid-nome   "Nome do Usuário"
;        valid-cpf    "87224286079"
;        invalid-id   -1
;        invalid-nome 0
;        invalid-cpf  87224286079
;        valid-user   {:id valid-id :nome valid-nome :cpf valid-cpf}]
;
;    (testing "create-user com dados válidos deve retornar um User"
;      (is (= valid-user (create-user valid-id valid-nome valid-cpf))))
;
;    (testing "create-user com id inválido deve retornar uma clojure.lang.ExceptionInfo"
;      (is (thrown? ExceptionInfo (create-user invalid-id valid-nome valid-cpf))))
;
;    (testing "create-user com nome inválido deve retornar uma clojure.lang.ExceptionInfo"
;      (is (thrown? ExceptionInfo (create-user valid-id invalid-nome valid-cpf))))
;
;    (testing "create-user com id inválido deve retornar uma clojure.lang.ExceptionInfo"
;      (is (thrown? ExceptionInfo (create-user valid-id valid-nome invalid-cpf))))))

; Mudei a forma como criamos score.
;(deftest create-user-score-test
;  (let [valid-user {:id 1 :nome "Nome do Usuário" :cpf "87224286079"}
;        invalid-user {}]
;
;    (testing "create-user-score-user retorna dados corretos do User"
;      (is (= valid-user (get (create-user-score valid-user) :user))))
;
;    (testing "create-user com id inválido deve retornar uma clojure.lang.ExceptionInfo"
;      (is (thrown? ExceptionInfo (create-user-score invalid-user))))))
