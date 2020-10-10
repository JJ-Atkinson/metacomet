(ns dev.freeformsoftware.metacomet.testing-utils
  (:require [com.fulcrologic.guardrails.core :refer [>defn => |]]
            [clojure.spec.alpha :as s]
            [dev.freeformsoftware.metacomet.prim.number-fns :as nf]))

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
  "Shortcut when only a few keys of the result are of interest. Can swap in a comparator,
  default is `=`. Will pass in `(comparator correct actual)`.
  (like? {:a 1 :b 2} {:b 2}) => true
  (like? {:a 1 :b 2} {:a 1}) => true
  (like? {} {}) => true
  (like? {:a 9} {:a 8}) => false"
  ([actual correct-sub-map]
   (like? actual correct-sub-map =))
  ([actual correct-sub-map comparator]
   (->> correct-sub-map
     (every?
       (fn [[key val]] (comparator val (get actual key ::prevent-missing=nil)))))))


(s/def :distance-check/mode #{:abs :floor :ceil})
(s/def :distance-check/opts (s/keys :opt-un [:distance-check/mode]))


(>defn absolute-distance
  ([dist] [number? => any?] (absolute-distance dist {}))
  ([dist {:keys [mode]
          :or   {mode :abs}}]
   [number? :distance-check/opts =>
    [number? number? => boolean?]]
   (fn [a b]
     (let [diff (- a b)
           abs? (<= (nf/abs diff) dist)]
       (case mode :abs abs?
                  :ceil (and abs? (>= a b))
                  :floor (and abs? (<= a b)))))))


(>defn magnitude-distance
  ([magnitude dist] [number? number? => any?] (magnitude-distance magnitude dist {}))
  ([magnitude dist {:keys [mode]
                    :or   {mode :abs}}]
   [number? number? :distance-check/opts =>
    [number? number? => boolean?]]
   (fn [a b]
     (let [original-magnitude (Math/log10 a)
           diff-magnitude (- original-magnitude magnitude)
           dist-mult (* dist (Math/pow 10 diff-magnitude))
           diff (- a b)
           abs? (<= (nf/abs diff) dist-mult)]
       (case mode :abs abs?
                  :ceil (and abs? (>= a b))
                  :floor (and abs? (<= a b)))))))

(def ^:dynamic *default-number-checker* (magnitude-distance 10 1))


(defmacro with-number-checker [ncheck & body]
  `(binding [*default-number-checker* ~ncheck]
    ~@body))

(defn like-numbers?
  ([actual correct-sub-map]
   (like-numbers? actual correct-sub-map *default-number-checker*))
  ([actual correct-sub-map number-checker]
   (like? actual correct-sub-map number-checker)))
