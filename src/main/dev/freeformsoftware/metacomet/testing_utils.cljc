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