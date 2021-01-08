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
  ([f initial]
   (postwalk-till= f initial -1))
  ([f initial max-runs]
   (loop [v initial
          runs-left max-runs]
     (let [new-v (w/postwalk f v)]
       (if (or (= v new-v) (= 0 runs-left))
         new-v
         (recur new-v (dec runs-left)))))))