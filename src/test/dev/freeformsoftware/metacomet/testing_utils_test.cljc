(ns dev.freeformsoftware.metacomet.testing-utils-test
  (:require [clojure.test :refer :all]
            [dev.freeformsoftware.metacomet.testing-utils :as dut :refer :all]))

(deftest counting-test
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


(deftest absolute-distance-test
  (testing "abs mode"
    (is ((absolute-distance 2)
         1 2.999))
    (is (not ((absolute-distance 2)
              1 3.1)))
    (is ((absolute-distance 2)
         2.99 1)))
  (testing "ceil mode"
    (let [f (absolute-distance 1 {:mode :ceil})]
      (is (f 1 1))
      (is (f 1 0.1))
      (is (not (f 1 1.99)))))
  (testing "floor mode"
    (let [f (absolute-distance 1 {:mode :floor})]
      (is (f 1 1))
      (is (not (f 1 0.1)))
      (is (f 1 1.99)))))


(deftest magnitude-diff-test
  (testing "abs mode"
    (let [fix1 (magnitude-distance 0 1)
          fix2 (magnitude-distance 1 1)
          fix3 (magnitude-distance 4 3)]
      (is (fix1 1 2))
      (is (not (fix1 1 2.2)))
      (is (fix2 1 0.9))
      (is (fix2 1 1.09))
      (is (not (fix2 1 0.89)))
      (is (not (fix2 1 1.1)))
      (is (fix3 1000 1000.29))
      (is (not (fix3 1000 1000.31)))))
  (testing "floor mode"
    (let [fix2 (magnitude-distance 1 1 {:mode :floor})]
      (is (not (fix2 1 0.9)))))
  (testing "ceil mode"
    (let [fix2 (magnitude-distance 1 1 {:mode :ceil})]
      (is (not (fix2 1 1.09))))))

(deftest like-number-checker
  (testing "magnitude-mode"
    (with-number-checker (magnitude-distance 3 1)
      (is (like-numbers?
            {:a 100.0 :b 100.0}
            {:a 100.1 :b 99.91}))
      (is (not (like-numbers?
                 {:a 100.0 :b 100.0}
                 {:a 100.11 :b 99.90}))))))

