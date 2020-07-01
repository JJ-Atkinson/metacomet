(ns dev.freeformsoftware.metacomet.prim.fn-utils)

(defn reduce-fns
  "Reduce a list of functions over a value"
  [v fns]
  (reduce (fn [acc f] (f acc)) v fns))