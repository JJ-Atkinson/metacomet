(ns dev.freeformsoftware.metacomet.testing-utils-test
  (:require [clojure.test :refer :all]
            [dev.freeformsoftware.metacomet.testing-utils :as dut :refer :all]))

(deftest counting
  (testing "Does it count correctly?"
    (let [[count f] (dut/counting)]
      (f) (f)
      (is (= @count 2))
      (f)
      (is (= @count 3))))
  (testing "Does it pass through the function correctly?"
    (let [[count f] (dut/counting inc)]
      (is (= (f 1) 2))
      (is (= @count 1)))))

(deftest like-test
  (testing "Submaps match every key"
    (is (like? {:a 1 :b 2} {:b 2}))
    (is (like? {:a 1 :b 2} {:a 1}))
    (is (like? {:a 1 :b 2} {:a 1 :b 2}))
    (is (not (like? {:a 1} {:a 2}))))
  (testing "Missing keys work correctly"
    (is (not (like? {} {:a nil})))))