(ns dev.freeformsoftware.metacomet.map-utils-test
  (:require [clojure.test :refer :all]
            [dev.freeformsoftware.metacomet.map-utils :as mc.mu]))


(deftest test-update-nn
  (testing "normal functionality of update"
    (is (= (mc.mu/update-nn {:a 1} :a inc)
          {:a 2}))
    (is (= (mc.mu/update-nn {:a 2} :a + 4)
          {:a 6}))
    (is (= (mc.mu/update-nn {:a 5} :a (constantly nil))
          {:a nil}))
    (is (= (mc.mu/update-nn {:a nil} :a str "whatever")
          {:a "whatever"})))
  (testing "the not present functionality"
    (is (= (mc.mu/update-nn {} :a str "whatever")
          {}))))