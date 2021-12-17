(ns cardlimit.scorelogic-test
  (:require [clojure.test :refer :all]
            [cardlimit.sr-scorelogic :refer :all]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/set-fn-validation! true)

(deftest get-score-band-test
   (let [expected-values-with-maximum-score                    (assoc upmarket-user-values :score 100)
         expected-values-with-minimum-score-in-upmarket-band   (assoc upmarket-user-values :score (get upmarket-user-values :minimum-score))
         expected-values-with-maximum-score-in-platinum-band   (assoc platinum-user-values :score (- (get upmarket-user-values :minimum-score) 1))
         expected-values-with-minimum-score-in-platinum-band   (assoc platinum-user-values :score (get platinum-user-values :minimum-score))
         expected-values-with-maximum-score-in-gold-band       (assoc gold-user-values     :score (- (get platinum-user-values :minimum-score) 1))
         expected-values-with-minimum-score-in-gold-band       (assoc gold-user-values     :score (get gold-user-values :minimum-score))
         expected-values-with-maximum-score-in-start-card-band (assoc starter-user-values  :score (- (get gold-user-values :minimum-score) 1))
         expected-values-with-minimum-score                    (assoc starter-user-values  :score 0)]

     (testing "get-score-band com score mínimo (0)"
       (is (= expected-values-with-minimum-score (get-score-band (get expected-values-with-minimum-score :score)))))

     (testing "get-score-band com score máximo para a banda de start-card"
       (is (= expected-values-with-maximum-score-in-start-card-band (get-score-band (get expected-values-with-maximum-score-in-start-card-band :score)))))

     (testing "get-score-band com score mínimo para a banda de cartão Gold"
       (is (= expected-values-with-minimum-score-in-gold-band (get-score-band (get expected-values-with-minimum-score-in-gold-band :score)))))

     (testing "get-score-band com score máximo para a banda de cartão Gold"
       (is (= expected-values-with-maximum-score-in-gold-band (get-score-band (get expected-values-with-maximum-score-in-gold-band :score)))))

     (testing "get-score-band com score mínimo para a banda de cartão Platinum"
       (is (= expected-values-with-minimum-score-in-platinum-band (get-score-band (get expected-values-with-minimum-score-in-platinum-band :score)))))

     (testing "get-score-band com score máximo para a banda de cartão Platinum"
       (is (= expected-values-with-maximum-score-in-platinum-band (get-score-band (get expected-values-with-maximum-score-in-platinum-band :score)))))

     (testing "get-score-band com score mínimo para a banda de cartão Upmarket"
       (is (= expected-values-with-minimum-score-in-upmarket-band (get-score-band (get expected-values-with-minimum-score-in-upmarket-band :score)))))

     (testing "get-score-band com score máximo para a banda de cartão Upmarket"
       (is (= expected-values-with-maximum-score (get-score-band (get expected-values-with-maximum-score :score)))))))

(deftest get-score-band-with-invalid-score-test
  (testing "get-score-band com score Double deve retornar uma clojure.lang.ExceptionInfo"
    (is (thrown? ExceptionInfo (get-score-band 1.0))))

  (testing "get-score-band com score String deve retornar uma clojure.lang.ExceptionInfo"
      (is (thrown? ExceptionInfo (get-score-band "s"))))

  (testing "get-score-band com score Nil deve retornar uma clojure.lang.ExceptionInfo"
    (is (thrown? ExceptionInfo (get-score-band nil)))))