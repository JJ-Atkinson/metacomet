(ns dev.freeformsoftware.metacomet.prim.seq-utils)


(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(defn dissoc-vec
  "Dissociate an index from a vector"
  [v n]
  (into [] (concat (subvec v 0 n) (subvec v (inc n)))))