(ns dev.freeformsoftware.metacomet.map-utils
  (:require [com.fulcrologic.guardrails.core :refer [>defn =>]]
            [clojure.spec.alpha :as s]))

(>defn update-nn 
  "Update if not nil. Useful if you don't want to add keys that weren't 
  already present."
  [map key f & args]
  [(s/nilable map?) any? ifn? (s/* any?) => (s/nilable map?)]
  (if (contains? map key)
    (apply update map key f args)
    map))
