(ns dev.freeformsoftware.metacomet.prim.number-fns)


(defn parse-int [x]
  #?(:clj (Integer/parseInt x)
     :cljs (js/parseInt x)))

(defn nan?
  "Universal nan"
  [n]
  #?(:cljs (js/isNaN n)
     :clj  (Double/isNaN n)))

(defn ceil [n]
  #?(:clj  (Math/ceil n)
     :cljs (Math/ceil n)))

(defn floor [n]
  #?(:clj  (Math/floor n)
     :cljs (Math/floor n)))

(defn round
  "Wrap stupid round functionality. Round to digit precision may not work properly in clj."
  ([n] (Math/round n))
  #?(:cljs ([n ds] (if (or (nan? ds)
                         (not (boolean n))) n (js/parseFloat (.toFixed n ds))))
     :clj  ([n ds] (with-precision ds n))))

(defn abs 
  "Easy cross platform abs"
  [n]
  #?(:clj (Math/abs n)
     :cljs (Math/abs n)))