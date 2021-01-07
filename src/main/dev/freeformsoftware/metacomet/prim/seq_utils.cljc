(ns dev.freeformsoftware.metacomet.prim.seq-utils
  (:require [clojure.walk :as w]))


(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(defn dissoc-vec
  "Dissociate an index from a vector"
  [v n]
  (into [] (concat (subvec v 0 n) (subvec v (inc n)))))

(defn postwalk-till=
  "Keep running (postwalk f v) until v remains the same between runs."
  [f initial]
  (loop [v initial]
    (let [new-v (w/postwalk f v)]
      (if (= v new-v)
        v
        (recur new-v)))))