(ns kees.room.util
  (:require [clojure.pprint]))

(defn ceil
  [n]
  (if (and (int? n) (== n (int n)))
    n
    (inc (int n))))

(defn pretty-spit
  [f s]
  (->> s clojure.pprint/pprint with-out-str (spit f)))
