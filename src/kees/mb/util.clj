(ns kees.mb.util)

(defn ceil
  [n]
  (if (and (int? n) (== n (int n)))
    n
    (inc (int n))))
