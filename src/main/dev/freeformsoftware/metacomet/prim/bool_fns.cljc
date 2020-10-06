(ns dev.freeformsoftware.metacomet.prim.bool-fns)

(defn xor [a b]
  (and (or a b) (not (and a b))))
