(ns dev.freeformsoftware.metacomet.fulcro-utils
  )


;; helpers for me
(defn ident->id [ident]
  (assert (and (vector? ident) (= (count ident) 2)))
  (second ident))

(defn ident->table-id [ident]
  (assert (and (vector? ident) (= (count ident) 2)))
  (first ident))
