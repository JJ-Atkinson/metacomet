(ns dev.freeformsoftware.metacomet.testing-utils-test
  (:require [clojure.test :refer :all]
            [dev.freeformsoftware.metacomet.testing-utils :as dut]))

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