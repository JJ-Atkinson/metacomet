(ns dev.freeformsoftware.metacomet.testing-utils)

(defn counting 
  "Define a function that counts how many times it has been invoked.
   []
   => [(atom int?) (fn [& _] nil)]
   [f]
   => [(atom int?) (fn [& args] (apply f args)]"
  ([] (counting (constantly nil)))
  ([f]
   (let [count (atom 0)]
     [count (fn [& args]
              (swap! count inc)
              (apply f args))])))


(defn like?
  "Shortcut when only a few keys of the result are of interest.
  (like? {:a 1 :b 2} {:b 2}) => true
  (like? {:a 1 :b 2} {:a 1}) => true
  (like? {} {}) => true
  (like? {:a 9} {:a 8}) => false"
  [actual correct-sub-map]
  (->> correct-sub-map
    (every?
      (fn [[key val]] (= (get actual key ::prevent-missing=nil) val)))))